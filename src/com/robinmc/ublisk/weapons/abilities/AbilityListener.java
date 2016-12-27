package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AbilityListener implements Listener {

	//TODO Fix ability listener

	@EventHandler
	public void onLeftClick(PlayerInteractEvent event) {
		if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) {
			return;
		}

	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

	}

}
