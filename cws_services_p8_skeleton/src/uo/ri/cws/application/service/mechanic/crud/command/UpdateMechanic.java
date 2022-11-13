package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;
	private static MechanicRepository repo = Factory.repository.forMechanic();
	
	public UpdateMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotEmpty(dto.dni);
		ArgumentChecks.isNotEmpty(dto.name);
		ArgumentChecks.isNotEmpty(dto.surname);
		ArgumentChecks.isNotBlank(dto.dni);
		ArgumentChecks.isNotBlank(dto.name);
		ArgumentChecks.isNotBlank(dto.surname);
		
		this.dto = dto;
	}

	public Void execute() throws BusinessException {
		// Comprobar que el mecanico a updatear existe
		BusinessChecks.isTrue(existMechanic(), "Mechanic does not exist.");
		
		Mechanic m = repo.findById(dto.id).get();
		m.setName(dto.name);
		m.setSurname(dto.surname);
		
		return null;
	}

	
	private boolean existMechanic() {
		Optional<Mechanic> m = repo.findByDni(dto.dni);
		return !m.isEmpty();
	}
	
}
