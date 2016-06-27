package com.robinmc.ublisk.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.Classes;
import com.robinmc.ublisk.Messages;

public class PlayerInteract implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR){
			PlayerInventory inv = player.getInventory();
			Material item = inv.getItemInMainHand().getType();
			Material offhand = inv.getItemInOffHand().getType();
			
			if (item == Material.BOW || offhand == Material.BOW){
				if (!(Classes.getClass(player) == Classes.ARCHER)){
					player.sendMessage(Messages.wrongWeapon());
					event.setCancelled(true);
				}
			} else if (item == Material.STICK || offhand == Material.STICK){
				if (!(Classes.getClass(player) == Classes.SORCERER)){
					player.sendMessage(Messages.wrongWeapon());
					event.setCancelled(true);
				}
			}
			
			if (item == Material.END_CRYSTAL){
				player.openInventory(player.getEnderChest());
			}
			
		}
	}

}
