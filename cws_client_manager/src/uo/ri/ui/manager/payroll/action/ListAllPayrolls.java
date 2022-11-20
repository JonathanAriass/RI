package uo.ri.ui.manager.payroll.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.menu.Action;

public class ListAllPayrolls implements Action {

	@Override
	public void execute() throws Exception {
		List<PayrollSummaryBLDto> result;
		result = Factory.service.forPayrollService().getAllPayrolls();

		Printer.printPayrollsSummary(result);
	}

}
