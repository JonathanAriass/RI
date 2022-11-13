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
		ArgumentChecks.isNotEmpty(mechanicId);
		ArgumentChecks.isNotBlank(mechanicId);
		
		this.mechanicId = mechanicId;
	}

	public Void execute() throws BusinessException {
		// Checkear que el mecanico existe
		BusinessChecks.isTrue(existMechanic(), "Repeated mechanic");
		
		Mechanic m = repo.findById(mechanicId).get();
		
		repo.remove(m);

		return null;
	}

	private boolean existMechanic() {
		Optional<Mechanic> m = repo.findById(mechanicId);
		return !m.isEmpty();
	}
}
