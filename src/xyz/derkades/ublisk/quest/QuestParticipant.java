package xyz.derkades.ublisk.quest;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.RESET;
import static org.bukkit.ChatColor.YELLOW;

import org.bukkit.entity.Player;

import xyz.derkades.ublisk.DataFile;
import xyz.derkades.ublisk.quest.npcmenu.NPCMenu;
import xyz.derkades.ublisk.utils.UPlayer;

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
		DataFile.QUESTS.getConfig().set(this.getUniqueId() + ".progress." + data.toString(), true);
	}
	
	public boolean getProgress(QuestProgress data){
		if (DataFile.QUESTS.getConfig().isSet(this.getUniqueId() + ".progress." + data.toString())){
			return DataFile.QUESTS.getConfig().getBoolean(this.getUniqueId() + ".progress." + data.toString());
		} else {
			return false;
		}
	}
	
	public void setQuestCompleted(boolean bool){
		DataFile.QUESTS.getConfig().set(this.getUniqueId() + ".quests." + quest.getConfigString(), bool);
	}
	
	public boolean getQuestCompleted(){
		return DataFile.QUESTS.getConfig().getBoolean(this.getUniqueId() + ".quests." + quest.getConfigString());
	}
	
	public boolean hasRequiredLevel(){
		int level = this.getLevel();
		int levelRequired = quest.getLevel();
		return level >= levelRequired;
	}
	
	@Override
	public void sendMessage(String message){
		super.sendMessage(DARK_AQUA + "" + BOLD + npc.getName() + ": " + RESET + "" + AQUA + message);
	}
	
	public void giveRewardExp(){
		this.addXP(quest.getRewardExp());
	}
	
	public void sendCompletedMessage(){
		if (quest.noLifeReward()){
			super.sendMessage("You have completed quest " + BOLD + this.getQuest().getName() + YELLOW + " and got " + this.getQuest().getRewardExp() + " XP!");
		} else {
			this.sendMessage("You have completed quest " + BOLD + this.getQuest().getName() + YELLOW + " and got " + this.getQuest().getRewardExp() + " XP and " + this.getQuest().getRewardExp() + " Life Crystals!");
		}
	}
	
	public void openMenu(NPCMenu menu){
		menu.open(this);
	}

}
