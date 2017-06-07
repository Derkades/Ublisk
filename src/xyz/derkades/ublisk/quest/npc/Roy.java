package xyz.derkades.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.entity.Villager.Profession;

import xyz.derkades.ublisk.Town;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.quest.NPC;
import xyz.derkades.ublisk.quest.Quest;
import xyz.derkades.ublisk.quest.QuestParticipant;
import xyz.derkades.ublisk.utils.UPlayer;

public class Roy extends NPC {
	
	@Override
	public String getName() {
		return "Roy";
	}

	@Override
	public Location getLocation() {
		return new Location(Var.WORLD, 322, 82, 410);
	}

	@Override
	public Profession getProfession() {
		return Profession.NITWIT;
	}

	@Override
	public boolean canWalk() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void talk(UPlayer player) {
		QuestParticipant qp = new QuestParticipant(player.getPlayer(), Quest.UNKNOWN, this);
		qp.sendMessage("Hoi " + player.getName() + ", ik ben Roy");
		Town town = player.getLastTown();
		String townNaam = town.getName();
		qp.sendMessage("Je bent in: " + townNaam);
	}
}
