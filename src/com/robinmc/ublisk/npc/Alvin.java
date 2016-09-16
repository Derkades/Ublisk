package com.robinmc.ublisk.npc;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.quest.Quest;
import com.robinmc.ublisk.utils.quest.QuestCharacter;
import com.robinmc.ublisk.utils.quest.QuestCharacterClass;
import com.robinmc.ublisk.utils.quest.QuestParticipant;
import com.robinmc.ublisk.utils.quest.QuestProgress;

public class Alvin implements QuestCharacterClass{

	@Override
	public void talk(Player player) {
		// TODO Auto-generated method stub
		QuestParticipant qp = new QuestParticipant(player, Quest.WATER_PROBLEM, QuestCharacter.ALVIN);
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
