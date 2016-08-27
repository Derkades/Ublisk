package com.robinmc.ublisk.utils;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.quest.QuestParticipant;

public class LifeCrystalPlayer {
	
	private Player player;
	private UUID uuid;
	
	public LifeCrystalPlayer(Player player){
		this.player = player;
	}
	
	public LifeCrystalPlayer(QuestParticipant qp){
		this.player = qp.getPlayer();
		this.uuid = player.getUniqueId();
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void setLifeCrystals(int amount){
		Config.set("life." + uuid, amount);
	}
	
	public void addLifeCrystals(int amount){
		int old = getLifeCrystals();
		setLifeCrystals(old + amount);
	}
	
	public void removeLifeCrystals(int amount){
		int old = getLifeCrystals();
		setLifeCrystals(old - amount);
	}
	
	public int getLifeCrystals(){
		if (Config.getConfig().isSet("life." + uuid)){
			return Config.getInteger("life." + uuid);
		} else {
			return 0;
		}
	}

}
