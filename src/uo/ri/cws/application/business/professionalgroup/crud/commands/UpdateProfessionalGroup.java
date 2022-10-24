package uo.ri.cws.application.business.professionalgroup.crud.commands;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;

public class UpdateProfessionalGroup implements Command<Void> {

	private ProfessionalGroupBLDto group = null;
	private ProfessionalGroupGateway pg = PersistenceFactory.forProfessionalGroup();
	
	
	public UpdateProfessionalGroup(ProfessionalGroupBLDto arg) {
		validate(arg);
		this.group = arg;
	}
	
	@Override
	public Void execute() throws BusinessException {
		if (!existProfessionalGroup(group.name)) {
			throw new BusinessException("Professional group doesn't exist");
		}
		
		pg.update(ProfessionalGroupAssembler.toDALDto(group));
		
		return null;
	}
	
	private boolean existProfessionalGroup(String name) {
		if (pg.findByName(name).isPresent()) {
			return true;
		}
		return false;
	}
	
	private void validate(ProfessionalGroupBLDto arg) {
		// usar clase del proyecto util Argument
		Argument.isNotNull(arg, "Null professional group");
		Argument.isNotEmpty(arg.name, "Null or empty name");
		Argument.isTrue(arg.productivityRate >= 0);
		Argument.isTrue(arg.trieniumSalary >= 0);
	}

}
