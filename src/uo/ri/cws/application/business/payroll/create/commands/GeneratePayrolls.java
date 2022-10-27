package uo.ri.cws.application.business.payroll.create.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.business.workorder.WorkOrderService.WorkOrderBLDto;
import uo.ri.cws.application.business.workorder.assembler.WorkOrderAssembler;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;

public class GeneratePayrolls implements Command<Void> {

	private LocalDate fechaPresente;
	
	public GeneratePayrolls() {
		fechaPresente = LocalDate.now();
	}
	
	public GeneratePayrolls(LocalDate date) {
		fechaPresente = date;
	}
	
	@Override
	public Void execute() throws BusinessException {
		// Comprobar para que empleados se pueden generar las payroll
		// Son aquellos que tienen contrato en vigor (IN_FORCE) o se extingue este mes
		// No puede tener 2 nominas generadas el mismo empleado para un mes
		
		/* Cosas a tener en cuenta en la nomina: 
		 * 		- monthlyWage (base / 14) 
		 * 		
		 * 		- bonus (meses correspondientes con importe igual al salario base)
		 * 		
		 * 		- productivityBonus (porcentaje importe total workorders asignadas al mecanico
		 * 							 de este mes y que hayan sido facturadas. El porcentaje depende
		 * 							 del grupo profesional)
		 * 		
		 * 		- trienniumPayment (complemento por antiguedad. Cada 3 anios acumulados en el mismo contrato
		 * 							se acumulara un trienio. Por cada trienio se recibe lo del grupo profesional)
		 * 
		 * 		- incomeTax (va por tramos - mirar en el pdf de la extension)
		 * */
		// Todos estos datos tienen que estar detallados al generar una factura
		
		List<String> contractsIds = checkContractsState();
		
		// comprobar si el mecanico de cada contrato valido ya tiene generada una payroll
		List<String> validContractsIds = validateContractsForMechanics(contractsIds);
		
		generateAllPayroll(validContractsIds);
		
		return null;
	}


	private ContractGateway cg = PersistenceFactory.forContract();
	// Se recorren todos los contratos y se comprueba el estado de este
	private List<String> checkContractsState() {
		List<String> result = new ArrayList<String>();
		
		List<ContractBLDto> contractsId = ContractAssembler.toDtoList(cg.findAll());
		ContractState state;
		LocalDate endDate;
		for (ContractBLDto m : contractsId) {
			state = m.state;
			endDate = m.endDate;
			if (state == ContractState.IN_FORCE) {
				result.add(m.id);
			} else if (endDate.getMonthValue() >= fechaPresente.getMonthValue() 
					&& endDate.getYear() == fechaPresente.getYear()) {
				result.add(m.id);
			}
		}
		
		return result;
	}
	
	
	private PayrollGateway pg = PersistenceFactory.forPayroll();
	
