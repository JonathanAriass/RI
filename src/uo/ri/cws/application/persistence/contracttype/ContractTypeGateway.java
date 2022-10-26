package uo.ri.cws.application.persistence.contracttype;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeDALDto;


public interface ContractTypeGateway extends Gateway<ContractTypeDALDto> {
	
	Optional<ContractTypeDALDto> findContractTypeIdByName(String name);
	
	public class ContractTypeDALDto {
		public String id;
		public Long version;
		
		public double compensationdays;
		public String name;
	}
}
