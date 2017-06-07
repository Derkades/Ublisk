package xyz.derkades.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.quest.NPC;

public class RespawnNPC extends BukkitRunnable {

	public void run(){
		NPC.respawnAll();
	}

}
