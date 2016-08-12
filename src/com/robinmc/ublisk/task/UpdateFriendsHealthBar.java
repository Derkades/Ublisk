package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Friends;
import com.robinmc.ublisk.utils.Task;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.enums.Setting;
import com.robinmc.ublisk.utils.exception.NotSetException;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;

import net.md_5.bungee.api.chat.TextComponent;

public class UpdateFriendsHealthBar implements Task {

	@Override
	public void task(final Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (final Player player : Bukkit.getOnlinePlayers()){
					try {
						if (!Setting.FRIENDS_SHOW_HEALTH.get(player)){
							return;
						}
					} catch (NotSetException e) {
						Setting.FRIENDS_SHOW_HEALTH.put(player, true);
					}
					
					if (player.getGameMode() == GameMode.CREATIVE){
						return;
					}
					
					for (String s : Friends.get(player)){
						OfflinePlayer friend = null;
						try {
							friend = UUIDUtils.getOfflinePlayerFromName(UUIDUtils.getNameFromIdString(s));
						} catch (PlayerNotFoundException e) {
							e.printStackTrace();
						}
						if (friend.isOnline()){
							Player onlineFriend = (Player) friend;
							TextComponent text = new TextComponent(onlineFriend.getName() + "'s health");
							double h = onlineFriend.getHealth();
							float p = 0;
							if (h == 1){
								p = 0.05f;
							} else if (h == 2){
								p = 0.1f;
							} else if (h == 3){
								p = 0.15f;
							} else if (h == 4){
								p = 0.2f;
							} else if (h == 5){
								p = 0.25f;
							} else if (h == 6){
								p = 0.3f;
							} else if (h == 7){
								p = 0.35f;
							} else if (h == 8){
								p = 0.4f;
							} else if (h == 9){
								p = 0.45f;
							} else if (h == 10){
								p = 0.5f;
							} else if (h == 11){
								p = 0.55f;
							} else if (h == 12){
								p = 0.6f;
							} else if (h == 13){
								p = 0.65f;
							} else if (h == 14){
								p = 0.7f;
							} else if (h == 15){
								p = 0.75f;
							} else if (h == 16){
								p = 0.8f;
							} else if (h == 17){
								p = 0.85f;
							} else if (h == 18){
								p = 0.9f;
							} else if (h == 19){
								p = 0.95f;
							} else {
								p = 1.0f;
							}
							
							final BossBar bossBar = BossBarAPI.addBar(
									player, 
									text, 
									BossBarAPI.Color.RED, 
									BossBarAPI.Style.NOTCHED_20,
									p, 
									20, 
									999);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
								public void run(){
									bossBar.removePlayer(player);
								}
							}, 2*20); // Remove after 5 seconds to make room for new bar
						}
					}
				}
			}
		}, 5*20, 2*20);
	}

}
