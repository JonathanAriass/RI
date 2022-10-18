package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.assembler.MechanicAssembler;

public class MechanicGatewayImpl implements MechanicGateway {

	private static String TMECHANICS_ADD = "insert into TMechanics(id, dni, name, surname, version) values (?, ?, ?, ?, ?)";
	private static String TMECHANICS_DELETE = "delete from TMechanics where id = ?";
	private static String TMECHANICS_UPDATE =  "update TMechanics set name = ?, surname = ?, version = version+1 where id = ?";
	private static String TMECHANICS_FINDBYDNI = "select * from TMECHANICS where dni = ?";
	private static String TMECHANICS_FINDBYID = "select id, dni, name, surname, version from TMechanics where id = ?";
	private static String TMECHANICS_FINDALL = "select id, dni, name, surname, version from TMechanics";
	
	@Override
	public void add(MechanicDALDto mechanic) {
		PreparedStatement pst = null;
		Connection c = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			pst = c.prepareStatement(TMECHANICS_ADD);
			pst.setString(1, mechanic.id);
			pst.setString(2, mechanic.dni);
			pst.setString(3, mechanic.name);
			pst.setString(4, mechanic.surname);
			pst.setLong(5, mechanic.version);
	
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

			pst = c.prepareStatement(TMECHANICS_DELETE);
			pst.setString(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public void update(MechanicDALDto t) {
		Connection c = null;
		PreparedStatement pst = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos

			pst = c.prepareStatement(TMECHANICS_UPDATE);
			pst.setString(1, t.name);
			pst.setString(2, t.surname);
			pst.setString(3, t.id);
			
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public Optional<MechanicDALDto> findById(String id) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			
			pst = c.prepareStatement(TMECHANICS_FINDBYID);
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return MechanicAssembler.toMechanicDALDto(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

	@Override
	public List<MechanicDALDto> findAll() {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			
			pst = c.prepareStatement(TMECHANICS_FINDALL);
			rs = pst.executeQuery();
			
			return MechanicAssembler.toMechanicDALDtoList(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

	@Override
	public Optional<MechanicDALDto> findByDni(String dni) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			
			pst = c.prepareStatement(TMECHANICS_FINDBYDNI);
			pst.setString(1, dni);
			
			rs = pst.executeQuery();
			
			return MechanicAssembler.toMechanicDALDto(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

	
	
}
