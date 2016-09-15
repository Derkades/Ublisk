package com.robinmc.ublisk.utils.chat;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.utils.variable.Message;

import net.md_5.bungee.api.ChatColor;

@Deprecated
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
		Player sender = getLastSender(player).getPlayer();
		player.sendMessage(Message.prefix("Private Message") + sender.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + ChatColor.BOLD + msg);
		sender.sendMessage(Message.prefix("Private Message") + ChatColor.AQUA + " -> " + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + ChatColor.BOLD + msg);
	}

}
