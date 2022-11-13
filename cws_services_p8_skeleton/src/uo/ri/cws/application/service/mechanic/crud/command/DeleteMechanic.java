package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
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
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("carworkshop");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
//		try {
//		Mechanic m = em.find(Mechanic.class, "mechanicId");
		
		// Checkear que el mecanico existe
		BusinessChecks.isTrue(existMechanic(), "Repeated mechanic");
		
		Mechanic m = repo.findById(mechanicId).get();
		
		repo.remove(m);
//		} catch (Exception e) {
//			if (tx.isActive()) tx.rollback();
//			throw e;
//		} finally {
//			em.close();
//			emf.close();
//		}
//		tx.commit();
		return null;
	}

	private boolean existMechanic() {
//		TypedQuery<Mechanic> query = Jpa.getManager().createNamedQuery("Mechanic.findByDni", Mechanic.class).setParameter(1, dto.dni);
		Optional<Mechanic> m = repo.findById(mechanicId);
		return !m.isEmpty();
	}
}
