package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteMechanic implements Command<Void> {

	private String mechanicId;
	private static MechanicRepository repo = Factory.repository.forMechanic();

	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotNull(mechanicId);
		ArgumentChecks.isNotEmpty(mechanicId);
		ArgumentChecks.isNotBlank(mechanicId);

		this.mechanicId = mechanicId;
	}

	public Void execute() throws BusinessException {

		Optional<Mechanic> m = repo.findById(mechanicId);

		check(m);

		repo.remove(m.get());

		return null;
	}

	private void check(Optional<Mechanic> m) throws BusinessException {
		BusinessChecks.isNotNull(m, "Mechanic does not exist");
		BusinessChecks.isTrue(m.isPresent(), "Mechanic is not present");
		BusinessChecks.isTrue(m.get().getInterventions().isEmpty(),
				"Mechanic has interventions assigned");
		BusinessChecks.isTrue(m.get().getTerminatedContracts().isEmpty(),
				"Mechanic has terminated contracts");
		BusinessChecks.isTrue(m.get().getContractInForce().isEmpty(),
				"Mechanic has contract in force");
		BusinessChecks.isTrue(m.get().getAssigned().isEmpty(),
				"Mechanic has workorders assigned");
	}

}
