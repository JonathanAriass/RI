package uo.ri.cws.application.ui.manager.action.professionalGroupManagement;

import java.util.Optional;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class ListProfessionalGroupByNameAction implements Action {

	@Override
	public void execute() throws BusinessException {

		
		String name = Console.readString("Professional group name ");
		


		Printer.printProfessionalGroup(result);
		
	}

}
