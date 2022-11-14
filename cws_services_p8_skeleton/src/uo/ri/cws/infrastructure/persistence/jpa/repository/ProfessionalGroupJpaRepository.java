package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ProfessionalGroupJpaRepository
		extends BaseJpaRepository<ProfessionalGroup>
		implements ProfessionalGroupRepository {

	@Override
	public Optional<ProfessionalGroup> findByName(String name) {
		EntityManager em = Jpa.getManager();
		ProfessionalGroup pg = em	.createNamedQuery(
											"ProfessionalGroup.findByName",
											ProfessionalGroup.class)
									.setParameter(1, name)
									.getResultStream()
									.findFirst()
									.orElse(null);

		if (pg == null) {
			return Optional.empty();
		}
		return Optional.of(pg);
	}

}
