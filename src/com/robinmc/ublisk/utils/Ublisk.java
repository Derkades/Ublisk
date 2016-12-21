package com.robinmc.ublisk.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Logger.LogLevel;

public class Ublisk {
	
	public static UPlayer[] getOnlinePlayers(){
		List<UPlayer> list = new ArrayList<UPlayer>();
		for (Player player : Bukkit.getOnlinePlayers()){
			list.add(new UPlayer(player));
		}
		return list.toArray(new UPlayer[0]);
	}
	
	/**
	 * Execute a command as the console (without the /)
	 * @param cmd The command to be executed
	 */
	public static void sendConsoleCommand(String cmd){
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
	}
	
	public static Connection getNewDatabaseConnection(String reason) throws SQLException {
		Logger.log(LogLevel.DEBUG, "New connection: " + reason);
		String ip = Var.DATABASE_HOST;
		int port = Var.DATABASE_PORT;
		String user = Var.DATABASE_USER;
		String pass = Var.DATABASE_PASSWORD;
		String db = Var.DATABASE_DB_NAME;
		return DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, user, pass);	
	}
	
	public static void dealWithException(Exception exception, String message){
		Logger.log(LogLevel.SEVERE, message);
		exception.printStackTrace();
	}


}
