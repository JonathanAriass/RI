package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicsInProfessionalGroup
		implements Command<List<MechanicDto>> {

	private String name = "";

	private static MechanicRepository repoMechanic = Factory.repository.forMechanic();
	private static ProfessionalGroupRepository repoGroups = Factory.repository.forProfessionalGroup();

	public FindMechanicsInProfessionalGroup(String name) {
		ArgumentChecks.isNotNull(name, "Invalid name, cannot be null");
		ArgumentChecks.isNotBlank(name, "Invalid name, cannot be empty");
		ArgumentChecks.isNotEmpty(name, "Invalid name, cannot be empty");

		this.name = name;
	}

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		List<MechanicDto> result = new ArrayList<>();

		Optional<ProfessionalGroup> pg = repoGroups.findByName(name);

		if (pg.isEmpty()) {
			return result;
		}

		result = MechanicAssembler.toListDto(
				repoMechanic.findAllInProfessionalGroup(pg.get()));

		return result;
	}

}
