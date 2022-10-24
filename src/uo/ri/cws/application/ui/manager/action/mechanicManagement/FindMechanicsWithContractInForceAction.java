package uo.ri.cws.application.ui.manager.action.mechanicManagement;

import java.util.List;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class FindMechanicsWithContractInForceAction implements Action {

	
	@Override
	public void execute() throws BusinessException {

		Console.println("\nList of mechanics with contract in force\n");  

		List<MechanicBLDto> mechanics = BusinessFactory.forMechanicService().findMechanicsInForce();
		
		Printer.printMechanics(mechanics);


	}
}
