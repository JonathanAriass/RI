package uo.ri.ui.manager.payroll.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListPayrollsByProfessionalGroupAction implements Action {

	@Override
	public void execute() throws BusinessException {

		// Get info
		String name = Console.readString("Professional group  ");

		// Process

		List<PayrollSummaryBLDto> result = null;
		result = Factory.service.forPayrollService()
								.getAllPayrollsForProfessionalGroup(name);

		// Print result
		Console.println(
				String.format("Payrolls for professional group %s", name));
		Printer.printPayrollsSummary(result);

	}

}
