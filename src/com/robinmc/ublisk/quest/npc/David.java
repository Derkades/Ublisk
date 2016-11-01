package com.robinmc.ublisk.quest.npc;

import org.bukkit.entity.Villager;

import com.robinmc.ublisk.quest.QuestCharacter;
import com.robinmc.ublisk.quest.QuestCharacterClass;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.utils.UPlayer;

public class David implements QuestCharacterClass {

	@Override
	public void talk(UPlayer player, QuestCharacter npc) {
		final QuestParticipant qp = player.getQuestParticipant(null, npc);
		qp.sendMessage("Hi!");
	}

	@Override
	public void spawn(Villager villager, QuestCharacter npc) {

	}

}
