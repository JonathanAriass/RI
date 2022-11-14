package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.service.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class FindProfessionalGroupByName
		implements Command<Optional<ProfessionalGroupBLDto>> {

	private String name = "";
	private static ProfessionalGroupRepository repo = Factory.repository.forProfessionalGroup();

	public FindProfessionalGroupByName(String id) {
		ArgumentChecks.isNotEmpty(id);
		ArgumentChecks.isNotBlank(id);

		this.name = id;
	}

	@Override
	public Optional<ProfessionalGroupBLDto> execute() throws BusinessException {
		ProfessionalGroupBLDto dto = null;
		Optional<ProfessionalGroup> group = repo.findByName(name);
		if (group.isPresent()) {
			dto = ProfessionalGroupAssembler.toOptionalDto(group.get());
			return Optional.of(dto);
		}
		return Optional.empty();
	}

}
