package uo.ri.ui.manager.mechanic.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class FindAllMechanicsAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Console.println("\nList of mechanics \n");

		Printer.printMechanics(
				Factory.service.forMechanicCrudService().findAllMechanics());
	}
}
