package uo.ri.cws.application.service.payroll.crud.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.service.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindAllPayrolls implements Command<List<PayrollSummaryBLDto>> {

	private static PayrollRepository repoPayrolls = Factory.repository.forPayroll();

	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		List<PayrollSummaryBLDto> result = summaryPayrolls();

		return result;
	}

	private List<PayrollSummaryBLDto> summaryPayrolls() {
		List<PayrollSummaryBLDto> aux = new ArrayList<PayrollSummaryBLDto>();

		List<PayrollBLDto> payrolls = PayrollAssembler.toDtoList(
				repoPayrolls.findAll());

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

		double earnings = p.monthlyWage + p.bonus + p.productivityBonus
				+ p.trienniumPayment;
		double deductions = p.incomeTax + p.nic;
		double netWage = Math.round(earnings * 100.0) / 100.0
				- Math.round(deductions * 100.0) / 100.0;
		result.netWage = netWage;

		return result;
	}

}
