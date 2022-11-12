package uo.ri.cws.application.service.mechanic.crud.command;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;

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
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("carworkshop");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Mechanic m = em.find(Mechanic.class, dto.id);
			// check if mechanic exists
			BusinessChecks.isNotNull(m, "Mechanic does not exist.");
			
			m.setName(dto.name);
			m.setSurname(dto.surname);
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			throw e;
		} finally {
			em.close();
			emf.close();
		}
		tx.commit();
		return null;
	}

}
