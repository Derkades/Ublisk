package com.robinmc.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.entity.Villager.Profession;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.utils.UPlayer;

public class Asher extends NPC {
	
	@Override
	public String getName() {
		return "Asher";
	}

	@Override
	public Location getLocation() {
		return new Location(Var.WORLD, 449.3, 70, -10.5);
	}

	@Override
	public Profession getProfession() {
		return null;
	}

	@Override
	public boolean canWalk() {
		return false;
	}
	
	@Override
	public void talk(UPlayer player) {
		
	}

}
