package restaurant_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
	
	
	private static GetConnection connectionobj = null;

	private GetConnection() {

	}
	public static GetConnection getinstance() {
		if(connectionobj == null) {
			return new GetConnection();
		}
		return connectionobj;
		
	}
		
	Connection getConnection() throws SQLException, ClassNotFoundException {
			Connection connection = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "YourPassword");
			System.out.println("Connection created.......");
		return connection;

	}

}
