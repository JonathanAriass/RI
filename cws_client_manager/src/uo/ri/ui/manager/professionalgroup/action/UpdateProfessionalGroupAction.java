package uo.ri.ui.manager.professionalgroup.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class UpdateProfessionalGroupAction implements Action {

	@Override
	public void execute() throws BusinessException {

		String name = Console.readString("Professional group name ");
		double t = Console.readDouble("Professional group triennium salary ");
		double p = Console.readDouble(
				"Professional group productivity salary ");

		ProfessionalGroupBLDto dto = new ProfessionalGroupBLDto();
		dto.name = name;
		dto.productivityRate = p;
		dto.trieniumSalary = t;

		Factory.service	.forProfessionalGroupService()
						.updateProfessionalGroup(dto);

		Console.print("Professional group successfully updated");
	}

}
