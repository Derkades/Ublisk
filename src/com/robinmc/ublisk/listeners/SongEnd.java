package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.enums.Music;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.xxmicloxx.NoteBlockAPI.SongEndEvent;

public class SongEnd implements Listener {
	
	@EventHandler
	public void musicStopped(SongEndEvent event){
		try {
		    for (String playername : event.getSongPlayer().getPlayerList()){
				Player player = Bukkit.getServer().getPlayer(playername);
		        try {
		        	if (Config.getBoolean("settings.music." + player.getUniqueId())){
		        		String town = Config.getString("last-town." + player.getUniqueId());
		 		        Music.playSong(player, town);
		        	} else {
		        		return;
		        	}
		        } catch (Exception e){
		        	 String town = Config.getString("last-town." + player.getUniqueId());
				     Music.playSong(player, town);
		        }	       
		    }
		} catch (Exception e) {
			Logger.log(LogLevel.WARNING, "Music", "Tried to play new song but player has already logged out");
		}
	}
}
