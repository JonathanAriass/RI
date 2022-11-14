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

public class UpdateProfessionalGroup implements Command<Void> {

	private ProfessionalGroupBLDto dto;
	private static ProfessionalGroupRepository repo = Factory.repository.forProfessionalGroup();

	public UpdateProfessionalGroup(ProfessionalGroupBLDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isTrue(dto.trieniumSalary > 0);
		ArgumentChecks.isNotEmpty(dto.name);
		ArgumentChecks.isTrue(dto.productivityRate > 0);
		ArgumentChecks.isNotBlank(dto.name);

		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		BusinessChecks.isTrue(existProfessionalGroup(),
				"Professoinal group does not exist.");

		ProfessionalGroup group = repo.findById(dto.id).get();

//		BusinessChecks.hasVersion(group, dto.version);

		group.setName(dto.name);
		group.setTrienniumSalary(dto.trieniumSalary);
		group.setProductivityRate(dto.productivityRate);

		return null;
	}

	private boolean existProfessionalGroup() {
		Optional<ProfessionalGroup> group = repo.findByName(dto.name);
		return !group.isEmpty();
	}

}
