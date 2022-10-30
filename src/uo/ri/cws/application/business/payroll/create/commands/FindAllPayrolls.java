package uo.ri.cws.application.business.payroll.create.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;

public class FindAllPayrolls implements Command<List<PayrollSummaryBLDto>> {

	private PayrollGateway pg = PersistenceFactory.forPayroll();
	
	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		List<PayrollSummaryBLDto> result = summaryPayrolls();
		
		return result;
	}

	/**
	 * Method that summary all payrolls
	 * @return List of payrollSummaryBLDto
	 */
	private List<PayrollSummaryBLDto> summaryPayrolls() {
		List<PayrollSummaryBLDto> aux = new ArrayList<PayrollSummaryBLDto>();
		
		List<PayrollBLDto> payrolls = PayrollAssembler.toDtoList(pg.findAll());
		
		for (PayrollBLDto p : payrolls) {
			aux.add(buildSummaryPayroll(p));
		}
		
		return aux;
	}

	/**
	 * Method that converts a payrollBLDto to a payrollSummaryBLDto
	 * @param p
	 * @return
	 */
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

}
