package com.robinmc.ublisk.task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.exception.ConnectionClosedException;
import com.robinmc.ublisk.utils.scheduler.Task;
import com.robinmc.ublisk.utils.sql.MySQL;
import com.robinmc.ublisk.utils.variable.Message;

public class CheckDoubleExp implements Task {

	@Override
	public void task(final Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
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
	    				startCooldown(plugin);
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
		}, 10*20, 10*20);
	}
	
	public void startCooldown(Main plugin){
		HashMaps.doubleExpCooldown.put(HashMaps.placeHolder(), true);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				HashMaps.doubleExpCooldown.put(HashMaps.placeHolder(), false);
			}
		}, 10*60*20);
	}

}
