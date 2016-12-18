package com.robinmc.ublisk.quest;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.quest.npcmenu.NPCMenu;
import com.robinmc.ublisk.utils.DataFile;
import com.robinmc.ublisk.utils.UPlayer;

public class QuestParticipant extends UPlayer {
	
	private Quest quest;
	private NPC npc;
	
	public QuestParticipant(Player player, Quest quest, NPC npc){
		super(player);
		this.quest = quest;
		this.npc = npc;
	}
	
	public Quest getQuest(){
		return quest;
	}
	
	public NPC getNPC(){
		return npc;
	}
	
	public void saveProgress(QuestProgress data){
		DataFile.QUESTS.set(this.getUniqueId() + ".progress." + data.toString(), true);
	}
	
	public boolean getProgress(QuestProgress data){
		if (DataFile.QUESTS.isSet(this.getUniqueId() + ".progress." + data.toString())){
			return DataFile.QUESTS.getBoolean(this.getUniqueId() + ".progress." + data.toString());
		} else {
			return false;
		}
	}
	
	public void setQuestCompleted(boolean bool){
		DataFile.QUESTS.set(this.getUniqueId() + ".quests." + quest.getConfigString(), bool);
	}
	
	public boolean getQuestCompleted(){
		return DataFile.QUESTS.getBoolean(this.getUniqueId() + ".quests." + quest.getConfigString());
	}
	
	public boolean hasRequiredLevel(){
		int level = this.getLevel();
		int levelRequired = quest.getLevel();
		return level >= levelRequired;
	}
	
	@Override
	public void sendMessage(String message){
		super.sendMessage(Message.Complicated.Quests.npcMsg(npc.getName(), message));
	}
	
	public void giveRewardExp(){
		this.addXP(quest.getRewardExp());
	}
	
	public void sendCompletedMessage(){
		if (quest.noLifeReward()){
			this.sendMessage(Message.Complicated.Quests.questCompleted(quest.getName(), quest.getRewardExp()));
		} else {
			this.sendMessage(Message.Complicated.Quests.questCompleted(quest.getName(), quest.getRewardExp(), quest.getLifeCrystalReward()));
		}
	}
	
	public void openMenu(NPCMenu menu){
		menu.open(this);
	}

}
