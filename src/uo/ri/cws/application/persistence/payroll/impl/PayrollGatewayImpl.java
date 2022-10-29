package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.persistence.util.Conf;

public class PayrollGatewayImpl implements PayrollGateway {

	@Override
	public void add(PayrollDALDto t) {
		PreparedStatement pst = null;
		Connection c = null;
		
		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TPAYROLLS_ADD"));
			pst.setString(1, t.id);
			pst.setDouble(2, t.bonus);
			pst.setDate(3, java.sql.Date.valueOf(t.date));
			pst.setDouble(4, t.incometax);
			pst.setDouble(5, t.monthlywage);
			pst.setDouble(6, t.nic);
			pst.setDouble(7, t.productivitybonus);
			pst.setDouble(8, t.trienniumpayment);
			pst.setLong(9, t.version);
			pst.setString(10, t.contractid);
	
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public void remove(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos

			pst = c.prepareStatement(Conf.getInstance().getProperty("TPAYROLLS_REMOVE"));
			pst.setString(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public void update(PayrollDALDto t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<PayrollDALDto> findById(String id) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TPAYROLLS_FINDBYID"));
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return PayrollAssembler.toPayrollDALDto(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

	@Override
	public List<PayrollDALDto> findAll() {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TPAYROLLS_FINDALL"));
			rs = pst.executeQuery();
			
			return PayrollAssembler.toPayrollDALDtoList(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

	@Override
	public List<PayrollDALDto> findPayrollByContractId(String contractId) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty(
					"TPAYROLLS_FINDPAYROLLBYCONTRACTID"));
			pst.setString(1, contractId);
			
			rs = pst.executeQuery();
			
			return PayrollAssembler.toPayrollDALDtoList(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

}
