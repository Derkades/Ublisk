package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.weapons.Weapon;

public class AbilityListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		
		if (action == Action.PHYSICAL)
			return;
		
		UPlayer player = new UPlayer(event);
		
		if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
			for (Weapon weapon : Weapon.WEAPONS){
				if (Weapon.itemStackIsWeapon(player.getInventory().getItemInMainHand().getItemStack(), weapon)){
					player.doAbility(weapon.getLeftClickAbility());
				}
			}
		}
		
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			for (Weapon weapon : Weapon.WEAPONS){
				if (Weapon.itemStackIsWeapon(player.getInventory().getItemInMainHand().getItemStack(), weapon)){
					player.doAbility(weapon.getRightClickAbility());
				}
			}
		}
		
	}

}
