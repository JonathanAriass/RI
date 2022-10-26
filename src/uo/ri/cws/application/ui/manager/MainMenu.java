package uo.ri.cws.application.ui.manager;

import menu.BaseMenu;

public class MainMenu extends BaseMenu {

    public MainMenu() {
	menuOptions = new Object[][] { { "Administrator", null },
		{ "Mechanics management", MechanicMenu.class },
		{ "Spare parts management", SparePartMenu.class },
		{ "Vehicle types management", ContractTypeMenu.class },
		{ "Contracts management", ContractsMenu.class },
		{ "Payroll management", PayrollManagementMenu.class },
		{ "Professional groups management", ProfessionalGroupMenu.class}

	};
    }

    public static void main(String[] args) {
	new MainMenu().execute();
    }

}
