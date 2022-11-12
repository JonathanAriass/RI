package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicById implements Command<Optional<MechanicDto>> {

	private String id;

	public FindMechanicById(String id) {
		ArgumentChecks.isNotEmpty(id);
		ArgumentChecks.isNotBlank(id);
		
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("carworkshop");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		MechanicDto dto = null;
		try {
			Mechanic m = em.find(Mechanic.class, id);
			
			dto = MechanicAssembler.toOptionalDto(m);
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			throw e;
		} finally {
			em.close();
			emf.close();
		}
		tx.commit();
		return Optional.of(dto);
	}

}
