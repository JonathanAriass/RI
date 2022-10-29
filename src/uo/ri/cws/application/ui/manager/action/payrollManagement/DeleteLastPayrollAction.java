package uo.ri.cws.application.ui.manager.action.payrollManagement;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;

public class DeleteLastPayrollAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		BusinessFactory.forPayrollService().deleteLastPayrolls();
		
		// Print result
		Console.println( "Last payroll successfully deleted" );		
	}

}
