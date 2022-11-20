package uo.ri.ui.manager.payroll.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class DeletePayrollForMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {

		// Get info
		String id = Console.readString("Mechanic id ");

		Factory.service.forPayrollService().deleteLastPayrollFor(id);

		// Print result
		Console.println(String.format(
				"Last payroll for mechanic %s successfully deleted", id));
	}

}
