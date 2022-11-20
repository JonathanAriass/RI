package uo.ri.ui.manager.professionalgroup.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class AddProfessionalGroupAction implements Action {

	@Override
	public void execute() throws Exception {

		// Get info
		String name = Console.readString("Name ");
		Double triennium = Console.readDouble("Triennium Salary ");
		Double productivityR = Console.readDouble("Productivity rate ");

		// Process

		ProfessionalGroupBLDto pg = new ProfessionalGroupBLDto();
		pg.trieniumSalary = triennium;
		pg.name = name;
		pg.productivityRate = productivityR;

		ProfessionalGroupBLDto result = Factory.service	.forProfessionalGroupService()
														.addProfessionalGroup(
																pg);

		// Print result
		Console.println("New Professional Group succesfully added");
		Printer.printProfessionalGroup(result);
	}

}
