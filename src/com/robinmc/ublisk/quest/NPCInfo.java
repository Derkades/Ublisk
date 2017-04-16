package com.robinmc.ublisk.quest;

import org.bukkit.Location;
import org.bukkit.entity.Villager.Profession;

import com.robinmc.ublisk.Var;

@Deprecated
public class NPCInfo {
	
	private String name;
	private Profession profession;
	private boolean canWalk;
	private Location loc;
	
	/**
	 * Provides info about an NPC.
	 * @param name The NPC's name.
	 * @param profession Villager profession (type Profession. and let autocomplete do the rest)
	 * @param canWalk If the villager should be able to walk
	 * @param loc Spawn location. new NPCLocation(x, y, z). Use null if location is unknown
	 */
	public NPCInfo(String name, Profession profession, boolean canWalk, NPCLocation loc){
		this.name = name;
		this.profession = profession;
		this.canWalk = canWalk;
		if (loc == null){
			this.loc = new Location(Var.WORLD, 2.5, 71, -16.5, 0, 0);
		} else {
			this.loc = loc.getLocation();
		}
	}
	
	String getName(){
		return name;
	}
	
	Profession getProfession(){
		return profession;
	}
	
	boolean canWalk(){
		return canWalk;
	}
	
	Location getLocation(){
		return loc;
	}
	
	public static class NPCLocation {
		
		private double x;
		private double y;
		private double z;
		
		public NPCLocation(double x, double y, double z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		private Location getLocation(){
			return new Location(Var.WORLD, x, y, z);
		}
		
	}
}
