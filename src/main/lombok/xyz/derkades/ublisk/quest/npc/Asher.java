package xyz.derkades.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.entity.Villager.Profession;

import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.quest.NPC;
import xyz.derkades.ublisk.utils.UPlayer;

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
