package com.robinmc.ublisk.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.scheduler.Scheduler;

public class CheckDoubleExp extends BukkitRunnable {

	@Override
	public void run(){
		try {
			Connection connection = null;
			PreparedStatement sql = null;
			ResultSet result = null;
			PreparedStatement sql1 = null;
			
			try {
				connection = Ublisk.getNewDatabaseConnection("Check double xp");
				sql = connection.prepareStatement("SELECT state FROM `bonus` WHERE type='doublexp_1';");
				result = sql.executeQuery();
				result.next();
				boolean doublexp = result.getBoolean(1);
				
				if (doublexp && Exp.DOUBLE_XP_COOLDOWN){
					Bukkit.broadcastMessage(Message.DOUBLE_XP_COOLDOWN.get());
				} else if (doublexp){
					startCooldown();
					Exp.DOUBLE_XP_ACTIVE = true;
				}
				
				sql1 = connection.prepareStatement("UPDATE bonus SET state=false WHERE type='doublexp_1'");
				sql1.executeUpdate();
			} catch (SQLException e){
				throw e;
			} finally {
				sql.close();
				result.close();
				sql1.close();
				connection.close();
			}
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Double XP", "An error occured while checking for double xp in database.");
		}
	}
	
	public void startCooldown(){
		Exp.DOUBLE_XP_COOLDOWN = true;
		Scheduler.runTaskLater(10*60*20, new Runnable(){
			public void run(){
				Exp.DOUBLE_XP_COOLDOWN = false;
			}
		});
	}

}
