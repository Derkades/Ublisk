package com.robinmc.ublisk.abilities;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.robinmc.ublisk.abilities.AbilityTrigger.TriggerType;
import com.robinmc.ublisk.utils.UPlayer;

public class AbilityListener implements Listener {
	
	@EventHandler
	public void onLeftClick(PlayerInteractEvent event){
		if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK){
			return;
		}
		
		// TODO Listen for abilities
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}
	
		for (Ability ability : Ability.values()){
			AbilityTrigger trigger = ability.getTrigger();
			if (trigger.getTriggerType() == TriggerType.RIGHT_CLICK &&
					event.getPlayer().getInventory().getItemInMainHand().getType() == trigger.getItemType()){
				ability.doAbility(UPlayer.get(event));
			}
		}
	}

}
