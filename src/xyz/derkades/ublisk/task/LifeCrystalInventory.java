package xyz.derkades.ublisk.task;

import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.inventory.Item;

public class LifeCrystalInventory extends BukkitRunnable {

	@Override
	public void run(){
		for (final UPlayer player : Ublisk.getOnlinePlayers()){
			if (player.isInBuilderMode()){
				final Item helmet = new Item(Material.GOLDEN_HELMET).setName(ChatColor.GOLD + "Builder's Helmet");
				player.getInventory().setHelmet(helmet);
			} else {
				final Item menuChest = new Item(Material.CHEST)
						.setName(ChatColor.BLUE + "" + ChatColor.BOLD + "Chest")
						.setLore(ChatColor.GRAY + "Right click to open menu.", ChatColor.GRAY + "TIP: You can also use /menu")
						.setDroppable(false);
				player.getInventory().set(7, menuChest);

				final Item item = new Item(Material.NETHER_STAR)
						.setAmount(player.getLifeCrystals())
						.setName(ChatColor.BLUE + "Life Crystals: " + ChatColor.AQUA + player.getLifeCrystals())
						.setLore("You have " + player.getLifeCrystals() + " life crystals")
						.setDroppable(false);
				player.getInventory().set(8, item);
			}


		}
	}

}
