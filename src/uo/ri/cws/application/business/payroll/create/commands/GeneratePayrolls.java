package uo.ri.cws.application.business.payroll.create.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import math.Round;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.business.workorder.WorkOrderService.WorkOrderBLDto;
import uo.ri.cws.application.business.workorder.assembler.WorkOrderAssembler;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;

import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

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
		if (fechaPresente == null) {
			fechaPresente = LocalDate.now();
		}
		
		List<String> contractsIds = checkContractsState();
		
		generateAllPayroll(contractsIds);
		
		return null;
	}


	private ContractGateway cg = PersistenceFactory.forContract();
	private List<String> checkContractsState() {
		List<String> result = new ArrayList<String>();
		
		List<ContractBLDto> contractsId = ContractAssembler.toDtoList(cg.findAll());
		ContractState state;
		LocalDate endDate;
		for (ContractBLDto c : contractsId) {
			state = c.state;
			endDate = c.endDate;
			if (state.equals(ContractState.IN_FORCE) ||
					( state.equals(ContractState.TERMINATED) && 
					fechaPresente != null && c.endDate != null &&
					endDate.getMonthValue() >= fechaPresente.getMonthValue() 
					&& endDate.getYear() == fechaPresente.getYear())) {
				if (!hasPayroll(c)) {
					result.add(c.id);					
				}
			}
		}
		
		return result;
	}
	
	
	private PayrollGateway pg = PersistenceFactory.forPayroll();
	
	private boolean hasPayroll(ContractBLDto contract) {
		List<PayrollBLDto> payrolls = PayrollAssembler.toDtoList(pg.findAll());
		
		for (PayrollBLDto p : payrolls) {
			if (p.contractId.equals(contract.id)
					&& p.date.getMonthValue() == fechaPresente.getMonthValue()
					&& p.date.getYear() == fechaPresente.getYear()) {
				return true;
			}
		}
		
		return false;
	}
	
	private void generateAllPayroll(List<String> contractIds) {
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
			double months = c.annualBaseWage / 14;
			p.monthlyWage = Round.twoCents(months);
			
			double bonus = getBonus(p.date, p.monthlyWage);
			p.bonus = Round.twoCents(bonus);

			double productivityBonus = getProductivityBonus(c.id, c);
			p.productivityBonus = Round.twoCents(productivityBonus);
			
			double triennium = getTrienniumPayment(c.id, c.startDate);
			p.trienniumPayment = Round.twoCents(triennium);
			
			// Calculate gross wage
			double earnings = p.monthlyWage + bonus + productivityBonus + triennium;
			
						
			// Deductions
			double tax = getIncomeTax(c.annualBaseWage);
			p.incomeTax = Round.twoCents(tax*earnings);
			
			double nic = getNic(c.annualBaseWage);
			p.nic = Round.twoCents(nic);
			
			
			// Calculate deductions
			double deductions = Round.twoCents(tax) + Round.twoCents(nic);
			// Calculate net wage
			double netWage = Round.twoCents(earnings) - Round.twoCents(deductions);
			p.netWage = Round.twoCents(netWage);
			
			// We add the data
			pg.add(PayrollAssembler.toDALDto(p));
		}
	}


	private double getIncomeTax(double annualBaseWage) {

		if (annualBaseWage > 0 && annualBaseWage <= 12450) {
			return 0.19;
		} 
		else if (annualBaseWage >= 12450 && annualBaseWage <= 20200) {
			return 0.24;
		}
		else if (annualBaseWage >= 20200 && annualBaseWage <= 35200) {
			return 0.30;
		}
		else if (annualBaseWage >= 35200 && annualBaseWage <= 60000) {
			return 0.37;
		} 
		else if (annualBaseWage >= 60000 && annualBaseWage <= 300000) {
			return 0.45;
		} 
		else {
			return 0.47;
		} 

	}


	private ProfessionalGroupGateway pgg = PersistenceFactory.forProfessionalGroup();
	private WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
	
	private double getProductivityBonus(String id, ContractBLDto c) {
		
		double auxWorkorders = 0;
		
		List<WorkOrderBLDto> workOrders = WorkOrderAssembler.toDtoList(
				wg.findByMechanic(c.dni));
		
		for (WorkOrderBLDto w : workOrders) {
			if (w.date.getMonth().equals(fechaPresente.getMonth())
					&& w.date.getYear() == fechaPresente.getYear() 
					&& w.state.equals("INVOICED")) {
				auxWorkorders += w.total;
			}
		}
				
		double productivityBonus = 0;
		
		String professionalGroupId = cg.findProfessionaGroupByContractId(id);
		productivityBonus = pgg.findById(professionalGroupId).get().
				productivity_bonus_percentage;

		return Round.twoCents((productivityBonus/100) * auxWorkorders);
	}
	
	private double getTrienniumPayment(String id, LocalDate startDate) {
		int trienniumAcumulatted = fechaPresente.getYear() - startDate.getYear();
		String professionalGroupId = cg.findProfessionaGroupByContractId(id);
		
		return Round.twoCents((trienniumAcumulatted /3 ) * 
				pgg.findById(professionalGroupId).get().triennium_payment);
	}

	private double getNic(double annualBaseWage) {
		return Round.twoCents((annualBaseWage * 0.05) / 12);
	}

	private double getBonus(LocalDate now, double monthWage) {
		if (now.getMonth().getValue() == 12 || now.getMonth().getValue() == 6) {
			return monthWage;
		}
		return 0;
	}
	
}
