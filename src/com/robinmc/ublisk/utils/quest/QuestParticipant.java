package com.robinmc.ublisk.utils.quest;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.LifeCrystalPlayer;
import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.utils.variable.CMessage;

public class QuestParticipant {
	
	private Player player;
	private Quest quest;
	private QuestCharacter npc;
	
	public QuestParticipant(Player player, Quest quest, QuestCharacter npc){
		this.player = player;
		this.quest = quest;
		this.npc = npc;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Quest getQuest(){
		return quest;
	}
	
	public QuestCharacter getNPC(){
		return npc;
	}
	
	public QuestCharacter getNpc(){
		return getNPC();
	}
	
	public QuestCharacter getQuestCharacter(){
		return getNPC();
	}
	
	public BetterInventory getInventory(){
		return new BetterInventory(player);
	}
	
	public void saveProgress(QuestProgress data){
		Config.set("quests." + player.getUniqueId() + "." + data.toString(), true);
	}
	
	public boolean getProgress(QuestProgress data){
		try {
			return Config.getBoolean("quests." + player.getUniqueId() + "." + data.toString());
		} catch (Exception e){
			return false;
		}
	}
	
	public void setQuestCompleted(boolean bool){
		Config.set("quest." + player.getUniqueId() + "." + quest.getConfigString(), bool);
	}
	
	public boolean getQuestCompleted(){
		return Config.getBoolean("quests." + player.getUniqueId() + "." + quest.getConfigString());
	}
	
	public boolean hasRequiredLevel(){
		int level = Exp.getLevel(player);
		int levelRequired = quest.getLevel();
		return level >= levelRequired;
	}
	
	public void msg(String msg){
		player.sendMessage(CMessage.npcMsg(npc.getName(), msg));
	}
	
	public void giveRewardExp(){
		Exp.add(player, quest.getRewardExp());
	}
	
	public void sendCompletedMessage(){
		player.sendMessage(CMessage.questCompleted(quest.getName(), quest.getRewardExp()));
	}
	
	public LifeCrystalPlayer getLifeCrystalPlayer(){
		return new LifeCrystalPlayer(player);
	}

}
