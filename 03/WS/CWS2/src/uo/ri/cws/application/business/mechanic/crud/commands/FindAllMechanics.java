package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;

public class FindAllMechanics {

	private static final String SQL = "select id, dni, name, surname, version from TMechanics";
	private static final String URL = "jdbc:hsqldb:hsql://localhost:1522/";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public FindAllMechanics() {
		
	}
	
	public List<MechanicBLDto> execute() {
		List<MechanicBLDto> result = new ArrayList<MechanicBLDto>();
		
		try {
			c = DriverManager.getConnection(URL, USER, PASSWORD);
			c.setAutoCommit(false);
			
			pst = c.prepareStatement(SQL);
			
			rs = pst.executeQuery();
			
			result = MechanicAssembler.toMechanicDtoList(rs);

			c.commit();
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
		
	}
	
}
