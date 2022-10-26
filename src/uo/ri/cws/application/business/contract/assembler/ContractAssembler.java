package uo.ri.cws.application.business.contract.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;



public class ContractAssembler {

	public static Optional<ContractBLDto> toBLDto(Optional<ContractDALDto> arg) {
		Optional<ContractBLDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toContractDto(arg.get()));
		return result;
	}

	public static List<ContractBLDto> toDtoList(List<ContractDALDto> arg) {
		List<ContractBLDto> result = new ArrayList<ContractBLDto>();
		for (ContractDALDto mr : arg)
			result.add(toContractDto(mr));
		return result;
	}

	public static ContractDALDto toDALDto(ContractBLDto arg) {
		ContractDALDto result = new ContractDALDto();
		result.id = arg.id;
		result.version = arg.version;
		result.annualbasewage = arg.annualBaseWage;
		result.contracttype_id = arg.contractTypeName;
		result.enddate = arg.endDate;
		result.mechanic_id = arg.
		return result;
	}

	private static ContractBLDto toContractDto(ContractDALDto arg) {

		ContractBLDto result = new ContractBLDto();
		result.id = arg.id;
		result.version = arg.version;
		result.name = arg.name;
		result.surname = arg.surname;
		result.dni = arg.dni;
		return result;
	}
	
}
