package com.robinmc.ublisk.task;

import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Clazz;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class CheckShield extends BukkitRunnable {

	@Override
	public void run() {
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			PlayerInventory inv = player.getInventory();
			if (inv.getItemInMainHand().getType() == Material.SHIELD && !(player.getClazz() == Clazz.PALADIN)){
				player.sendMessage(Message.CLASS_WRONG_WEAPON);
				player.setHealth(0.5);
			}
		}
	}

}
