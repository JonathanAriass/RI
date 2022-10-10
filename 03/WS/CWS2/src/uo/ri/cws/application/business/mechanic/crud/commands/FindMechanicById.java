package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;

public class FindMechanicById {

	private static final String SQL = "select id, dni, name, surname, version from TMechanics where id = ?";
	private static final String URL = "jdbc:hsqldb:hsql://localhost:1522/";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	
	private Connection c = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	private String id = "";
	
	public FindMechanicById(String arg) {
		validate(arg);
		this.id = arg;
	}
	
	public Optional<MechanicBLDto> execute() throws BusinessException {
		Optional<MechanicBLDto> result = null;
		
		try {
			c = DriverManager.getConnection(URL, USER, PASSWORD);
//			c.setAutoCommit(false);
		
			// Check if mechanic exist with this id
			mechanicExists(id);
			
			result = findMechanicById(id);
			
//			c.commit();
			
			return result ;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
	}
	
	
	private Optional<MechanicBLDto> findMechanicById(String id) throws SQLException {
		pst = c.prepareStatement(SQL);
		pst.setString(1, id);
		rs = pst.executeQuery();
		rs.next();
		MechanicBLDto m = MechanicAssembler.toMechanicDto(rs);
		return Optional.of(m);
	}

	private void mechanicExists(String id) throws SQLException, BusinessException {
		String q = "select * from TMECHANICS where id = ?";

		pst = c.prepareStatement(q);
		pst.setString(1, id);
		rs = pst.executeQuery();

		if (!rs.next()) {
			throw new BusinessException("Mechanic doesn't exist");
		}
	}
	private void validate(String arg) {
		// usar clase del proyecto util Argumen
		Argument.isNotEmpty(arg, "Null or empty id");
	}
	
}
