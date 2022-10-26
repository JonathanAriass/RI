package uo.ri.cws.application.ui.manager;

import menu.BaseMenu;
import menu.NotYetImplementedAction;
import uo.ri.cws.application.ui.manager.action.professionalGroupManagement.AddProfessionalGroupAction;
import uo.ri.cws.application.ui.manager.action.professionalGroupManagement.DeleteProfessionalGroupAction;
import uo.ri.cws.application.ui.manager.action.professionalGroupManagement.ListAllProfessionalGroupAction;
import uo.ri.cws.application.ui.manager.action.professionalGroupManagement.ListProfessionalGroupByNameAction;
import uo.ri.cws.application.ui.manager.action.professionalGroupManagement.UpdateProfessionalGroupAction;

public class ProfessionalGroupMenu extends BaseMenu {

    public ProfessionalGroupMenu() {
	menuOptions = new Object[][] {
		{ "Manager > Professional Group management", null },

		{ "Add Professional Group ", AddProfessionalGroupAction.class },
		{ "Update Professional Group ", UpdateProfessionalGroupAction.class },
		{ "Delete Professional Group ", DeleteProfessionalGroupAction.class },
		{ "List all Professional Group ",
			ListAllProfessionalGroupAction.class },
		{ "List Professional Group By name ",
			ListProfessionalGroupByNameAction.class }, };
    }

}
