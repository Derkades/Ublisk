package xyz.derkades.ublisk.modules;

import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.URunnable;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.inventory.Item;

public class CustomHealth extends UModule {
	
	private static final int HEALTH_INCREMENT = 18;
	
	@Override
	public void onEnable(){
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
		
		int bonusHealth = 0;
		for (Item item : player.getInventory().getItems(true, true, true)){
			double percentage = item.getHealthBonusPercentage() / 100.0;
			bonusHealth += bonusHealth * percentage;
		}
		
		int totalHealth = health + bonusHealth;
		player.setAttribute(Attribute.GENERIC_MAX_HEALTH, totalHealth);
		player.setHealthScale(20);
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
