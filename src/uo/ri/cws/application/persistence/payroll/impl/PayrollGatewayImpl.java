package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.assembler.PayrollAssembler;

public class PayrollGatewayImpl implements PayrollGateway {

	private String TPAYROLL_FINDPAYROLLBYCONTRACTID = "SELECT * FROM TPAYROLL WHERE contract_id = ?";
	
	@Override
	public void add(PayrollDALDto t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(PayrollDALDto t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<PayrollDALDto> findById(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<PayrollDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PayrollDALDto> findPayrollByContractId(String contractId) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			
			pst = c.prepareStatement(TPAYROLL_FINDPAYROLLBYCONTRACTID);
			pst.setString(1, contractId);
			
			rs = pst.executeQuery();
			
			return PayrollAssembler.toPayrollDALDto(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

}
