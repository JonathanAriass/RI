package uo.ri.cws.application.persistence.professionalgroup.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;

public class ProfessionalGroupAssembler {

	public static Optional<ProfessionalGroupDALDto> toProfessionalGroupDALDto(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToProfessionalGroupDALDto(m));
		}
		else 	
			return Optional.ofNullable(null);
	}
	

	public static List<ProfessionalGroupDALDto> toProfessionalGroupDALDtoList(ResultSet rs) throws SQLException {
		List<ProfessionalGroupDALDto> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToProfessionalGroupDALDto(rs));
		}

		return res;
	}

	private static ProfessionalGroupDALDto resultSetToProfessionalGroupDALDto(ResultSet rs) throws SQLException {
		ProfessionalGroupDALDto value = new ProfessionalGroupDALDto();
		value.id = rs.getString("id");
		value.version = rs.getLong("version");
		
		value.name = rs.getString("name");
		value.productivity_bonus_percentage = rs.getDouble("productivitybonuspercentage");
		value.triennium_payment = rs.getDouble("trienniumpayment");
		return value;
	}
	
}
