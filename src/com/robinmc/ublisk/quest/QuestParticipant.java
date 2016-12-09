package com.robinmc.ublisk.quest;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.Town;
import com.robinmc.ublisk.quest.npcmenu.NPCMenu;
import com.robinmc.ublisk.utils.DataFile;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.BetterInventory;

public class QuestParticipant {
	
	private UPlayer player;
	private Quest quest;
	private NPC npc;
	
	public QuestParticipant(Player player, Quest quest, NPC npc){
		this.player = UPlayer.get(player);
		this.quest = quest;
		this.npc = npc;
	}
	
	public Player getBukkitPlayer(){
		return player.getPlayer();
	}
	
	public UPlayer getPlayer(){
		return player;
	}
	
	public Quest getQuest(){
		return quest;
	}
	
	public NPC getNPC(){
		return npc;
	}
	
	public BetterInventory getInventory(){
		return player.getInventory();
	}
	
	public void saveProgress(QuestProgress data){
		DataFile.QUESTS.set(player.getUniqueId() + ".progress." + data.toString(), true);
	}
	
	public boolean getProgress(QuestProgress data){
		if (DataFile.QUESTS.isSet(player.getUniqueId() + ".progress." + data.toString())){
			return DataFile.QUESTS.getBoolean(player.getUniqueId() + ".progress." + data.toString());
		} else {
			return false;
		}
	}
	
	public void setQuestCompleted(boolean bool){
		DataFile.QUESTS.set(player.getUniqueId() + ".quests." + quest.getConfigString(), bool);
	}
	
	public boolean getQuestCompleted(){
		return DataFile.QUESTS.getBoolean(player.getUniqueId() + ".quests." + quest.getConfigString());
	}
	
	public boolean hasRequiredLevel(){
		int level = player.getLevel();
		int levelRequired = quest.getLevel();
		return level >= levelRequired;
	}
	
	public void sendMessage(String msg){
		player.sendMessage(Message.Complicated.Quests.npcMsg(npc.getName(), msg));
	}
	
	public void sendMessage(Message message){
		player.sendMessage(message.get());
	}
	
	public void giveRewardExp(){
		player.addXP(quest.getRewardExp());
	}
	
	public void sendCompletedMessage(){
		if (quest.noLifeReward()){
			player.sendMessage(Message.Complicated.Quests.questCompleted(quest.getName(), quest.getRewardExp()));
		} else {
			player.sendMessage(Message.Complicated.Quests.questCompleted(quest.getName(), quest.getRewardExp(), quest.getLifeCrystalReward()));
		}
	}
	
	public Town getLastTown(){
		return player.getLastTown();
	}
	
	public PlayerInventory getBukkitInventory(){
		return player.getInventory().getBukkitInventory();
	}
	
	public void openMenu(NPCMenu menu){
		menu.open(player);
	}

}
