package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.utils.Console;
import com.xxmicloxx.NoteBlockAPI.SongEndEvent;

public class SongEnd implements Listener {
	
	@EventHandler
	public void musicStopped(SongEndEvent event){
		try {
		    for (String playername : event.getSongPlayer().getPlayerList()){
		        @SuppressWarnings("deprecation")
				Player player = Bukkit.getServer().getPlayer(playername);
		        player.sendMessage(Messages.songEnded());
		    }
		} catch (Exception e) {
			Console.sendMessage("[Music] Tried to send music stopped message but player has already logged out");
		}
	}
}
