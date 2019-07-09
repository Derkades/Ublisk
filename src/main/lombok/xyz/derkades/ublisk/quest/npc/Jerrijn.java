package xyz.derkades.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.entity.Villager.Profession;

import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.quest.NPC;
import xyz.derkades.ublisk.utils.UPlayer;

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
