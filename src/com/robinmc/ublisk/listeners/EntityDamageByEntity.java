package com.robinmc.ublisk.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.utils.enums.Classes;
import com.robinmc.ublisk.utils.quest.QuestCharacter;
import com.robinmc.ublisk.utils.variable.Message;

public class EntityDamageByEntity implements Listener {
	
	@EventHandler
	public void useWeapon(EntityDamageByEntityEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		if (QuestCharacter.getAllNames().contains(event.getEntity().getName())){
			event.setCancelled(true);
			return;
		}
		
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
					player.sendMessage(Message.CLASS_WRONG_WEAPON.get());
					event.setCancelled(true);
				}
			}
		}
	}

}
