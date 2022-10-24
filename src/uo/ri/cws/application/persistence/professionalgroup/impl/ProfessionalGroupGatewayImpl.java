package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.assembler.ProfessionalGroupAssembler;

public class ProfessionalGroupGatewayImpl implements ProfessionalGroupGateway {

	private static String TPROFESSIONALGROUPS_ADD = "insert into TPROFESSIONALGROUPS(id, name, productivitybonuspercentage, trienniumpayment, version) values (?, ?, ?, ?, ?)";
	private static String TPROFESSIONALGROUPS_DELETE = "delete from TPROFESSIONALGROUPS where id = ?";
	private static String TPROFESSIONALGROUPS_UPDATE = "update TPROFESSIONALGROUPS set name = name, productivitybonuspercentage = ?, trienniumpayment = ?, version = version + 1 where id = ?";
	private static String TPROFESSIONALGROUPS_FINDBYID = "SELECT * FROM TPROFESSIONALGROUPS WHERE id = ?";
	private static String TPROFESSIONALGROUPS_FINDALL = "SELECT * FROM TPROFESSIONALGROUPS";
	private static String TPROFESSIONALGROUPS_FINDBYNAME = "SELECT * FROM TPROFESSIONALGROUPS WHERE name = ?";
	
	
	@Override
	public void add(ProfessionalGroupDALDto t) {
		PreparedStatement pst = null;
		Connection c = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			pst = c.prepareStatement(TPROFESSIONALGROUPS_ADD);
			pst.setString(1, t.id);
			pst.setString(2, t.name);
			pst.setDouble(3, t.productivity_bonus_percentage);
			pst.setDouble(4, t.triennium_payment);
			pst.setLong(5, t.version);
	
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

			pst = c.prepareStatement(TPROFESSIONALGROUPS_DELETE);
			pst.setString(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public void update(ProfessionalGroupDALDto t) {
		Connection c = null;
		PreparedStatement pst = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos

			pst = c.prepareStatement(TPROFESSIONALGROUPS_UPDATE);
			pst.setDouble(1, t.productivity_bonus_percentage);
			pst.setDouble(2, t.triennium_payment);
			pst.setString(3, t.id);
			
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public Optional<ProfessionalGroupDALDto> findById(String id) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			
			pst = c.prepareStatement(TPROFESSIONALGROUPS_FINDBYID);
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return ProfessionalGroupAssembler.toProfessionalGroupDALDto(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

	@Override
	public List<ProfessionalGroupDALDto> findAll() {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			
			pst = c.prepareStatement(TPROFESSIONALGROUPS_FINDALL);
			
			rs = pst.executeQuery();
			
			return ProfessionalGroupAssembler.toProfessionalGroupDALDtoList(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

	@Override
	public Optional<ProfessionalGroupDALDto> findByName(String name) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			
			pst = c.prepareStatement(TPROFESSIONALGROUPS_FINDBYNAME);
			pst.setString(1, name);
			
			rs = pst.executeQuery();
			
			return ProfessionalGroupAssembler.toProfessionalGroupDALDto(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

}
