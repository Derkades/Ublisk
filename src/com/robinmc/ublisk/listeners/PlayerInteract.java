package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.enums.Clazz;
import com.robinmc.ublisk.enums.Tracker;
import com.robinmc.ublisk.iconmenus.MainMenu;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Voting;
import com.robinmc.ublisk.utils.inventory.item.Item;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.variable.Message;
import com.robinmc.ublisk.utils.variable.Var;

import net.md_5.bungee.api.ChatColor;

public class PlayerInteract implements Listener {
	
	@EventHandler(ignoreCancelled = false)
	public void onInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR){
			PlayerInventory inv = player.getInventory();
			Material item = inv.getItemInMainHand().getType();
			Material offhand = inv.getItemInOffHand().getType();
			
			if (item == Material.BOW || offhand == Material.BOW){
				if (!(Clazz.getClass(player) == Clazz.ARCHER)){
					player.sendMessage(Message.CLASS_WRONG_WEAPON.get());
					event.setCancelled(true);
				}
			} else if (item == Material.STICK || offhand == Material.STICK){
				if (!(Clazz.getClass(player) == Clazz.SORCERER)){
					player.sendMessage(Message.CLASS_WRONG_WEAPON.get());
					event.setCancelled(true);
				}
			} else if (item == Material.CHEST){
				MainMenu.open(player);
				event.setCancelled(true);
			} else if (item == Material.END_CRYSTAL){
				player.openInventory(player.getEnderChest());
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOW, ignoreCancelled = false)
	public void tracker(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Action action = event.getAction();
		
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
			Tracker.RIGHT_CLICKED.add(player);
		}
		
		if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK){
			Tracker.LEFT_CLICKED.add(player);
		}
		
		if (action == Action.RIGHT_CLICK_BLOCK){
			Material type = event.getClickedBlock().getType();
			if (type == Material.CHEST){
				Tracker.LOOT_FOUND.add(player);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void spellTest(PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD){
				final Block block = event.getClickedBlock();
				if (block.getType() == Material.COBBLESTONE){
					@SuppressWarnings("deprecation")
					FallingBlock fall = block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
					Vector velocity = fall.getVelocity();
					velocity.setY(velocity.getY() + 1.0);
					fall.setVelocity(velocity);
					block.setType(Material.AIR);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
						public void run(){
							Location loc = block.getLocation();
							loc.setY(loc.getY() + 1);
							ThrownPotion potion = (ThrownPotion) Var.WORLD.spawnEntity(loc, EntityType.SPLASH_POTION);
							PotionEffect effect = new PotionEffect(PotionEffectType.HARM, 1, 2);
							potion.getEffects().add(effect);
						}
					}, 2*20);
				}
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void voteBox(PlayerInteractEvent event){
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}

		Block block = event.getClickedBlock();
		if (Voting.isVotingChest(block)){
			Chest chest = (Chest) block.getState();
			Inventory inv = chest.getInventory();
			
			int gold = Voting.getRandomGold();
			int xp = Voting.getRandomXP();
			int life = Voting.getRandomLife();
			
			String reset = "" + ChatColor.RESET; 
					
			Item goldItem = new Item(Material.GOLD_NUGGET);
			goldItem.setAmount(gold);
			goldItem.setDisplayName(reset + ChatColor.GOLD + "" + ChatColor.BOLD + "Gold: " + gold);
			
			Item xpItem = new Item(Material.EMERALD);
			xpItem.setAmount(xp);
			xpItem.setDisplayName(reset + ChatColor.GREEN + "" + ChatColor.BOLD + "XP: " + xp);
			
			Item lifeItem = new Item(Material.NETHER_STAR);
			lifeItem.setAmount(life);
			lifeItem.setDisplayName(reset + ChatColor.BOLD + "Life Crystals: " + life);
			
			inv.setItem(12, goldItem.getItemStack());
			inv.setItem(13, xpItem.getItemStack());
			inv.setItem(14, lifeItem.getItemStack());
			
			UPlayer player = UPlayer.get(event.getPlayer());
			
			if (gold !=0){
				player.getInventory().add(Material.GOLD_NUGGET, gold);
			}
			
			if (xp != 0){
				player.addXP(xp);
			}
			
			if (life != 0){
				player.addLifeCrystals(life);
			}
			
			Logger.log(LogLevel.DEBUG, "Gold: " + gold + " | XP: " + xp + " | Life: " + life);
		}
	}
	
	/*
	 * Taken from http://dev.bukkit.org/bukkit-plugins/anticroptrample/. 
	 * Changed old deprecated methods of getting blocks by id to the new system.
	 * All original code is not deleted but commented out.
	 */
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
	public void onTrample(PlayerInteractEvent event){
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
