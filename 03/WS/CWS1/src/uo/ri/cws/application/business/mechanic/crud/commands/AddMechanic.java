package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;

public class AddMechanic {

	private static String SQL = "insert into TMechanics(id, dni, name, surname, version) values (?, ?, ?, ?, ?)";
	private static final String URL = "jdbc:hsqldb:hsql://localhost:1522/";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	
	private Connection c = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	private MechanicBLDto mechanic = null;
	
	public AddMechanic(MechanicBLDto arg) {
		validate(arg);
		mechanic = arg;
	}
	
	public MechanicBLDto execute() throws BusinessException {
		// Process
		try {
			// DTO completed
			mechanic.id = UUID.randomUUID().toString();
			mechanic.version = 1L;
			
			
			c = DriverManager.getConnection(URL, USER, PASSWORD);

			/**
			 * Como se realiza mas de una consulta tenemos que garantizar que ambas se ejecuten como una transaccion
			 * por lo que debemos deshabilitar el auto-commit y tendremos que commitear al final para garantizar el commit.
			 */
			c.setAutoCommit(false);
			
			// Check UNIQUE DNI
			notRepeatedDni(mechanic.dni);
			
			insertMechanic(mechanic);
			
			c.commit();
			
			return mechanic;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					/* ignore */ }
			if (pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					/* ignore */ }
			if (c != null)
				try {
					c.close();
				} catch (SQLException e) {
					/* ignore */ }
		}
	}

	private void insertMechanic(MechanicBLDto mechanic) throws SQLException {
		pst = c.prepareStatement(SQL);
		pst.setString(1, mechanic.id);
		pst.setString(2, mechanic.dni);
		pst.setString(3, mechanic.name);
		pst.setString(4, mechanic.surname);
		pst.setLong(5, mechanic.version);

		pst.executeUpdate();
	}

	// Throw business exception if there is a mechanic with this DNI in DDBB
	private void notRepeatedDni(String dni) throws BusinessException, SQLException {
		String q = "select * from TMECHANICS where dni = ?";
		
		pst = c.prepareStatement(q);
		pst.setString(1, dni);
		rs = pst.executeQuery();
		
		if (rs.next()) {
			throw new BusinessException("Repeated DNI");
		}
	}

	private void validate(MechanicBLDto arg) {
		// usar clase del proyecto util Argumen
		Argument.isNotNull(arg, "Null mechanic");
		Argument.isNotEmpty(arg.dni, "Null or empty dni");
		Argument.isNotEmpty(arg.name, "Null or empty name");
		Argument.isNotEmpty(arg.surname, "Null or empty surname");
		
	}
	
}
