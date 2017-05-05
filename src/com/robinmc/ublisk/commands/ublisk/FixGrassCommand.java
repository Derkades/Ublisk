package com.robinmc.ublisk.commands.ublisk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.robinmc.ublisk.utils.UPlayer;

import net.md_5.bungee.api.ChatColor;

public class FixGrassCommand extends UbliskCommand {

	private static final List<Material> TRANSPARENT_BLOCKS = Arrays.asList(
			Material.COBBLE_WALL,
			Material.FENCE,
			Material.FENCE_GATE,
			Material.ACACIA_FENCE,
			Material.ACACIA_FENCE_GATE,
			Material.BIRCH_FENCE,
			Material.BIRCH_FENCE_GATE,
			Material.DARK_OAK_FENCE,
			Material.DARK_OAK_FENCE_GATE,
			Material.IRON_FENCE,
			Material.JUNGLE_FENCE,
			Material.JUNGLE_FENCE_GATE,
			Material.NETHER_FENCE,
			Material.SPRUCE_FENCE,
			Material.SPRUCE_FENCE_GATE,
			Material.IRON_TRAPDOOR,
			Material.TRAP_DOOR,
			Material.IRON_DOOR_BLOCK,
			Material.WOODEN_DOOR,
			Material.WOOD_DOOR,
			Material.ACACIA_DOOR,
			Material.BIRCH_DOOR,
			Material.DARK_OAK_DOOR,
			Material.JUNGLE_DOOR,
			Material.SPRUCE_DOOR,
			Material.LEAVES,
			Material.LEAVES_2,
			Material.ANVIL,
			Material.TORCH,
			Material.REDSTONE_TORCH_OFF,
			Material.REDSTONE_TORCH_ON,
			Material.GLASS,
			Material.THIN_GLASS,
			Material.STAINED_GLASS,
			Material.STAINED_GLASS_PANE,
			Material.END_ROD
	);
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCommand(UPlayer player, String[] args) {
		Location location = player.getLocation();
		List<Block> blocks = new ArrayList<Block>();
		for (int x = -10; x < 11; x++){
			for (int y = -10; y < 11; y++){
				for (int z = -10; z < 11; z++){
					blocks.add(new Location(location.getWorld(), location.getX() + x, location.getY() + y, location.getZ() + z).getBlock());
				}
			}
		}
		
		int changed = 0;
		for (Block block : blocks){
			if (block.getType() == Material.GRASS){
				// If the block above a grass block is solid or if it's under water, change grass to dirt
				if (block.getRelative(BlockFace.UP).getType().isSolid() ||
						block.getRelative(BlockFace.UP).getType() == Material.WATER || 
						block.getRelative(BlockFace.UP).getType() == Material.STATIONARY_WATER){
					if (TRANSPARENT_BLOCKS.contains(block.getRelative(BlockFace.UP).getType())){
						return;
					}
					block.setType(Material.DIRT);
					changed++;
				}
			} else if (block.getType() == Material.DIRT && block.getData() == 0){
				if (block.getRelative(BlockFace.UP).getType() == Material.WATER || 
						block.getRelative(BlockFace.UP).getType() == Material.STATIONARY_WATER){
					// Don't do anything if the dirt block is under water
					continue;
				}
				
				// If the block above the dirt block is not solid, change dirt to grass
				if (!block.getRelative(BlockFace.UP).getType().isSolid() ||
						TRANSPARENT_BLOCKS.contains(block.getRelative(BlockFace.UP).getType())){
					block.setType(Material.GRASS);
					changed++;
				}
			}
		}
		
		player.sendMessage(ChatColor.AQUA + "Changed " + changed + " blocks.");
	}

	@Override
	protected String getDescription() {
		return "Fixes grass in a 10x10x10 area";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"fixgrass", "grass", "growgrass"};
	}

}
