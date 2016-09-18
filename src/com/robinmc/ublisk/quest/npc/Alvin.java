package com.robinmc.ublisk.quest.npc;

import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestCharacter;
import com.robinmc.ublisk.quest.QuestCharacterClass;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.quest.QuestProgress;
import com.robinmc.ublisk.utils.UPlayer;

public class Alvin implements QuestCharacterClass{

	@Override
	public void talk(UPlayer player) {
		// TODO Auto-generated method stub
		//QuestParticipant qp = new QuestParticipant(player, Quest.WATER_PROBLEM, QuestCharacter.ALVIN);
		QuestParticipant qp = player.getQuestParticipant(Quest.WATER_PROBLEM, QuestCharacter.ALVIN);
		if (qp.getProgress(QuestProgress.CHECKED_DAM)){
			//TODO add message
			return;
		}
		if (!qp.getProgress(QuestProgress.DAM_FIRST_TALK)){
			//TODO add message
			return;
		}
		qp.msg(""); //TODO add message
		qp.saveProgress(QuestProgress.CHECKED_DAM);
		
		
	}

}
