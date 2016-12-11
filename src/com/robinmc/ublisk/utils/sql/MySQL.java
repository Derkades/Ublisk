package com.robinmc.ublisk.utils.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.robinmc.ublisk.utils.DataFile;

public class MySQL {
	
	protected static Connection connection;
	
	public synchronized static void closeConnection() throws SQLException {
		connection.close();
	}
	
	public static void onDisable(){
		if (connection != null){
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized static void openConnection() throws SQLException {
		String ip = "localhost";
		int port = 3306;
		String user = DataFile.MYSQL.getString("user");
		String pass = DataFile.MYSQL.getString("password"); //Admit it, you hoped that the password would be here in plain text. Nope!
		String db = "ublisk";
		connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, user, pass);	
	}
	
	public static PreparedStatement prepareStatement(String sql) throws SQLException {
		return MySQL.connection.prepareStatement(sql);
	}
}
