package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto dto;
	private static MechanicRepository repo = Factory.repository.forMechanic();

	public AddMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotEmpty(dto.dni);
		ArgumentChecks.isNotEmpty(dto.name);
		ArgumentChecks.isNotEmpty(dto.surname);
		ArgumentChecks.isNotBlank(dto.dni);
		ArgumentChecks.isNotBlank(dto.name);
		ArgumentChecks.isNotBlank(dto.surname);

		this.dto = dto;
	}

	public MechanicDto execute() throws BusinessException {
		BusinessChecks.isTrue(notExistMechanic(), "Repeated mechanic");

		Mechanic mechanic = new Mechanic(dto.dni, dto.name, dto.surname);
		dto.id = mechanic.getId();

		repo.add(mechanic);

		return dto;
	}

	private boolean notExistMechanic() {
		Optional<Mechanic> m = repo.findByDni(dto.dni);
		return m.isEmpty();
	}

}
