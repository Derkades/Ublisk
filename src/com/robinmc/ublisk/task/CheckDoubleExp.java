package com.robinmc.ublisk.task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.utils.exception.ConnectionClosedException;
import com.robinmc.ublisk.utils.scheduler.Scheduler;
import com.robinmc.ublisk.utils.sql.MySQL;
import com.robinmc.ublisk.utils.variable.Message;

public class CheckDoubleExp extends BukkitRunnable {

	@Override
	public void run(){
		try {
			MySQL.openConnection();
			PreparedStatement sql = MySQL.prepareStatement("SELECT state FROM `bonus` WHERE type='doublexp_1';");
			ResultSet rs = sql.executeQuery();
			rs.next();
			boolean doublexp = rs.getBoolean(1);
			sql.close();
			
			if (doublexp && HashMaps.doubleExpCooldown.get(HashMaps.placeHolder())){
				Bukkit.broadcastMessage(Message.DOUBLE_XP_COOLDOWN.get());
				PreparedStatement sql1 = MySQL.prepareStatement("UPDATE bonus SET state=false WHERE type='doublexp_1'");
				sql1.executeUpdate();
				sql1.close();
				return;
			} else if (doublexp){
				startCooldown();
				HashMaps.doublexp.put("hi", true);
				PreparedStatement sql1 = MySQL.prepareStatement("UPDATE bonus SET state=false WHERE type='doublexp_1'");
				sql1.executeUpdate();
				sql1.close();
			}

		} catch (SQLException | ConnectionClosedException e){
			e.printStackTrace();
		} finally {
			try {
				MySQL.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	public void startCooldown(){
		HashMaps.doubleExpCooldown.put(HashMaps.placeHolder(), true);
		Scheduler.runTaskLater(10*60*20, new Runnable(){
			public void run(){
				HashMaps.doubleExpCooldown.put(HashMaps.placeHolder(), false);
			}
		});
	}

}
