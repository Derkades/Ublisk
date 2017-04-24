package com.robinmc.ublisk.commands.ublisk;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;

public class RemoveInvisibleCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		final Block block = player.getLocation().getBlock();
		block.setType(Material.STONE);
		new BukkitRunnable() {

			public void run() {
				block.setType(Material.AIR);
			}
			
		}.runTaskLater(Main.getInstance(), 5);
	}

	@Override
	protected String getDescription() {
		return "Removes invisible blocks (stand inside the invisible block)";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"rib", "removeinvisibleblock", "rinv"};
	}

}
