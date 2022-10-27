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
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;

public class GeneratePayrolls implements Command<Void> {

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
		
		generateAllPayroll();
		
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
			} else if (endDate.getMonthValue() >= LocalDate.now().getMonthValue()) {
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
			
			p.date = LocalDate.now();
			p.monthlyWage = c.annualBaseWage / 14;
			p.bonus = p.monthlyWage;
			p.contractId = c.id;
			
			double tax = getIncomeTax(c.id);
			p.incomeTax = tax;
			
			double productivityBonus = getProductivityBouns(c.id);
			p.productivityBonus = productivityBonus;
			
		}
	}

	private ProfessionalGroupGateway pgg = PersistenceFactory.forProfessionalGroup();
	
	private double getIncomeTax(String id) {
		double tax = 0;
		
		String professionalGroupId= cg.findProfessionaGroupByContractId(id);
		String professionalGroupName = pgg.findById(professionalGroupId).get().name;
		switch(professionalGroupName) {
		case "I":
			tax = 
			break;
		case "II":
			
			break;
		case "III":
			
			break;
		case "IV":
			
			break;
		case "V":
			
			break;
		case "VI":
			
			break;
		case "VII":
			
			break;
		}
	}
	
	
	

}
