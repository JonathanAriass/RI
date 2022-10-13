package uo.ri.cws.application.ui.manager.action;

import java.util.Optional;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class FindMechanicByDniAction implements Action {

	@Override
	public void execute() throws Exception {
		String dniMechanic = Console.readString("Type mechanic dni "); 
		
		MechanicService service = BusinessFactory.forMechanicService();
		Optional<MechanicBLDto> m = service.findMechanicByDni(dniMechanic);
		
		Printer.printMechanic(m.get());
	}
	
}
