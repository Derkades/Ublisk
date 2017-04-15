package com.robinmc.ublisk.quest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;

import net.md_5.bungee.api.ChatColor;

public abstract class NPC {
	
	
	public abstract String getName();
	
	public abstract Location getLocation();
	
	public abstract Profession getProfession();
	
	public abstract boolean canWalk();
	
	public boolean isEnabled(){
		return true;
	}

	public abstract void talk(UPlayer player);
	
	public void spawn(){
		if (!isEnabled()) throw new UnsupportedOperationException("Cannot spawn a disabled NPC.");
		
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

		for (NPC npc : NPC.getAllNPCs()){
			if (npc.isEnabled()) npc.spawn();
		}
	}
	
	public static List<NPC> getAllNPCs(){
		List<NPC> list = new ArrayList<NPC>();
		for (NPCEnum npc : NPCEnum.values())
			list.add(npc.getNPC());
		return list;
	}

}
