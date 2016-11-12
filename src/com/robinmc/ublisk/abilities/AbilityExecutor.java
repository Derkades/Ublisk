package com.robinmc.ublisk.abilities;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;

public abstract class AbilityExecutor {
	
	public abstract void doAbility(UPlayer player);

	public abstract int getCooldown();
	
	private final HashMap<UUID, Integer> cooldown = new HashMap<UUID, Integer>();
	
	public void startCooldown(final UPlayer player){
		cooldown.put(player.getUniqueId(), getCooldown());
		new BukkitRunnable(){
			public void run(){
				cooldown.put(player.getUniqueId(), cooldown.get(cooldown.get(player.getUniqueId())));
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}
	
	public boolean hasCooldown(UPlayer player){
		return cooldown.get(player.getUniqueId()) == 0;
	}
	
}
