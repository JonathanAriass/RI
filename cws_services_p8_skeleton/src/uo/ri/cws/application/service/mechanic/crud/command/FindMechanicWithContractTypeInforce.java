package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicWithContractTypeInforce
		implements Command<List<MechanicDto>> {

	private String name = "";
	private static MechanicRepository repoMechanic = Factory.repository.forMechanic();
	private static ContractTypeRepository repoContract = Factory.repository.forContractType();

	public FindMechanicWithContractTypeInforce(String name) {
		ArgumentChecks.isNotNull(name, "Invalid name, cannot be null");
		ArgumentChecks.isNotBlank(name, "Invalid name, cannot be empty");
		ArgumentChecks.isNotEmpty(name, "Invalid name, cannot be empty");

		this.name = name;
	}

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		// Comprobar que el tipo de contrato existe
//		BusinessChecks.isTrue(existsContractType(),
//				"ContractType does not exist");

		List<MechanicDto> result = new ArrayList<>();

		Optional<ContractType> ct = repoContract.findByName(name);

		if (ct.isEmpty()) {
			return result;
		}

		result = MechanicAssembler.toListDto(
				repoMechanic.findInForceInContractType(ct.get()));

		return result;
	}

	private boolean existsContractType() {
		if (repoContract.findByName(name).isPresent()) {
			return true;
		}
		return false;
	}

}