	private List<String> validateContractsForMechanics(List<String> contracts) {
		List<String> result = new ArrayList<String>();
		
		for (String cId : contracts) {
			if (pg.findPayrollByContractId(cId).isEmpty()) {
				result.add(cId);
			}
		}
		
		return result;
	}
	
	
	/* Cosas a tener en cuenta en la nomina: 
	 * 		- monthlyWage (base / 14) 
	 * 		
	 * 		- bonus (meses correspondientes con importe igual al salario base)
	 * 		
	 * 		- productivityBonus (porcentaje importe total workorders asignadas al mecanico
	 * 							 de este mes y que hayan sido facturadas. El porcentaje depende
	 * 							 del grupo profesional)
	 * 		
	 * 		- trienniumPayment (complemento por antiguedad. Cada 3 anios acumulados en el mismo contrato
	 * 							se acumulara un trienio. Por cada trienio se recibe lo del grupo profesional)
	 * 
	 * 		- incomeTax (va por tramos - mirar en el pdf de la extension)
	 */
	private void generateAllPayroll(List<String> contractIds) {
		// obtenemos toda la informacion del contrato con la gateway y la lista de ids de contrato
		List<ContractBLDto> contracts = new ArrayList<ContractBLDto>();
		for (String c : contractIds) {
			Optional<ContractBLDto> aux = ContractAssembler.toBLDto(cg.findById(c));
			if (aux.isPresent()) {
				contracts.add(aux.get());				
			}
		}
		
		PayrollBLDto p;
		for (ContractBLDto c : contracts) {
			p = new PayrollBLDto();
			p.id = UUID.randomUUID().toString();
			p.version = 1L;
			
			p.date = fechaPresente;
			p.contractId = c.id;
			
			// Earnings
			p.monthlyWage = c.annualBaseWage / 14;
			
			double bonus = getBonus(p.date, p.monthlyWage);
			p.bonus = bonus;

			double productivityBonus = getProductivityBouns(c.id, c);
			p.productivityBonus = productivityBonus;
			
			double triennium = getTrienniumPayment(c.id, c.startDate);
			p.trienniumPayment = triennium;
			
			// Deductions
			double tax = getIncomeTax(c.annualBaseWage);
			p.incomeTax = tax;
			
			double nic = getNic(c.annualBaseWage);
			p.nic = nic;
			
			// Calculate gross wage
			double earnings = p.monthlyWage + bonus + productivityBonus + triennium;
			// Calculate deductions
			double deductions = tax + nic;
			// Calculate net wage
			double netWage = earnings - deductions;
			p.netWage = netWage;
			
			// We add the data
			pg.add(PayrollAssembler.toDALDto(p));
		}
	}


	private double getIncomeTax(double annualBaseWage) {
		double tax = 0;
		
		if (annualBaseWage >= 0 && annualBaseWage <= 12450) {
			tax = 0.19;
		} 
		else if (annualBaseWage >= 12450 && annualBaseWage <= 20200) {
			tax = 0.24;
		}
		else if (annualBaseWage >= 20200 && annualBaseWage <= 35200) {
			tax = 0.30;
		}
		else if (annualBaseWage >= 35200 && annualBaseWage <= 60000) {
			tax = 0.37;
		} 
		else if (annualBaseWage >= 60000 && annualBaseWage <= 300000) {
			tax = 0.45;
		} 
		else if (annualBaseWage >= 300000) {
			tax = 0.47;
		} 
		
		return tax;
	}


	private ProfessionalGroupGateway pgg = PersistenceFactory.forProfessionalGroup();
	
	private double getProductivityBouns(String id, ContractBLDto c) {
		double productivityBonus = 0;
		
		
		double auxWorkorders = 0;
		List<WorkOrderBLDto> workOrders = WorkOrderAssembler.toDtoList(PersistenceFactory.forWorkOrder().findByMechanic(c.dni));
		for (WorkOrderBLDto w : workOrders) {
			if (w.date.getMonth().equals(fechaPresente.getMonth())
					&& w.date.getYear() == fechaPresente.getYear() 
					&& w.state.equals("INOICED")) {
				auxWorkorders += w.total;
			}
		}
		
		String professionalGroupId = cg.findProfessionaGroupByContractId(id);
		productivityBonus = pgg.findById(professionalGroupId).get().productivity_bonus_percentage;
		
		return (productivityBonus/100) * auxWorkorders;
	}
	
	private double getTrienniumPayment(String id, LocalDate startDate) {
		int trienniumAcumulatted = fechaPresente.getYear() - startDate.getYear();
		String professionalGroupId = cg.findProfessionaGroupByContractId(id);
		
		return (trienniumAcumulatted /3 ) * 
				pgg.findById(professionalGroupId).get().triennium_payment;
	}

	private double getNic(double annualBaseWage) {
		return (annualBaseWage * 0.05) / 12;
	}

	private double getBonus(LocalDate now, double monthWage) {
		if (now.getMonth().getValue() == 12 || now.getMonth().getValue() == 6) {
			return monthWage;
		}
		return 0;
	}
	
}
