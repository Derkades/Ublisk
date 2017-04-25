package com.robinmc.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.Town;
import com.robinmc.ublisk.ext.com.xxmicloxx.noteblockapi.SongEndEvent;
import com.robinmc.ublisk.ext.com.xxmicloxx.noteblockapi.SongPlayer;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;
import com.robinmc.ublisk.utils.settings.Setting;

public class SongEnd implements Listener {
	
	@EventHandler
	public void musicStopped(SongEndEvent event){
		try {
			SongPlayer songPlayer = event.getSongPlayer();
			
		    for (String playername : songPlayer.getPlayerList()){
		    	UPlayer player = new UPlayer(playername);
		    	if (player.getSetting(Setting.PLAY_MUSIC)){
					Town town = player.getTown();
					town.playThemeToPlayer(player);
				}
		    }
		    
		    songPlayer.destroy();
		} catch (PlayerNotFoundException e) {
			Logger.log(LogLevel.WARNING, "Music", "Tried to play new song but player has already logged out");
		}
	}
}
