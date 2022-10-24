package uo.ri.cws.application.ui.manager.action.professionalGroupManagement;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessFactory;

public class DeleteProfessionalGroupAction implements Action {

	@Override
	public void execute() throws Exception {
		
		String name = Console.readString("Professional group name ");
		
		BusinessFactory.forProfessionalGroupService().deleteProfessionalGroup(name);

		Console.print("Professional group successfully deleted");		
	}

}
