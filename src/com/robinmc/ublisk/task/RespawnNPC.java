package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.quest.NPC;

public class RespawnNPC extends BukkitRunnable {

	public void run(){
		NPC.respawnAll();
	}

}
