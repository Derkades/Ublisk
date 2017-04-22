package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;

public class PewPew extends Ability {

	public PewPew() {
		super(1, 0);
	}

	@Override
	public void run(final UPlayer player) {	
		new BukkitRunnable(){
			
			int i = 0;
			
			public void run(){
				player.sendMessage(i);
				
				i++;
				if (i > 5) this.cancel();
			}
			
		}.runTaskTimer(Main.getInstance(), 0, 1*20);	
	}

}
