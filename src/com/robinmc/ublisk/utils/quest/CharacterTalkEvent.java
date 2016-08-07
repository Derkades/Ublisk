package com.robinmc.ublisk.utils.quest;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CharacterTalkEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	private boolean cancel;
	private Player player;
	private QuestCharacter npc;
	
	public CharacterTalkEvent(Player player, QuestCharacter npc){
		this.player = player;
		this.npc = npc;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public QuestCharacter getNPC(){
		return npc;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}

}
