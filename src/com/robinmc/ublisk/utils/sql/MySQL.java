package com.robinmc.ublisk.utils.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.robinmc.ublisk.utils.DataFile;

@Deprecated
public class MySQL {
	
	@Deprecated
	protected static Connection connection;
	
	@Deprecated
	public synchronized static void closeConnection() throws SQLException {
		connection.close();
	}
	
	@Deprecated
	public static void onDisable(){
		if (connection != null){
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Deprecated
	public synchronized static void openConnection() throws SQLException {
		String ip = "localhost";
		int port = 3306;
		String user = DataFile.MYSQL.getString("user");
		String pass = DataFile.MYSQL.getString("password"); //Admit it, you hoped that the password would be here in plain text. Nope!
		String db = "ublisk";
		connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, user, pass);	
	}
	
	@Deprecated
	public static PreparedStatement prepareStatement(String sql) throws SQLException {
		return MySQL.connection.prepareStatement(sql);
	}

}
