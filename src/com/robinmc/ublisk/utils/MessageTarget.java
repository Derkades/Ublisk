package com.robinmc.ublisk.utils;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.HashMaps;

public class MessageTarget {
	
	private Player player;
	
	public MessageTarget(Player player){
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void setLastSender(Player lastSender){
		HashMaps.lastMessageSender.put(player, lastSender);
	}
	
	public static MessageTarget getLastSender(Player player){
		return new MessageTarget(HashMaps.lastMessageSender.get(player));
	}
	
	public void sendMessage(String msg){
		// TODO Fancy format
		player.sendMessage(player.getName() + ": " + msg);
	}

}
