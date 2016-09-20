package com.robinmc.ublisk.quest;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.enums.Town;
import com.robinmc.ublisk.utils.DataFile;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.utils.variable.Message;

public class QuestParticipant {
	
	private Player player;
	private Quest quest;
	private QuestCharacter npc;
	
	public QuestParticipant(Player player, Quest quest, QuestCharacter npc){
		this.player = player;
		this.quest = quest;
		this.npc = npc;
	}
	
	public Player getBukkitPlayer(){
		return player;
	}
	
	public UPlayer getPlayer(){
		return UPlayer.get(player);
	}
	
	public Quest getQuest(){
		return quest;
	}
	
	public QuestCharacter getQuestCharacter(){
		return npc;
	}
	
	public BetterInventory getInventory(){
		return new BetterInventory(player);
	}
	
	public void saveProgress(QuestProgress data){
		DataFile.QUESTS.set(player.getUniqueId() + "." + data.toString(), true);
	}
	
	public boolean getProgress(QuestProgress data){
		try {
			return DataFile.QUESTS.getBoolean(player.getUniqueId() + "." + data.toString());
		} catch (Exception e){
			return false;
		}
	}
	
	public void setQuestCompleted(boolean bool){
		DataFile.QUESTS.set(player.getUniqueId() + "." + quest.getConfigString(), bool);
	}
	
	public boolean getQuestCompleted(){
		return DataFile.QUESTS.getBoolean(player.getUniqueId() + "." + quest.getConfigString());
	}
	
	public boolean hasRequiredLevel(){
		int level = Exp.getLevel(player);
		int levelRequired = quest.getLevel();
		return level >= levelRequired;
	}
	
	public void sendMessage(String msg){
		player.sendMessage(Message.Complicated.Quests.npcMsg(npc.getName(), msg));
	}
	
	public void giveRewardExp(){
		Exp.add(player, quest.getRewardExp());
	}
	
	public void sendCompletedMessage(){
		if (quest.noLifeReward()){
			player.sendMessage(Message.Complicated.Quests.questCompleted(quest.getName(), quest.getRewardExp()));
		} else {
			player.sendMessage(Message.Complicated.Quests.questCompleted(quest.getName(), quest.getRewardExp(), quest.getLifeCrystalReward()));
		}
	}
	
	public Town getLastTown(){
		return Town.getLastTown(player);
	}
	
	public PlayerInventory getBukkitInventory(){
		return player.getInventory();
	}

}
