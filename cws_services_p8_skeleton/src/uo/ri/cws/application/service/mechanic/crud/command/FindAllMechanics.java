package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class FindAllMechanics implements Command<List<MechanicDto>> {

	public List<MechanicDto> execute() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("carworkshop");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<MechanicDto> dto = new ArrayList<>();
		try {
			TypedQuery<Mechanic> query = em.createNamedQuery("Mechanic.findAll", Mechanic.class);
			List<Mechanic> l = query.getResultList();
			dto = MechanicAssembler.toListDto(l);
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			throw e;
		} finally {
			em.close();
			emf.close();
		}
		tx.commit();
		return dto;
	}

}
