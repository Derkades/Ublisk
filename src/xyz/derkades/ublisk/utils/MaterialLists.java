package xyz.derkades.ublisk.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import xyz.derkades.derkutils.ListUtils;

public class MaterialLists {
	
	public static final List<Material> TRAPDOORS = Arrays.asList(
			Material.ACACIA_TRAPDOOR,
			Material.BIRCH_TRAPDOOR,
			Material.DARK_OAK_TRAPDOOR,
			Material.IRON_TRAPDOOR,
			Material.JUNGLE_TRAPDOOR,
			Material.OAK_TRAPDOOR,
			Material.SPRUCE_TRAPDOOR
	);
	
	public static final List<Material> FENCES = Arrays.asList(
			Material.ACACIA_FENCE,
			Material.BIRCH_FENCE,
			Material.DARK_OAK_FENCE,
			Material.JUNGLE_FENCE,
			Material.NETHER_BRICK_FENCE,
			Material.OAK_FENCE,
			Material.SPRUCE_FENCE
	);
	
	public static final List<Material> FENCE_GATES = Arrays.asList(
			Material.ACACIA_FENCE_GATE,
			Material.BIRCH_FENCE_GATE,
			Material.DARK_OAK_FENCE_GATE,
			Material.JUNGLE_FENCE_GATE,
			Material.OAK_FENCE_GATE,
			Material.SPRUCE_FENCE_GATE
	);
	
	public static final List<Material> WALLS = Arrays.asList(
			Material.COBBLESTONE_WALL, 
			Material.MOSSY_COBBLESTONE_WALL
	);
	
	public static final List<Material> DOORS = Arrays.asList(
			Material.ACACIA_DOOR,
			Material.BIRCH_DOOR,
			Material.DARK_OAK_DOOR,
			Material.JUNGLE_DOOR,
			Material.OAK_DOOR,
			Material.SPRUCE_DOOR
	);
	
	public static final List<Material> STAINED_GLASS_PANES = Arrays.asList();
	
	public static final List<Material> GLASS_PANES = Arrays.asList();
	
	public static final List<Material> TORCHES = Arrays.asList(
			Material.TORCH, 
			Material.REDSTONE_TORCH, 
			Material.REDSTONE_WALL_TORCH, 
			Material.WALL_TORCH);
	
	public static final List<Material> LEAVES = Arrays.asList(
			Material.ACACIA_LEAVES,
			Material.BIRCH_LEAVES,
			Material.DARK_OAK_LEAVES,
			Material.JUNGLE_LEAVES,
			Material.OAK_LEAVES,
			Material.SPRUCE_LEAVES
	);
	
	private static final List<Material> internal_OTHER_PARTIAL_BLOCKS = Arrays.asList(
			Material.ANVIL,
			Material.GLASS,
			Material.END_ROD,
			Material.SIGN
	);
	
	public static final List<Material> PARTIAL_BLOCKS = ListUtils.addToList(
			TRAPDOORS,
			FENCES,
			FENCE_GATES,
			WALLS,
			LEAVES,
			TORCHES,
			GLASS_PANES,
			internal_OTHER_PARTIAL_BLOCKS
	);
	
	public static final List<Material> FLUIDS = Arrays.asList(
			Material.WATER,
			Material.LAVA);
	
	public static final List<Material> WOOLS = Arrays.asList(
			Material.BLACK_WOOL,
			Material.BLUE_WOOL,
			Material.BROWN_WOOL,
			Material.CYAN_WOOL,
			Material.GRAY_WOOL,
			Material.GREEN_WOOL,
			Material.LIGHT_BLUE_WOOL,
			Material.LIGHT_GRAY_WOOL,
			Material.LIME_WOOL,
			Material.MAGENTA_WOOL,
			Material.ORANGE_WOOL,
			Material.PINK_WOOL,
			Material.PURPLE_WOOL,
			Material.RED_WOOL,
			Material.WHITE_WOOL,
			Material.YELLOW_WOOL);
	
	public static final List<Material> DYES = Arrays.asList(
			Material.INK_SAC,
			Material.LAPIS_LAZULI,
			Material.COCOA_BEANS,
			Material.CYAN_DYE,
			Material.GRAY_DYE,
			Material.CACTUS_GREEN,
			Material.LIGHT_BLUE_DYE,
			Material.LIGHT_GRAY_DYE,
			Material.LIME_DYE,
			Material.MAGENTA_DYE,
			Material.ORANGE_DYE,
			Material.PINK_DYE,
			Material.PURPLE_DYE,
			Material.ROSE_RED,
			Material.BONE_MEAL,
			Material.DANDELION_YELLOW);
	
}
