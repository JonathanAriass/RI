package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class AddProfessionalGroup implements Command<ProfessionalGroupBLDto> {

	private ProfessionalGroupBLDto dto;
	private static ProfessionalGroupRepository repo = Factory.repository.forProfessionalGroup();

	public AddProfessionalGroup(ProfessionalGroupBLDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isTrue(dto.trieniumSalary > 0);
		ArgumentChecks.isNotEmpty(dto.name);
		ArgumentChecks.isTrue(dto.productivityRate > 0);
		ArgumentChecks.isNotBlank(dto.name);

		this.dto = dto;
	}

	@Override
	public ProfessionalGroupBLDto execute() throws BusinessException {
		BusinessChecks.isTrue(notExistMechanic(),
				"Repeated professional group");

		ProfessionalGroup group = new ProfessionalGroup(dto.name,
				dto.trieniumSalary, dto.productivityRate);
		dto.id = group.getId();

		repo.add(group);

		return dto;
	}

	private boolean notExistMechanic() {
		Optional<ProfessionalGroup> m = repo.findByName(dto.name);
		return m.isEmpty();
	}
}
