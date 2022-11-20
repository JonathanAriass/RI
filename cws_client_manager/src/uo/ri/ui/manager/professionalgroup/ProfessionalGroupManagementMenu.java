package uo.ri.ui.manager.professionalgroup;

import uo.ri.ui.manager.professionalgroup.action.AddProfessionalGroupAction;
import uo.ri.ui.manager.professionalgroup.action.DeleteProfessionalGroupAction;
import uo.ri.ui.manager.professionalgroup.action.ListAllProfessionalGroupAction;
import uo.ri.ui.manager.professionalgroup.action.ListProfessionalGroupByNameAction;
import uo.ri.ui.manager.professionalgroup.action.UpdateProfessionalGroupAction;
import uo.ri.util.menu.BaseMenu;

public class ProfessionalGroupManagementMenu extends BaseMenu {

	public ProfessionalGroupManagementMenu() {
		menuOptions = new Object[][] {
				{ "Manager > Professional Group management", null },

				{ "Add Professional Group ", AddProfessionalGroupAction.class },
				{ "Update Professional Group ",
						UpdateProfessionalGroupAction.class },
				{ "Delete Professional Group ",
						DeleteProfessionalGroupAction.class },
				{ "List all Professional Group ",
						ListAllProfessionalGroupAction.class },
				{ "List Professional Group By name ",
						ListProfessionalGroupByNameAction.class }, };
	}

}
