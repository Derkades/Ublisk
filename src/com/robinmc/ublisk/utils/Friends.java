package com.robinmc.ublisk.utils;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Main;

public class Friends {
	
	public static boolean addFriend(Player player, Player newFriend){
		final List<String> list = Main.getInstance().getConfig().getStringList("friends." + player.getUniqueId());
		if (list.contains(newFriend.getUniqueId().toString())){
			return false;
		} else {
			list.add(newFriend.getUniqueId().toString());
			Main.getInstance().getConfig().set("friends." + player.getUniqueId(), list);
			return true;
		}
	}
	
	public static boolean removeFriend(Player player, int index){
		//Config.removeFromList("friends." + player.getUniqueId(), index);
		final List<String> list = Main.getInstance().getConfig().getStringList("friends." + player.getUniqueId());
		if (list.size() >= index){
			list.remove(index);
			Main.getInstance().getConfig().set("friends." + player.getUniqueId(), list);
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean removeFriend(Player player, OfflinePlayer friendToRemove){
		//Config.removeFromList("friends." + player.getUniqueId(), friendToRemove.getUniqueId());
		final List<String> list = Main.getInstance().getConfig().getStringList("friends." + player.getUniqueId());
		if (list.contains(friendToRemove.getUniqueId().toString())){			
			list.remove(friendToRemove.getUniqueId().toString());
			Main.getInstance().getConfig().set("friends." + player.getUniqueId(), list);
			return true;
		} else {
			return false;
		}
	}
	
	public static List<String> get(Player player){
		final List<String> list = Main.getInstance().getConfig().getStringList("friends." + player.getUniqueId());
		return list;
	}
	
}
