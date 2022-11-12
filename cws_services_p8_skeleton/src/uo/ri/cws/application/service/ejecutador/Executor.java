package uo.ri.cws.application.service.ejecutador;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class Executor implements CommandExecutor {

	@Override
	public <T> T execute(Command<T> cmd) throws BusinessException {
		T result = null;
		
		EntityManager em = Jpa.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			result = cmd.execute();
			tx.commit();
		}
		catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			throw e;
		} finally {
			em.close();
		}
		
		return result;
	}

	
	
}
