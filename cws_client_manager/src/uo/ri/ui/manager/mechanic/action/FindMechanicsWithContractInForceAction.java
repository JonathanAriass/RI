package uo.ri.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class FindMechanicsWithContractInForceAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Console.println("\nList of mechanics with contract in force\n");

		List<MechanicDto> mechanics = Factory.service	.forMechanicCrudService()
														.findMechanicsInForce();

		Printer.printMechanics(mechanics);

	}
}
