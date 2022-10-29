package uo.ri.cws.application.ui.manager.action.payrollManagement;

import java.util.List;

import menu.Action;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class ListAllPayrolls implements Action {

	@Override
	public void execute() throws Exception {
		List<PayrollSummaryBLDto> result;
		result = BusinessFactory.forPayrollService().getAllPayrolls();
		
		Printer.printPayrollsSummary(result);
	}

}
