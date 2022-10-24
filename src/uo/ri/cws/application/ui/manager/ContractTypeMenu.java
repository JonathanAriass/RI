package uo.ri.cws.application.ui.manager;

import menu.BaseMenu;
import menu.NotYetImplementedAction;

public class ContractTypeMenu extends BaseMenu {

    public ContractTypeMenu() {
	menuOptions = new Object[][] {
		{ "Manager > contract type type management", null },

		{ "Add contract type ", NotYetImplementedAction.class },
		{ "Update contract type ", NotYetImplementedAction.class },
		{ "Delete contract type ", NotYetImplementedAction.class },
		{ "List all contract types", NotYetImplementedAction.class }, };
    }

}
