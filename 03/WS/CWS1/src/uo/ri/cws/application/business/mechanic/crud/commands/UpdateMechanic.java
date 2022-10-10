package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;

public class UpdateMechanic {

	private static String SQL_UPDATE = 
			"update TMechanics " +
				"set name = ?, surname = ?, version = version+1 " +
				"where id = ?";
		private static final String URL = "jdbc:hsqldb:hsql://localhost:1522/";
		private static final String USER = "sa";
		private static final String PASSWORD = "";
		
		private Connection c = null;
		private PreparedStatement pst = null;
		private ResultSet rs = null;
		
		private MechanicBLDto mechanic = null;
		
		public UpdateMechanic(MechanicBLDto arg) {
			validate(arg);
			this.mechanic = arg;
		}
		
		public void execute() throws BusinessException {
			try {
				c = DriverManager.getConnection(URL, USER, PASSWORD);
				c.setAutoCommit(false);
				
				mechanicExists(mechanic.id);
				
				updateMechanic(mechanic);
				
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
		
		private void mechanicExists(String id) throws BusinessException, SQLException {
			String q = "select * from TMECHANICS where id = ?";

			pst = c.prepareStatement(q);
			pst.setString(1, id);
			rs = pst.executeQuery();

			if (!rs.next()) {
				throw new BusinessException("Mechanic doesn't exist");
			}
		}

		private void updateMechanic(MechanicBLDto mechanic) throws SQLException {
			pst = c.prepareStatement(SQL_UPDATE);
			pst.setString(1, mechanic.name);
			pst.setString(2, mechanic.surname);
			pst.setString(3, mechanic.id);
			
			pst.executeUpdate();
		}

		private void validate(MechanicBLDto arg) {
			// usar clase del proyecto util Argument
			Argument.isNotNull(arg, "Null mechanic");
			Argument.isNotEmpty(arg.id, "Null or empty id");
			Argument.isNotEmpty(arg.name, "Null or empty name");
			Argument.isNotEmpty(arg.surname, "Null or empty surname");
			
		}
	
}
