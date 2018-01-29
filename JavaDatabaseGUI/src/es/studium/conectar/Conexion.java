package es.studium.conectar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/jugueteria?useSSL=false";
	static String login = "root";
	static String password = "studium2017";
	static Connection connection = null;

	public static void datosConexion() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Se ha producido un error al cargar el Driver");
		} catch (SQLException e) {
			System.out.println("Se produjo un error al conectar a la Base de Datos");
		}
	}

	public static Connection getConexion() {
		return connection;
	}
}
