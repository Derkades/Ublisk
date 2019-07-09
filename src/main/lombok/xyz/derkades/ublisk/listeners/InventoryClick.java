package xyz.derkades.ublisk.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.utils.UPlayer;

public class InventoryClick implements Listener {

	@EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
	public void tracker(final InventoryClickEvent event){
		final UPlayer player = new UPlayer(event.getWhoClicked());
		player.tracker(PlayerInfo.INV_CLICK);
	}

	@EventHandler
	public void onItemClick(final InventoryClickEvent event){

		final UPlayer player = new UPlayer(event.getWhoClicked());

		if (player.isInBuilderMode()){
			return;
		}

		if (event.getInventory() != null && event.getView().getTitle() != null && event.getView().getTitle().contains("Box")){
			event.setCancelled(true);
			return;
		}

		final Material[] cancel = {
				Material.NETHER_STAR,
				Material.CHEST
				};

		final Material clicked = event.getCurrentItem().getType();
		for (final Material material : cancel){
			if (clicked.equals(material)){
				event.setCancelled(true);
			}
		}
	}

}
