package com.robinmc.ublisk.modules;

import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.URunnable;
import com.robinmc.ublisk.utils.Ublisk;

public class CustomHealth extends UModule {
	
	private static final int HEALTH_INCREMENT = 5;
	
	@Override
	public void onEnable(Main plugin){
		new UpdateHealthTask().runTimer(60*20); //Update health every minute just in case
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(final PlayerJoinEvent event){
		new URunnable(){
			public void run(){
				updateMaxHealth(new UPlayer(event));
			}
		}.runLater(20);
		
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void levelUp(PlayerLevelChangeEvent event){
		updateMaxHealth(new UPlayer(event));
	}
	
	public static void updateMaxHealth(UPlayer player){
		int health = getMaxHealth(player);
		player.setAttribute(Attribute.GENERIC_MAX_HEALTH, health);
		player.getPlayer().setHealthScale(20);
	}
	
	public static int getMaxHealth(UPlayer player){
		return player.getLevel() * HEALTH_INCREMENT + 1;
	}
	
	private static class UpdateHealthTask extends URunnable {
		
		@Override
		public void run(){
			for (UPlayer player : Ublisk.getOnlinePlayers()){
				updateMaxHealth(player);
			}
		}
		
	}

}
