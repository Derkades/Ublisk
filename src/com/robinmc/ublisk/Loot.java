package com.robinmc.ublisk;

import java.security.SecureRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;

import com.robinmc.ublisk.utils.Console;

public enum Loot {
	
	A(1, 80, 74, -5),
	B(1, 250, 67, -10),
	C(1, 214, 69, -11);
	
	private int tier;
	private int x;
	private int y;
	private int z;
	
	Loot(int tier, int x, int y, int z){
		this.tier = tier;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getTier(){
		return tier;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
	
	private static <T extends Enum<?>> T randomEnum(Class<T> clazz){
		final SecureRandom random = new SecureRandom();
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
	
	public static void spawnRandomLoot(){
		Loot loot = randomEnum(Loot.class);
		final int x = loot.getX();
		final int y = loot.getY();
		final int z = loot.getZ();
		final Location loc = new Location(Var.world(), x, y, z);
		Location shulkerBullet = new Location(Var.world(), x + 0.5, y + 100, z + 0.5);
		World world = Var.world();
		world.spawnEntity(shulkerBullet, EntityType.SHULKER_BULLET);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			public void run(){
				Block block = loc.getBlock();
				block.setType(Material.CHEST);
				Bukkit.broadcastMessage(Messages.lootSpawned(x, y, z));
			}
		}, 70);
	}
	
	public static void removeLoot(){
		Console.sendMessage("[Loot] Removed all loot chests!");
		for (Loot loot : Loot.values()){
			Block block = new Location(Var.world(), loot.getX(), loot.getY(), loot.getZ()).getBlock();
			block.setType(Material.AIR);
		}
	}

}
