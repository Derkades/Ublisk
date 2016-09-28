package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.enums.Clazz;
import com.robinmc.ublisk.utils.variable.Message;

public class CheckShield extends BukkitRunnable {

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()){
			PlayerInventory inv = player.getInventory();
			if (inv.getItemInOffHand().getType() == Material.SHIELD && !(Clazz.getClass(player) == Clazz.PALADIN)){
				player.sendMessage(Message.CLASS_WRONG_WEAPON.get());
				player.setHealth(0.5);
			}
		}
	}

}
