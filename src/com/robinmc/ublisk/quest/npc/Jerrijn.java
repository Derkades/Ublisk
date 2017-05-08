package com.robinmc.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.entity.Villager.Profession;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.utils.UPlayer;

public class Jerrijn extends NPC {
	
	@Override
	public String getName() {
		return "Jerrijn";
	}

	@Override
	public Location getLocation() {
		return new Location(Var.WORLD, 678, 74, 387);
	}

	@Override
	public Profession getProfession() {
		return Profession.NITWIT;
	}

	@Override
	public boolean canWalk() {
		return false;
	}
	
	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void talk(UPlayer player) {
		player.sendMessage("Town: "+ player.getLastTown().getName());
	}

}
