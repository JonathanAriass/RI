package uo.ri.cws.application.persistence.contracttype.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeDALDto;

public class ContractTypeAssembler {

	public static Optional<ContractTypeDALDto> toContractTypeDALDto(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToContractTypeDALDto(m));
		}
		else 	
			return Optional.ofNullable(null);
	}
	

	public static List<ContractTypeDALDto> toContractTypeDALDtoList(ResultSet rs) throws SQLException {
		List<ContractTypeDALDto> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToContractTypeDALDto(rs));
		}

		return res;
	}

	private static ContractTypeDALDto resultSetToContractTypeDALDto(ResultSet rs) throws SQLException {
		ContractTypeDALDto value = new ContractTypeDALDto();
		value.id = rs.getString("id");
		value.version = rs.getLong("version");
		
		value.compensationdays = rs.getDouble("compensationdays");
		value.name = rs.getString("name");
		return value;
	}
	
}
