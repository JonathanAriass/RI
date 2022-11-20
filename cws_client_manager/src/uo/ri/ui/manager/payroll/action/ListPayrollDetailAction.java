package uo.ri.ui.manager.payroll.action;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListPayrollDetailAction implements Action {

	@Override
	public void execute() throws BusinessException {
		// Get info
		String id = Console.readString("Payroll id  ");

		// Process

		Optional<PayrollBLDto> result;
		result = Factory.service.forPayrollService().getPayrollDetails(id);

		if (!result.isPresent()) {
			Console.print("No payroll found for mechanic");
		} else {
			// Print result
			Console.println(String.format("Details Payroll %s", id));
			Printer.printPayroll(result.get());
		}
	}

}
