package uo.ri.cws.application.business.professionalgroup.crud.commands;

import java.util.UUID;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;

public class AddProfessionalGroup implements Command<ProfessionalGroupBLDto> {

	private ProfessionalGroupBLDto group = null;
	private ProfessionalGroupGateway pg = PersistenceFactory.forProfessionalGroup();
	
	public AddProfessionalGroup(ProfessionalGroupBLDto arg) {
		validate(arg);
		group = arg;
	}
	
	@Override
	public ProfessionalGroupBLDto execute() throws BusinessException {
		// check if professional group exists already
		if (existProfessionalGroup(group.name)) {
			throw new BusinessException("Professional group already exists");
		}
		
		insertProfessionalGroup();
		
		return group;
	}

	private boolean existProfessionalGroup(String name) {
		if (pg.findByName(name).isPresent()) {
			return true;
		}
		return false;
	}

	private void insertProfessionalGroup() {
		group.id = UUID.randomUUID().toString();
		group.version = 1L;
		
		ProfessionalGroupDALDto prgr = ProfessionalGroupAssembler.toDALDto(group);
		pg.add(prgr);
	}

	private void validate(ProfessionalGroupBLDto arg) {
		// usar clase del proyecto util Argument
		Argument.isNotNull(arg, "Null professional group");
		Argument.isNotEmpty(arg.name, "Null or empty name");
		Argument.isTrue(arg.productivityRate >= 0);
		Argument.isTrue(arg.trieniumSalary >= 0);
	}
	
}
