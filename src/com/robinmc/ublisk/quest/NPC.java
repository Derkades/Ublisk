package com.robinmc.ublisk.quest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

import net.md_5.bungee.api.ChatColor;

public abstract class NPC {
	
	public abstract NPCInfo getNPCInfo();
	
	public String getName(){
		return this.getNPCInfo().getName();
	}
	
	public Location getLocation(){
		return this.getNPCInfo().getLocation();
	}
	
	public Profession getProfession(){
		return this.getNPCInfo().getProfession();
	}
	
	public boolean canWalk(){
		return this.getNPCInfo().canWalk();
	}

	public abstract void talk(UPlayer player);
	
	public void spawn(){
		Villager villager = Var.WORLD.spawn(this.getLocation(), Villager.class);
		villager.setCustomName(ChatColor.DARK_GREEN + this.getName());
		villager.setSilent(true);
		villager.setInvulnerable(true);
		villager.setCustomNameVisible(true);
		villager.setBreed(false);
		if (!canWalk()) 
			villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000*20, 255, true));
		if (getProfession() != null)
			villager.setProfession(getProfession());
		Logger.log(LogLevel.DEBUG, "NPC", "Spawned " + getName() + " at (" + this.getLocation().getX() + ", " + this.getLocation().getBlockY() + ", " + this.getLocation().getBlockZ() + ")");
	}
	
	public static NPC fromName(String text){
		if (text == null) {
			throw new IllegalArgumentException();
		}
		
		for (NPC npc : NPC.getAllNPCs()) {
			if (text.contains(npc.getName())) {
				return npc;
			}
		}
		
		return null;
		
	}
	
	public static void respawnAll(){
		for (Villager villager : Var.WORLD.getEntitiesByClass(Villager.class))
			villager.remove();

		for (NPC npc : NPC.getAllNPCs())
			npc.spawn();
	}
	
	public static List<NPC> getAllNPCs(){
		List<NPC> list = new ArrayList<NPC>();
		for (NPCEnum npc : NPCEnum.values())
			list.add(npc.getNPC());
		return list;
	}

}
