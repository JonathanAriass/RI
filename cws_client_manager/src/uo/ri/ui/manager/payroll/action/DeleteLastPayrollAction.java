package uo.ri.ui.manager.payroll.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class DeleteLastPayrollAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Factory.service.forPayrollService().deleteLastPayrolls();

		// Print result
		Console.println("Last payroll successfully deleted");
	}

}
