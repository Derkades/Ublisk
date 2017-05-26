package com.robinmc.ublisk.modules;

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
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent event){
		setCorrectMaxHealth(new UPlayer(event));
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void levelUp(PlayerLevelChangeEvent event){
		setCorrectMaxHealth(new UPlayer(event));
	}
	
	public static void setCorrectMaxHealth(UPlayer player){
		player.setMaxHealth(calculateHealth(player.getLevel()));
	}
	
	public static int calculateHealth(int level){
		return level * HEALTH_INCREMENT + 1;
	}
	
	private static class UpdateHealthTask extends URunnable {
		
		@Override
		public void run(){
			for (UPlayer player : Ublisk.getOnlinePlayers()){
				setCorrectMaxHealth(player);
			}
		}
		
	}

}
