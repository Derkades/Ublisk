package com.robinmc.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.entity.Villager.Profession;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.utils.UPlayer;

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
