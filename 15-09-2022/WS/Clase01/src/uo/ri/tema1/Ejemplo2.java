package uo.ri.tema1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejemplo2 {

	private static Connection connection = null;
	private static Statement statement = null;
	
	// El objeto ResultSet es un proxy: se hace una carga perezosa
	// El objeto ResulSet es read-only y sigue el mismo principio que el cursor de sql
	private static ResultSet resultSet = null;
	
	public static void Main(String[] args) {
		
		// el metodo getConnection esta sobrecargado y se necesita pasarle la url de la base de datos
		// asi como la IP del servidor
		// La url de jdbc se formara con el siguiente codigo: jdbc::oracle:thin:@hostname:portnumber:sid
		// Para hsqldb sera:
		try {
			// Se carga el driver y realiza la conexion
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost");
			
			// Ahora se crea la sentencia
			statement = connection.createStatement();
			
			// Ahora se ejecuta la sentencia
			resultSet = statement.executeQuery("SELECT * FROM TWORKORDERS");
			
			// Ahora mismo ya se creo la conexion y se ejecuto la sentencia pero ahora
			// es necesario recoger los resultados con ResultSet

			String mechanic = null, vehicle = null, invoice = null;
			double money;
			while(resultSet.next()) {
				vehicle = resultSet.getString("VEHICLE_ID");
				mechanic = resultSet.getString("MECHANIC_ID");
				invoice = resultSet.getString("INVOICE_ID");
				money = resultSet.getDouble("AMOUNT");
				System.out.println(String.format("40% - 40%s - 40%s - 40%d", vehicle, mechanic, invoice, money));

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Si se cierra la connection se invalida el resto pero no se libera el recurso
			releaseResult(resultSet);
			releaseStatement(statement);
			releaseConnection(connection);
		}
	}

	private static void releaseResult(ResultSet arg) {
		try {
			arg.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void releaseStatement(Statement arg) {
		try {
			arg.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void releaseConnection(Connection arg) {
		try {
			arg.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
