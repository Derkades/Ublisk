package xyz.derkades.ublisk.modules;

import java.util.Arrays;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.utils.MaterialLists;

public class ResetDoors extends UModule {
	
	@EventHandler(priority=EventPriority.LOW)
	public void resetDoors(PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK &&
				Arrays.asList(MaterialLists.TRAPDOORS).contains(event.getClickedBlock().getType())){
			
			final Block block = event.getClickedBlock();
			Player player = event.getPlayer();
			
			//If the trapdoor is one of the safe trapdoors, let the player open it.
			for (Location loc : Var.SAFE_TRAPDOORS){
				Block safe = loc.getBlock();
				if (block.getLocation().equals(safe.getLocation())){
					if (player.getGameMode() == GameMode.CREATIVE) player.sendMessage("Safe trapdoor!");
					return;
				}
			}
			
			//If player is holding a beetroot, let them open the trapdoor.
			if (player.getInventory().getItemInMainHand().getType() == Material.BEETROOT){
				player.sendMessage("Permanently closed trapdoor!");
				return;
			}
			
			//If the player is in creative mode, send them a message on how to permanently open the trapdoor.
			if (player.getGameMode() == GameMode.CREATIVE)
				player.sendMessage("Right click with a beetroot to permanently open or close a trapdoor. If this is a safe trapdoor, please add it to the list of safe trapdoors.");
			
			event.setCancelled(true);
		}
	}

}
