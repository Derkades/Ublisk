package com.robinmc.ublisk.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.Classes;
import com.robinmc.ublisk.Messages;

public class PlayerInteract implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
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
	/*
	 * Taken from http://dev.bukkit.org/bukkit-plugins/anticroptrample/. 
	 * Changed old deprecated methods of getting blocks by id to the new system.
	 * All original code is not deleted but commented out.
	 */
	
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.MONITOR)
	public void onTrample(PlayerInteractEvent event){
	    if (event.isCancelled()){
	      return;
	    }
	    
	    if (event.getAction() == Action.PHYSICAL){
	      Block block = event.getClickedBlock();
	      if (block == null) {
	        return;
	      }
	      //int blockType = block.getTypeId();
	      Material material = block.getType();
	      //if (blockType == Material.getMaterial(59).getId()){
	      if (material == Material.WHEAT){
	        //event.setUseInteractedBlock(Event.Result.DENY);
	    	event.setUseInteractedBlock(PlayerInteractEvent.Result.DENY);
	        event.setCancelled(true);

	        //block.setTypeId(blockType);
	        block.setType(Material.WHEAT);
	        block.setData(block.getData());
	      }
	    }
	    
	    if (event.getAction() == Action.PHYSICAL){
	      Block block = event.getClickedBlock();
	      if (block == null){
	        return;
	      }
	      
	      //int blockType = block.getTypeId();
	      Material material = block.getType();
	      
	      //if (blockType == Material.getMaterial(60).getId()){
	      if (material == Material.SOIL){
	        //event.setUseInteractedBlock(Event.Result.DENY);
	        event.setUseInteractedBlock(PlayerInteractEvent.Result.DENY);
	        event.setCancelled(true);
	        
	        //block.setType(Material.getMaterial(60));
	        block.setType(Material.SOIL);
	        block.setData(block.getData());
	      }
	    }
	  }

}
