package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.quest.QuestCharacter;

public class RespawnNPC extends BukkitRunnable {

	public void run(){
		QuestCharacter.respawnAll();
	}

}
