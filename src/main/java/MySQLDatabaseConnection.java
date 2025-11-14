import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;

public class MySQLDatabaseConnection {

	public static Connection getDatabaseConnection(ServletContext context) {
		Connection con;
		PreparedStatement ps;
		try {
			String mySqlUrl = (String) context.getInitParameter("mysqlURL");
			String databasename = (String) context.getInitParameter("databasename");
			String username = (String) context.getInitParameter("databaseusername");
			String password = (String) context.getInitParameter("databasepassword");
			String drivername = (String) context.getInitParameter("databasedrivername");
			Class.forName(drivername);
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb", "root", "Manoj@2004");
			return con;
		} catch (Exception e) {
			return null;
		}
	}
}