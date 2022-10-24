package uo.ri.cws.application.business.professionalgroup.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;

public class FindAllProfessionalGroups implements Command<List<ProfessionalGroupBLDto>> {

	private ProfessionalGroupGateway pg = PersistenceFactory.forProfessionalGroup();
	
	@Override
	public List<ProfessionalGroupBLDto> execute() throws BusinessException {
		List<ProfessionalGroupBLDto> result = new ArrayList<ProfessionalGroupBLDto>();
		
		result = ProfessionalGroupAssembler.toDtoList(pg.findAll());
		
		return result;
	}

}
