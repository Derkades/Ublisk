package com.robinmc.ublisk.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.Music;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.NotSetException;
import com.robinmc.ublisk.utils.settings.Setting;
import com.xxmicloxx.NoteBlockAPI.SongEndEvent;

public class SongEnd implements Listener {
	
	@EventHandler
	public void musicStopped(SongEndEvent event){
		try {
		    for (String playername : event.getSongPlayer().getPlayerList()){
				UPlayer player = UPlayer.get(playername);
				try {
					if (player.getSetting(Setting.PLAY_MUSIC)){
						Music.playSong(player.getPlayer(), player.getTown().getName().toLowerCase());
					}
				} catch (NotSetException e){
					Music.playSong(player.getPlayer(), player.getTown().getName().toLowerCase());
				}
		    }
		} catch (Exception e) {
			Logger.log(LogLevel.WARNING, "Music", "Tried to play new song but player has already logged out");
		}
	}
}
