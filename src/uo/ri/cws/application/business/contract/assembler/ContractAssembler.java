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
		result.settlement = arg.settlement;
		result.contracttype_id = arg.contractTypeName;
		result.startdate = arg.startDate;
		result.enddate = arg.endDate;
		result.state = arg.state;
		result.professionalgroup_id = arg.professionalGroupName;
		
		return result;
	}

	private static ContractBLDto toContractDto(ContractDALDto arg) {

		ContractBLDto result = new ContractBLDto();
		result.id = arg.id;
		result.dni = arg.mechanic_id;
		result.version = arg.version;
		result.annualBaseWage = arg.annualbasewage;
		result.settlement = arg.settlement;
		result.contractTypeName = arg.contracttype_id;
		result.startDate = arg.startdate;
		result.endDate = arg.enddate;
		result.state = arg.state;
		result.professionalGroupName = arg.professionalgroup_id;
		
		return result;
	}
	
}
