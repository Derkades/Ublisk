package com.robinmc.ublisk.quest.npc;

import com.robinmc.ublisk.quest.QuestCharacter;
import com.robinmc.ublisk.quest.QuestCharacterClass;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.utils.UPlayer;

public class David implements QuestCharacterClass {

	@Override
	public void talk(UPlayer player) {
		QuestParticipant qp = player.getQuestParticipant(null, QuestCharacter.DAVID);
		qp.sendMessage("Hi!");
	}

}
