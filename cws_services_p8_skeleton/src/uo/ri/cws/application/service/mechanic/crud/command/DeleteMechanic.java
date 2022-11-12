package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteMechanic implements Command<Void> {

	private String mechanicId;

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
		Mechanic m = em.find(Mechanic.class, "mechanicId");
		// check if mechanic exists
		BusinessChecks.isNotNull(m, "Mechanic does not exist.");
		
		em.remove(m);
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

}
