package uo.ri.ui.manager.payroll.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class GeneratePayrollsAction implements Action {
	private PayrollService ps = Factory.service.forPayrollService();

	@Override
	public void execute() throws BusinessException {
		List<PayrollSummaryBLDto> all = null;

		ps.generatePayrolls();

		all = ps.getAllPayrolls();

		Console.printf("Generated %d new payrolls", all.size());
	}

}
