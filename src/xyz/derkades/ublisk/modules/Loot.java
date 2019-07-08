package xyz.derkades.ublisk.modules;

import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.RESET;
import static org.bukkit.ChatColor.YELLOW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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
			@Override
			public void run() {
				final int random = Random.getRandomInteger(0, 5);
				if (random == 0){
					Loot.getRandomLoot().spawn();
				}
			}
		}.runTimer(5*60*20, 5*60*20);

		//Spawn particles
		new URunnable() {
			@Override
			public void run() {
				for (final Location location : SPAWNED_LOOT_LOCATIONS) {
					Ublisk.spawnParticle(Particle.TOTEM, location.add(0.5, 0.5, 0.5), 2, 0, 0, 0, 0.35);
				}
			}
		}.runTimer(3);
	}

	@Override
	public void onDisable() {
		Loot.removeLootChests();
	}

	@EventHandler
	public void onInvClose(final InventoryCloseEvent event) {
		if (event.getView().getTitle().contains("Loot")){
			new BukkitRunnable(){
				@Override
				public void run(){
					Var.WORLD.getBlockAt(event.getInventory().getLocation()).setType(Material.AIR);
				}
			}.runTaskLater(Main.getInstance(), 5*20);
		}
	}

	private static final List<Location> SPAWNED_LOOT_LOCATIONS = new ArrayList<>();

	private static final LootChest[] LOOT = new LootChest[]{
			new LootChest(Level.ONE, 80, 74, -5, BlockFace.SOUTH),
			new LootChest(Level.ONE, 214, 69, -11, BlockFace.NORTH),
			new LootChest(Level.TWO, 90, 120, 335, BlockFace.WEST),
		};

	public static LootChest getRandomLoot() {
		final int index = Random.getRandomInteger(0, LOOT.length - 1);
		return LOOT[index];
	}

	public static LootChest[] getLootChests() {
		return LOOT;
	}

	public static boolean isLoot(final Chest chest) {
		final Location loc = chest.getLocation();
		for (final LootChest loot : LOOT) {
			final Location lootLoc = loot.getLocation();
			if (lootLoc.getX() == loc.getBlockX() && lootLoc.getY() == loc.getBlockY() && lootLoc.getZ() == loc.getBlockZ()) {
				return true;
			}
		}
		return false;
	}

	public static void removeLootChests() {
		Logger.log(LogLevel.INFO, "Loot", "Removed all loot chets.");
		SPAWNED_LOOT_LOCATIONS.clear();
		for (final LootChest lootChest : LOOT) {
			lootChest.getLocation().getBlock().setType(Material.AIR);
		}
	}

	public static class LootChest {

		private final Level level;
		private final Location location;
		private final BlockFace direction;

		LootChest(final Level level, final int x, final int y, final int z, final BlockFace direction) {
			this.level = level;
			this.location = new Location(Var.WORLD, x, y, z);
			this.direction = direction;
		}

		public Level getLevel() {
			return this.level;
		}

		public Location getLocation() {
			return this.location;
		}

		public BlockFace getDirection() {
			return this.direction;
		}

		public void spawn() {
			final Location loc = this.getLocation();
			final Location shulkerBullet = new Location(Var.WORLD, loc.getX() + 0.5, loc.getY() + 100, loc.getZ() + 0.5);
			Var.WORLD.spawnEntity(shulkerBullet, EntityType.SHULKER_BULLET);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
				final Block block = loc.getBlock();
				block.setType(Material.CHEST);

				final Chest chest = (Chest) block.getState();

				Ublisk.NMS.setChestName(chest, "Loot - Level " + LootChest.this.getLevel().level);

				final org.bukkit.material.Chest data = (org.bukkit.material.Chest) block.getState().getData();
				data.setFacingDirection(LootChest.this.getDirection());
				block.getState().setData(data);

				chest.update();

				LootChest.this.fillChestWithLoot();
				final int x = loc.getBlockX();
				final int y = loc.getBlockY();
				final int z = loc.getBlockZ();

				SPAWNED_LOOT_LOCATIONS.add(block.getLocation());
				Ublisk.broadcastPrefixedMessage("Loot", "A loot chest dropped at " + GOLD + "" + BOLD + x + " " + y
						+ " " + z + RESET + YELLOW + "!");
			}, 70);
		}

		private void fillChestWithLoot() {
			final Chest chest = (Chest) this.getLocation().getBlock().getState();
			final LootItem[] loot = this.level.items;
			final List<ItemStack> items = new ArrayList<>();
			for (final LootItem item : loot)
				items.add(new ItemStack(item.getMaterial(), item.getAmount()));
			final List<ItemStack> contents = Arrays.asList(chest.getInventory().getContents());

			for (final ItemStack item : items) {
				final int slot = Random.getRandomInteger(0, 27);
				contents.set(slot, item);
			}

			chest.getInventory().setContents((ItemStack[]) contents.toArray());
		}

	}

	private enum Level {

		ONE(new LootItem(Material.ROTTEN_FLESH, 1, 20)),

		TWO(),

		THREE();

		private final LootItem[] items;
		private int level;

		Level(final LootItem... items) {
			this.items = items;

			switch (this.toString()){
				case "ONE":
					this.level = 1; break;
				case "TWO":
					this.level = 2; break;
				case "THREE":
					this.level = 3; break;
				default:
					this.level = -1;
			}
		}

	}

	private static class LootItem {

		private final Material material;
		private final int min;
		private final int max;

		LootItem(final Material material, final int min, final int max) {
			this.material = material;
			this.min = min;
			this.max = max;
		}

		private Material getMaterial() {
			return this.material;
		}

		private int getAmount() {
			return Random.getRandomInteger(this.min, this.max);
		}

	}

}
