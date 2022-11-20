package uo.ri.ui.manager.professionalgroup.action;

import uo.ri.conf.Factory;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class DeleteProfessionalGroupAction implements Action {

	@Override
	public void execute() throws Exception {

		String name = Console.readString("Professional group name ");

		Factory.service	.forProfessionalGroupService()
						.deleteProfessionalGroup(name);

		Console.print("Professional group successfully deleted");
	}

}
