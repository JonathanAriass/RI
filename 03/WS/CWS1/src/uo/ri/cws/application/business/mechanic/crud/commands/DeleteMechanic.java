package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;

public class DeleteMechanic {

	private static final String SQL = "delete from TMechanics where id = ?";
	private static final String URL = "jdbc:hsqldb:hsql://localhost:1522/";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	
	private Connection c = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	private String id = "";
	
	public DeleteMechanic(String id) {
		validate(id);
		this.id = id;
	}

	public void execute() throws BusinessException {
		try {
			c = DriverManager.getConnection(URL, USER, PASSWORD);
			c.setAutoCommit(false);
		
			// Check if mechanic exist with this id
			mechanicExists(id);
			notWorkorders(id);
			deleteMechanic(id);

			pst.executeUpdate();
			
			c.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
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
	
	private void notWorkorders(String id2) throws SQLException, BusinessException {
		String q = "select * from TWORKORDERS where mechanic_id = ?";
		pst = c.prepareStatement(q);
		pst.setString(1, id);
		rs = pst.executeQuery();

		if (rs.next()) {
			throw new BusinessException("Mechanic has undone works on this moment");
		}
	}
	
	private void deleteMechanic(String id) throws SQLException {
		pst = c.prepareStatement(SQL);
		pst.setString(1, id);
		pst.executeUpdate();
	}

	private void validate(String arg) {
		// usar clase del proyecto util Argumen
		Argument.isNotEmpty(arg, "Null or empty id");
	}
	
}
