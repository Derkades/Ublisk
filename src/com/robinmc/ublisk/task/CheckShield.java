package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.enums.Clazz;
import com.robinmc.ublisk.utils.scheduler.Task;
import com.robinmc.ublisk.utils.variable.Message;

public class CheckShield implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player : Bukkit.getOnlinePlayers()){
					PlayerInventory inv = player.getInventory();
					if (inv.getItemInOffHand().getType() == Material.SHIELD && !(Clazz.getClass(player) == Clazz.PALADIN)){
						player.sendMessage(Message.CLASS_WRONG_WEAPON.get());
						player.setHealth(0.5);
					}
				}
			}
		}, 5*20, 5*20);
	}

}
