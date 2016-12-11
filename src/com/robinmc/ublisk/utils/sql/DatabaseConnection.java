package com.robinmc.ublisk.utils.sql;

@Deprecated
public class DatabaseConnection {
	
	private String ip;
	private int port;
	private String user;
	private String password;
	private String database;
	
	public DatabaseConnection(String ip, int port, String user, String password, String database){
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
		this.database = database;
	}
	
	public DatabaseConnection(String ip, String user, String password, String database){
		this.ip = ip;
		this.port = 3306;
		this.user = user;
		this.password = password;
		this.database = database;
	}
	
	public DatabaseConnection(String user, String password, String database){
		this.ip = "localhost";
		this.port = 3306;
		this.user = user;
		this.password = password;
		this.database = database;
	}

	public String getIP(){
		return ip;
	}
	
	public int getPort(){
		return port;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getDatabase(){
		return database;
	}

}
