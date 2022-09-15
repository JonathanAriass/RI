package uo.ri.tema1;

// Instalacion de drives de JDBC
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejemplo1 {

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
			// Esto es una mala practica puesto que se esta realizando la fase de compilacion 2 veces
			// siendo la misma query (proceso muy costoso)
			String query = String.format("SELECT * FROM TVEHICLES where model = '%s'", "POLO");
			resultSet = statement.executeQuery(query);
			query = String.format("SELECT * FROM TVEHICLES where model = '%s'", "PANDA");
			resultSet = statement.executeQuery(query);
			
			
			// Ahora mismo ya se creo la conexion y se ejecuto la sentencia pero ahora
			// es necesario recoger los resultados con ResultSet
			String plateNum;
			while(resultSet.next()) {
				plateNum = resultSet.getString("PLATENUMBER");
				System.out.println(plateNum);
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
