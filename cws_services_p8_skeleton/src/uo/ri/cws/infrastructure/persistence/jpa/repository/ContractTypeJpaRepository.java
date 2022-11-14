package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.domain.ContractType;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ContractTypeJpaRepository extends BaseJpaRepository<ContractType>
		implements ContractTypeRepository {

	@Override
	public Optional<ContractType> findByName(String name) {
		EntityManager em = Jpa.getManager();
		ContractType contractType = em	.createNamedQuery(
												"ContractType.findByName",
												ContractType.class)
										.setParameter(1, name)
										.getResultStream()
										.findFirst()
										.orElse(null);

		if (contractType == null) {
			return Optional.empty();
		}
		return Optional.of(contractType);
	}

}
