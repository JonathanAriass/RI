package uo.ri.cws.application.business.payroll.create.commands;

import java.util.ArrayList;
import java.util.List;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;

public class FindPayrollsForProfessionalGroups implements Command<List<PayrollSummaryBLDto>> {

	
	private String professionalGroupName = "";
	private PayrollGateway pg = PersistenceFactory.forPayroll();
	private ProfessionalGroupGateway pgg = PersistenceFactory.forProfessionalGroup();
	private ContractGateway cg = PersistenceFactory.forContract();
	
	public FindPayrollsForProfessionalGroups(String arg) {
		validate(arg);
		this.professionalGroupName = arg;
	}
	
	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		if (!existProfessionalGroup(professionalGroupName)) {
			throw new BusinessException("Professional group doesn't exist");
		}
		
		// get all contracts for professional group and then checks payroll exists add to list
		String professionalGroupId = pgg.findByName(professionalGroupName).get().id;
		List<ContractBLDto> contractsId = ContractAssembler.toDtoList(cg.findByProfessionalGroupId(professionalGroupId));
		
		
		List<PayrollBLDto> payrolls = new ArrayList<PayrollBLDto>();
		for (ContractBLDto c : contractsId) {
			 payrolls.addAll(PayrollAssembler.toDtoList(pg.findPayrollByContractId(c.id)));
		}
		
		
		
		return buildSummaryPayroll(payrolls);
	}

	private boolean existProfessionalGroup(String name) {
		if (pgg.findByName(name).isPresent()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method that converts a payrollBLDto to a payrollSummaryBLDto
	 * @param p
	 * @return
	 */
	private List<PayrollSummaryBLDto> buildSummaryPayroll(List<PayrollBLDto> payrolls) {
		List<PayrollSummaryBLDto> aux = new ArrayList<PayrollSummaryBLDto>();
		
		for (PayrollBLDto p : payrolls) {
			aux.add(buildSummaryPayroll(p));
		}
		
		return aux;
	}
	
	private PayrollSummaryBLDto buildSummaryPayroll(PayrollBLDto p) {
		PayrollSummaryBLDto result = new PayrollSummaryBLDto();
		
		result.id = p.id;
		result.version = p.version;
		
		result.date = p.date;
		
		double earnings = p.monthlyWage + p.bonus + p.productivityBonus + p.trienniumPayment;
		double deductions = p.incomeTax + p.nic;
		double netWage = Math.round(earnings*100.0)/100.0 - Math.round(deductions*100.0)/100.0;
		result.netWage = netWage;
		
		return result;
	}
	
	private void validate(String arg) {
		// usar clase del proyecto util Argumen
		Argument.isNotEmpty(arg, "Null or empty id");
	}
	
}
