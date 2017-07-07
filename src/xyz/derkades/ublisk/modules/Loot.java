package xyz.derkades.ublisk.modules;

import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.RESET;
import static org.bukkit.ChatColor.YELLOW;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_12_R1.block.CraftChest;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.derkutils.Random;
import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.URunnable;
import xyz.derkades.ublisk.utils.Ublisk;

public class Loot extends UModule {
	
	@Override
	public void onEnable() {
		new URunnable() {
			public void run() {
				int random = Random.getRandomInteger(0, 5);
				if (random == 0){
					Loot.getRandomLoot().spawn();
				}
			}
		}.runTimer(5*60*20, 5*60*20);
	}
	
	private static final LootChest[] LOOT = new LootChest[]{
			new LootChest(Level.ONE, 80, 74, -5),
			new LootChest(Level.ONE, 214, 69, -11),
			new LootChest(Level.TWO, 90, 120, 335),
		};
		
	public static LootChest getRandomLoot() {
		int index = Random.getRandomInteger(0, LOOT.length - 1);
		return LOOT[index];
	}

	public static LootChest[] getLootChests() {
		return LOOT;
	}

	public static boolean isLoot(Chest chest) {
		Location loc = chest.getLocation();
		for (LootChest loot : LOOT) {
			Location lootLoc = loot.getLocation();
			if (lootLoc.getX() == loc.getBlockX() && lootLoc.getY() == loc.getBlockY() && lootLoc.getZ() == loc.getBlockZ()) {
				return true;
			}
		}
		return false;
	}
		
	public static class LootChest {

		private Level level;
		private Location location;

		LootChest(Level level, int x, int y, int z) {
			this.level = level;
			this.location = new Location(Var.WORLD, x, y, z);
		}

		public Level getLevel() {
			return level;
		}

		public Location getLocation() {
			return location;
		}

		public void spawn() {
			final Location loc = this.getLocation();
			Location shulkerBullet = new Location(Var.WORLD, loc.getX() + 0.5, loc.getY() + 100, loc.getZ() + 0.5);
			Var.WORLD.spawnEntity(shulkerBullet, EntityType.SHULKER_BULLET);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				public void run() {
					Block block = loc.getBlock();
					block.setType(Material.CHEST);
					CraftChest chest = (CraftChest) block.getState();
					try {
						Field inventoryField = chest.getClass().getDeclaredField("chest");
						inventoryField.setAccessible(true);
						chest.setCustomName("Loot");
					} catch (Exception e) {
						e.printStackTrace();
					}
					fillChestWithLoot();
					int x = loc.getBlockX();
					int y = loc.getBlockY();
					int z = loc.getBlockZ();
					Ublisk.broadcastPrefixedMessage("Loot", "A loot chest dropped at " + GOLD + "" + BOLD + x + " " + y
							+ " " + z + RESET + YELLOW + "!");
				}
			}, 70);
		}

		private void fillChestWithLoot() {
			Chest chest = (Chest) this.getLocation().getBlock().getState();
			LootItem[] loot = level.items;
			List<ItemStack> items = new ArrayList<ItemStack>();
			for (LootItem item : loot)
				items.add(new ItemStack(item.getMaterial(), item.getAmount()));
			List<ItemStack> contents = Arrays.asList(chest.getInventory().getContents());

			for (ItemStack item : items) {
				int slot = Random.getRandomInteger(0, 27);
				contents.set(slot, item);
			}

			chest.getInventory().setContents((ItemStack[]) contents.toArray());
		}
			
		public void remove() {
			Location loc = this.getLocation();
			Block block = new Location(Var.WORLD, loc.getX(), loc.getY(), loc.getZ()).getBlock();
			block.setType(Material.AIR);
			Logger.log(LogLevel.INFO, "Loot", "Removed loot chest at (" + loc.getX() + "," + loc.getY() + "," + loc.getZ() + ")!");
		}

	}
		
	private enum Level {

		ONE(new LootItem(Material.ROTTEN_FLESH, 1, 20)), 
		
		TWO(), 
		
		THREE();

		private LootItem[] items;

		Level(LootItem... items) {
			this.items = items;
		}

	}

	private static class LootItem {

		private Material material;
		private int min;
		private int max;

		LootItem(Material material, int min, int max) {
			this.material = material;
			this.min = min;
			this.max = max;
		}

		private Material getMaterial() {
			return material;
		}

		private int getAmount() {
			return Random.getRandomInteger(min, max);
		}

	}

}
