package com.robinmc.ublisk;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_11_R1.block.CraftChest;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.java.EnumUtils;
import com.robinmc.ublisk.utils.java.Random;

import net.minecraft.server.v1_11_R1.TileEntityChest;

public enum Loot {
	
	A(Level.ONE, 80, 74, -5),
	B(Level.ONE, 250, 67, -10),
	C(Level.ONE, 214, 69, -11);
	
	private Level level;
	private Location loc;
	
	Loot(Level level, int x, int y, int z){
		this.level = level;
		this.loc = new Location(Var.WORLD, x, y, z);
	}
	
	public static Loot getRandomLoot(){
		return EnumUtils.getRandomEnum(Loot.class);	
    }
	
	public void spawn(){
		Location shulkerBullet = new Location(Var.WORLD, loc.getX() + 0.5, loc.getY() + 100, loc.getZ() + 0.5);
		Var.WORLD.spawnEntity(shulkerBullet, EntityType.SHULKER_BULLET);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			public void run(){
				Block block = loc.getBlock();
				block.setType(Material.CHEST);
				CraftChest chest = (CraftChest) block.getState();
				try {
				    Field inventoryField = chest.getClass().getDeclaredField("chest");
				    inventoryField.setAccessible(true);
				    TileEntityChest teChest = ((TileEntityChest) inventoryField.get(chest));
				    teChest.a("Loot");
				} catch (Exception e){
				     e.printStackTrace();
				}
				fillChestWithLoot();
				Bukkit.broadcastMessage(Message.Complicated.lootSpawned(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
			}
		}, 70);
	}
	
	private void fillChestWithLoot(){
		Chest chest = (Chest) loc.getBlock().getState();
		LootItem[] loot = level.items;
		List<ItemStack> items = new ArrayList<ItemStack>();
		for (LootItem item : loot) items.add(new ItemStack(item.getMaterial(), item.getAmount()));
		List<ItemStack> contents = Arrays.asList(chest.getInventory().getContents());
		
		for (ItemStack item : items){
			int slot = Random.getRandomInteger(0, 27);
			contents.set(slot, item);
		}
		
		chest.getInventory().setContents((ItemStack[]) contents.toArray());
	}
	
	public static void removeLoot(){
		Logger.log(LogLevel.INFO, "Loot", "Removed all loot chests!");
		for (Loot loot : Loot.values()){
			Block block = new Location(Var.WORLD, loot.loc.getX(), loot.loc.getY(), loot.loc.getZ()).getBlock();
			block.setType(Material.AIR);
		}
	}
	
	public static boolean isLoot(Chest chest){
		Location loc = chest.getLocation();
		for (Loot loot : Loot.values()){
			if (	loot.loc.getX() == loc.getBlockX() &&
					loot.loc.getY() == loc.getBlockY() &&
					loot.loc.getZ() == loc.getBlockZ()){
				return true;
			}
		}
		return false;
	}
	
	private enum Level {
		
		ONE(
				new LootItem(Material.ROTTEN_FLESH, 1, 20)
				),
		TWO(
				
				),
		THREE(
				
				);
		
		private LootItem[] items;
		
		Level(LootItem... items){
			this.items = items;
		}
		
	}

	private static class LootItem {
		
		private Material material;
		private int min;
		private int max;
		
		LootItem(Material material, int min, int max){
			this.material = material;
			this.min = min;
			this.max = max;
		}
		
		private Material getMaterial(){
			return material;
		}
		
		private int getAmount(){
			return Random.getRandomInteger(min, max);
		}
		
	}

}
