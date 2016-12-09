package com.robinmc.ublisk.quest;

import org.bukkit.entity.Villager;

import com.robinmc.ublisk.utils.UPlayer;

@Deprecated
public interface QuestCharacterClass {

	public void talk(UPlayer player, QuestCharacter npc);
	
	public void spawn(Villager villager, QuestCharacter npc);

}
