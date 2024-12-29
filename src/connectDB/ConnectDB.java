package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	public static Connection con = null;
	private static ConnectDB instance = new ConnectDB();
	
	public static ConnectDB getInstance() {
		return instance;
	}
	
	public void connect() throws Exception {
		String username = "sa";
		String password = "1234";
				
		String url = "jdbc:sqlserver://localhost:1434;databasename=QLKaraokeNice";
		con = DriverManager.getConnection(url, username, password);
	}
	
	public void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		return con;
	}
}
