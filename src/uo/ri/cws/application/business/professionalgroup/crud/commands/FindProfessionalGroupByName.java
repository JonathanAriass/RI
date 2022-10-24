package uo.ri.cws.application.business.professionalgroup.crud.commands;

import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;

public class FindProfessionalGroupByName implements Command<Optional<ProfessionalGroupBLDto>>{

	private String name = "";
	private ProfessionalGroupGateway pg = PersistenceFactory.forProfessionalGroup();

	
	public FindProfessionalGroupByName(String arg) {
		validate(arg);
		this.name = arg;
	}
	
	@Override
	public Optional<ProfessionalGroupBLDto> execute() throws BusinessException {
		return ProfessionalGroupAssembler.toBLDto(pg.findByName(name));
	}
	
	private void validate(String arg) {
		// usar clase del proyecto util Argument
		Argument.isNotEmpty(arg, "Null or empty name");

	}
	
}
