package xyz.derkades.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.entity.Villager.Profession;

import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.quest.NPC;
import xyz.derkades.ublisk.quest.QuestParticipant;
import xyz.derkades.ublisk.utils.UPlayer;

public class David extends NPC {
	
	@Override
	public String getName() {
		return "David";
	}

	@Override
	public Location getLocation() {
		return new Location(Var.WORLD, 72.5, 67, -2.5);
	}

	@Override
	public Profession getProfession() {
		return Profession.FARMER;
	}

	@Override
	public boolean canWalk() {
		return false;
	}
	
	@Override
	public void talk(UPlayer player) {
		final QuestParticipant qp = player.getQuestParticipant(null, this);
		qp.sendMessage("Hi!");
	}

}
