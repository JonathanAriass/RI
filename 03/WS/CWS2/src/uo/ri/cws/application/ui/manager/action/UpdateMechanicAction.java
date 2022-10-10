package uo.ri.cws.application.ui.manager.action;



import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;

public class UpdateMechanicAction implements Action {

	

	@Override
	public void execute() throws BusinessException {
		
		// Get info
		String id = Console.readString("Type mechahic id to update"); 
		String name = Console.readString("Name"); 
		String surname = Console.readString("Surname");
		
		MechanicBLDto dto = new MechanicBLDto();
		dto.id = id;
		dto.name = name;
		dto.surname = surname;
		
		MechanicService service = BusinessFactory.forMechanicService();
		service.updateMechanic(dto);
		
		// Print result
		Console.println("Mechanic updated");
	}

}
