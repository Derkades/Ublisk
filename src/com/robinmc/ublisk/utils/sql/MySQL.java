package com.robinmc.ublisk.utils.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.robinmc.ublisk.utils.DataFile;

public class MySQL {
	
	protected static Connection connection;

	public static DatabaseConnection getConnection(){
		return new DatabaseConnection("192.168.0.125", 3306, getUser(), getPassword(), "ublisk");
	}
	
	private static String getUser(){
		//return Config.getString("mysql.user");
		return DataFile.MYSQL.getString("user");
	}

	private static String getPassword(){
		//return Config.getString("mysql.password");
		//Admit it, you hoped that the password would be here in plain text. Nope!
		return DataFile.MYSQL.getString("password");
	}
	
	public synchronized static void openConnection(DatabaseConnection dbCon) throws SQLException {
		String ip = dbCon.getIP();
		int port = dbCon.getPort();
		String user = dbCon.getUser();
		String pass = dbCon.getPassword();
		String db = dbCon.getDatabase();
		connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, user, pass);
	}
	
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

	public static void openConnection() throws SQLException {
		openConnection(getConnection());		
	}
	
	public static PreparedStatement prepareStatement(String sql) throws SQLException {
		return MySQL.connection.prepareStatement(sql);
	}
}
