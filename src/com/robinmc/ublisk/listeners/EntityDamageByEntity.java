package com.robinmc.ublisk.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.Classes;
import com.robinmc.ublisk.Messages;

public class EntityDamageByEntity implements Listener {
	
	@EventHandler
	public void useWeapon(EntityDamageByEntityEvent event){
		if (event.getDamager().getType() == EntityType.PLAYER){
			Player player = (Player) event.getDamager();
			PlayerInventory inv = player.getInventory();
			Material item = inv.getItemInMainHand().getType();
			
			if (	item == Material.WOOD_SWORD ||
					item == Material.STONE_SWORD ||
					item == Material.DIAMOND_SWORD ||
					item == Material.GOLD_SWORD ||
					item == Material.IRON_SWORD){
				if (!(Classes.getClass(player) == Classes.SWORDSMAN)){
					player.sendMessage(Messages.wrongWeapon());
					event.setCancelled(true);
				}
			}
		}
	}

}
