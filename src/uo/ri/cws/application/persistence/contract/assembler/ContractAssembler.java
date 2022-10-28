package uo.ri.cws.application.persistence.contract.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

public class ContractAssembler {

	public static Optional<ContractDALDto> toContractDALDto(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToContractDALDto(m));
		}
		else 	
			return Optional.ofNullable(null);
	}
	

	public static List<ContractDALDto> toContractDALDtoList(ResultSet rs) throws SQLException {
		List<ContractDALDto> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToContractDALDto(rs));
		}

		return res;
	}

	private static ContractDALDto resultSetToContractDALDto(ResultSet rs) throws SQLException {
		ContractDALDto value = new ContractDALDto();
		value.id = rs.getString("id");
		value.version = rs.getLong("version");
		
		if (rs.getDate("enddate") != null) {
			value.enddate = rs.getDate("enddate").toLocalDate();			
		}
		value.startdate = rs.getDate("startdate").toLocalDate();
		value.annualbasewage = rs.getDouble("annualbasewage");
		value.settlement = rs.getDouble("settlement");
//		if (rs.getString("state") == "IN_FORCE") {
//			value.state = ContractState.IN_FORCE;			
//		} else if (rs.getString("state") == "TERMINATED") {
//			value.state = ContractState.TERMINATED;		
//		}
		value.state = ContractState.valueOf(rs.getString("state"));
		value.contracttype_id = rs.getString("contracttype_id");
		value.mechanic_id = rs.getString("mechanic_id");
		value.professionalgroup_id = rs.getString("professionalgroup_id");
		return value;
	}
	
}
