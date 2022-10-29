package uo.ri.cws.application.persistence.contract;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

public interface ContractGateway extends Gateway<ContractDALDto> {

	Optional<ContractDALDto> findByMechanic(String mechanicid);
	List<String> findMechanicsIdWithContract();
	List<String> findMechanicsIdWithContractType(String name);
	List<ContractDALDto> findByProfessionalGroupId(String groupid);
	List<String> findMechanicsForProfessionalGroupsName(String id);
	String findProfessionaGroupByContractId(String id);
	
	public class ContractDALDto {
		public String id;
		public Long version;
		
		public double annualbasewage;
		public double settlement;
		public LocalDate startdate;
		public LocalDate enddate;
		public String contracttype_id;
		public ContractState state;
		public String mechanic_id;
		public String professionalgroup_id;
	}
	
}
