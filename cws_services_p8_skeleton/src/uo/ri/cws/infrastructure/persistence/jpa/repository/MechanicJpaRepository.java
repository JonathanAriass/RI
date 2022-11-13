package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class MechanicJpaRepository
		 extends BaseJpaRepository<Mechanic>
		implements MechanicRepository {

	@Override
	public List<Mechanic> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Mechanic> findByDni(String dni) {
		// TODO Auto-generated method stub
		return null;
	}

}
