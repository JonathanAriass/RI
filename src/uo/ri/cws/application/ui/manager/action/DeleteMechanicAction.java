package uo.ri.cws.application.ui.manager.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicService;

public class DeleteMechanicAction implements Action {

	
	
	@Override
	public void execute() throws BusinessException {
		String idMechanic = Console.readString("Type mechanic id "); 
		
		MechanicService service = BusinessFactory.forMechanicService();
		service.deleteMechanic(idMechanic);
		
		Console.println("Mechanic deleted");
	}

}
