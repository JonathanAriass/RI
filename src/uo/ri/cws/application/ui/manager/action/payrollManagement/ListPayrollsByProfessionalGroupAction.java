package uo.ri.cws.application.ui.manager.action.payrollManagement;

import java.util.List;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class ListPayrollsByProfessionalGroupAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		// Get info
		String name = Console.readString("Professional group  ");

		// Process

		List<PayrollSummaryBLDto> result = null;



		// Print result
		Console.println(String.format("Payrolls for professional group %s", name));
		Printer.printPayrollsSummary(result);


	}

}
