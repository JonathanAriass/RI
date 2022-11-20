package uo.ri.cws.application.service.contracttype.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;

public class ContractTypeServiceImpl implements ContractTypeService {

	@Override
	public ContractTypeDto addContractType(ContractTypeDto dto)
			throws BusinessException {

		return null;
	}

	@Override
	public void deleteContractType(String name) throws BusinessException {

	}

	@Override
	public void updateContractType(ContractTypeDto dto)
			throws BusinessException {

	}

	@Override
	public Optional<ContractTypeDto> findContractTypeByName(String name)
			throws BusinessException {

		return Optional.empty();
	}

	@Override
	public List<ContractTypeDto> findAllContractTypes()
			throws BusinessException {

		return null;
	}

}
