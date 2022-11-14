package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.domain.ContractType;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class MechanicJpaRepository extends BaseJpaRepository<Mechanic>
		implements MechanicRepository {

	@Override
	public Optional<Mechanic> findByDni(String dni) {
		EntityManager em = Jpa.getManager();
		Mechanic mechanic = em	.createNamedQuery("Mechanic.findByDni",
										Mechanic.class)
								.setParameter(1, dni)
								.getResultStream()
								.findFirst()
								.orElse(null);

		if (mechanic == null) {
			return Optional.empty();
		}
		return Optional.of(mechanic);
	}

	@Override
	public List<Mechanic> findAllInForce() {
		EntityManager em = Jpa.getManager();
		List<Mechanic> mechanics = em	.createNamedQuery(
												"Mechanic.findMechanicWithContractInforce",
												Mechanic.class)
										.getResultList();

		return mechanics;
	}

	@Override
	public List<Mechanic> findInForceInContractType(ContractType contractType) {
		EntityManager em = Jpa.getManager();
		List<Mechanic> mechanics = em	.createNamedQuery(
												"Mechanic.findMechanicWithContracTypetInforce",
												Mechanic.class)
										.setParameter(1, contractType)
										.getResultList();

		return mechanics;
	}

	@Override
	public List<Mechanic> findAllInProfessionalGroup(ProfessionalGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

}
