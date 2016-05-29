package com.robinmc.ublisk.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.Messages;
import com.xxmicloxx.NoteBlockAPI.SongEndEvent;

public class SongEnd implements Listener {
	
	@EventHandler
	public void musicStopped(SongEndEvent event){
		Player player = (Player) event.getSongPlayer(); //FIXME: This doesn't work, find a solution
		player.sendMessage(Messages.songEnded());
	}
}
