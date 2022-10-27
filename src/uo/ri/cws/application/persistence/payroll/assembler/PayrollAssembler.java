package uo.ri.cws.application.persistence.payroll.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;

public class PayrollAssembler {

	public static Optional<PayrollDALDto> toPayrollDALDto(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToPayrollDALDto(m));
		}
		else 	
			return Optional.ofNullable(null);
	}
	

	public static List<PayrollDALDto> toPayrollDALDtoList(ResultSet rs) throws SQLException {
		List<PayrollDALDto> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToPayrollDALDto(rs));
		}

		return res;
	}

	private static PayrollDALDto resultSetToPayrollDALDto(ResultSet rs) throws SQLException {
		PayrollDALDto value = new PayrollDALDto();
		value.id = rs.getString("id");
		value.version = rs.getLong("version");
		
		value.bonus = rs.getDouble("bonus");
		value.date = rs.getDate("date").toLocalDate();
		value.incometax = rs.getDouble("incometax");
		value.monthlywage = rs.getDouble("monthlywage");
		value.nic = rs.getDouble("nic");
		value.productivitybonus = rs.getDouble("productivitybonus");
		value.trienniumpayment = rs.getDouble("trienniumpayment");
		value.contractid = rs.getString("contract_id");
		return value;
	}
	
}
