package xyz.derkades.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import xyz.derkades.ublisk.Town;
import xyz.derkades.ublisk.ext.com.xxmicloxx.noteblockapi.SongEndEvent;
import xyz.derkades.ublisk.ext.com.xxmicloxx.noteblockapi.SongPlayer;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.exception.PlayerNotFoundException;
import xyz.derkades.ublisk.utils.settings.Setting;

public class SongEnd implements Listener {
	
	@EventHandler
	public void musicStopped(SongEndEvent event){
		try {
			SongPlayer songPlayer = event.getSongPlayer();
			
		    for (String playername : songPlayer.getPlayerList()){
		    	UPlayer player = new UPlayer(playername);
		    	if (player.getSetting(Setting.PLAY_MUSIC)){
					Town town = player.getTown();
					if (town != null){
						town.playThemeToPlayer(player);
					}
				}
		    }
		    
		    songPlayer.destroy();
		} catch (PlayerNotFoundException e) {
			Logger.log(LogLevel.WARNING, "Music", "Tried to play new song but player has already logged out");
		}
	}
}
