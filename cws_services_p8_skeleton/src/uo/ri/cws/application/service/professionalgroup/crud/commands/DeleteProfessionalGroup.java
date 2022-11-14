package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteProfessionalGroup implements Command<Void> {

	private String name = "";
	private static ProfessionalGroupRepository repo = Factory.repository.forProfessionalGroup();

	public DeleteProfessionalGroup(String name) {
		ArgumentChecks.isNotNull(name);
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotBlank(name);

		this.name = name;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<ProfessionalGroup> group = repo.findByName(name);

		check(group);

		repo.remove(group.get());

		return null;
	}

	private void check(Optional<ProfessionalGroup> g) throws BusinessException {
		BusinessChecks.isNotNull(g, "Professional group does not exist");
		BusinessChecks.isTrue(g.isPresent(),
				"Professional group is not present");
		BusinessChecks.isTrue(g.get().getContracts().isEmpty(),
				"Professional group has contracts in relation");
	}

}
