package com.robinmc.ublisk.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
	
	public static Connection getNewDatabaseConnection() throws SQLException {
		Logger.log(LogLevel.DEBUG, "Something asked for a new MySQL connection!");
		String ip = "localhost"; //TODO Use Var class to get database info
		int port = 3306;
		String user = DataFile.MYSQL.getString("user");
		String pass = DataFile.MYSQL.getString("password"); //Admit it, you hoped that the password would be here in plain text. Nope!
		String db = "ublisk";
		return DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, user, pass);	
	}


}
