package xyz.derkades.ublisk.modules;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.PlayerInventory;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.URunnable;

public class RegeneratingBlocks extends UModule {
	
	private static final Material[] REGENERATING_MATERIALS = new Material[]{
			Material.IRON_ORE,
			Material.HAY_BLOCK
	};
	
	private static final int REGENERATE_TIME = 30;
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBreakBlock(BlockBreakEvent event){		
		final Player player = event.getPlayer();
		final PlayerInventory inv = player.getInventory();
		
		if (inv.getItemInMainHand().getType() == Material.BEETROOT && player.getGameMode() == GameMode.CREATIVE){
			player.sendMessage("Block permanently broken!");
			return;
		}
		
		final Block block = event.getBlock();
		
		for (Material regeneratingMaterial : REGENERATING_MATERIALS){
			if (block.getType() == regeneratingMaterial){
				if (!Var.BLOCK_REGENERATION_ENABLED){
					player.sendMessage(ChatColor.DARK_GREEN + "Block regeneration is disabled. This block will not turn back into its original state.");
					return;
				}
								
				Logger.log(LogLevel.INFO, "Regenerating block broken at (" + block.getX() + ", " + block.getY() + ", " + block.getZ() + ") by " + player.getName());
				
				final Material originalMaterial = block.getType();
				new URunnable() {
					public void run() {
						block.setType(originalMaterial);
					}
				}.runLater(REGENERATE_TIME * 20);
			}
		}	
	}


}
