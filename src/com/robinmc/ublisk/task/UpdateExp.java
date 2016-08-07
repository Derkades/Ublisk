package com.robinmc.ublisk.task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.Task;
import com.robinmc.ublisk.utils.sql.MySQL;

public class UpdateExp implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
		        try {
		        	MySQL.openConnection();
		        	for (Player player : Bukkit.getOnlinePlayers()){
		        		if (player.getGameMode() == GameMode.ADVENTURE){
			        		Console.sendMessage("[MobExp] Updating XP in database for player " + player.getName());
			        		
			        		PreparedStatement sql2 = MySQL.prepareStatement("SELECT * FROM `exp` WHERE uuid=?;");
			    			sql2.setString(1, player.getUniqueId().toString());
			    			ResultSet resultSet = sql2.executeQuery();
			    			boolean containsPlayer = resultSet.next();
			    			
			    			sql2.close();
			    			resultSet.close();
				        	
				        	if (containsPlayer){
				        		int xp = Exp.get(player);
				        		PreparedStatement updatexp = MySQL.prepareStatement("UPDATE `exp` SET count=? WHERE uuid=?;");
				        		updatexp.setInt(1, xp + 1);
				        		updatexp.setString(2, player.getUniqueId().toString());
				        		updatexp.executeUpdate();
				        		
				        		PreparedStatement name = MySQL.prepareStatement("UPDATE `exp` SET name=? where uuid=?;");
				        		name.setString(1, player.getName());
				        		name.setString(2, player.getUniqueId().toString());
				        		name.executeUpdate();
				        		
				        		name.close();
				        		updatexp.close();
				        	} else {
				        		PreparedStatement newplayer = MySQL.prepareStatement("INSERT INTO `exp` values(?, 0, ?);");
				        		newplayer.setString(1, player.getUniqueId().toString());
				        		newplayer.setString(2, player.getName());
				        		newplayer.execute();
				        		newplayer.close();
				        	}
		        		}
		        	}
		        } catch (Exception e){
		        	e.printStackTrace();
		        } finally {
		        	try {
						MySQL.closeConnection();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		        }
			}
		}, 5*20, 1*60*20);
	}

}
