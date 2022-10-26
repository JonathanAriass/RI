package uo.ri.cws.application.business.contracttype.create;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contracttype.ContractTypeService;

public class ContractTypeServiceImpl implements ContractTypeService {

	@Override
	public ContractTypeBLDto addContractType(ContractTypeBLDto dto) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteContractType(String name) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateContractType(ContractTypeBLDto dto) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ContractTypeBLDto> findContractTypeByName(String name) throws BusinessException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ContractTypeBLDto> findAllContractTypes() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
