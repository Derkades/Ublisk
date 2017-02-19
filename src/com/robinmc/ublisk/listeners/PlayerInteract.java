package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.robinmc.ublisk.Clazz;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.Voting;
import com.robinmc.ublisk.database.Tracker;
import com.robinmc.ublisk.iconmenus.MainMenu;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.ItemBuilder;

public class PlayerInteract implements Listener {
	
	@EventHandler(ignoreCancelled = false)
	public void onInteract(PlayerInteractEvent event){
		UPlayer player = new UPlayer(event);
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR){
			PlayerInventory inv = player.getInventory();
			Material item = inv.getItemInMainHand().getType();
			Material offhand = inv.getItemInOffHand().getType();
			
			if (item == Material.BOW || offhand == Material.BOW){
				if (!(player.getClazz() == Clazz.ARCHER)){
					player.sendMessage(Message.CLASS_WRONG_WEAPON);
					event.setCancelled(true);
				}
			} else if (item == Material.STICK || offhand == Material.STICK){
				if (!(player.getClazz() == Clazz.SORCERER)){
					player.sendMessage(Message.CLASS_WRONG_WEAPON);
					event.setCancelled(true);
				}
			} else if (item == Material.CHEST && !player.isInBuilderMode()){
				MainMenu.open(player);
				event.setCancelled(true);
			} else if (item == Material.END_CRYSTAL){
				player.openEnderchest();
				event.setCancelled(true);
			}
		}
	}
	
	//ignoreCancelled = true - Still track clicks if they are cancelled
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
	public void tracker(PlayerInteractEvent event){
		UPlayer player = new UPlayer(event);
		Action action = event.getAction();
		
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
			player.tracker(Tracker.RIGHT_CLICKED);
		}
		
		if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK){
			player.tracker(Tracker.LEFT_CLICKED);
		}
		
		if (action == Action.RIGHT_CLICK_BLOCK){
			Material type = event.getClickedBlock().getType();
			if (type == Material.CHEST && !Voting.isVotingChest(event.getClickedBlock())){
				player.tracker(Tracker.LOOT_FOUND);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
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
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.LOW)
	public void staffTool(PlayerInteractEvent event){
		ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();

		
		if (itemInHand.getType() != Material.COAL_ORE || event.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}
		
		Player player = event.getPlayer();
		String itemName = itemInHand.getItemMeta().getDisplayName();
		Block block = event.getClickedBlock();
		
		event.setCancelled(true);
		
		if (itemName == null){
			sendStaffToolInfoMessage(player);
			return;
		}
		
		if (itemName.contains("farmland")){
			block.setType(Material.SOIL);
			block.setData((byte) 7);
			Location loc = block.getLocation();
			Block wheat = new Location(Var.WORLD, loc.getX(), loc.getY() + 1, loc.getZ()).getBlock();
			wheat.setType(Material.CROPS);
			wheat.setData((byte) 7);
		} else if (itemName.contains("invis")){
			block.setData((byte) 0);
			block.setType(Material.PISTON_MOVING_PIECE);
			event.getPlayer().sendMessage("Placed invisible block. To remove invisible block, type /u rinv while standing inside an invisible block.");
		} else {
			sendStaffToolInfoMessage(player);
		}
	}
	
	private static void sendStaffToolInfoMessage(Player player){
		String[] strings = new String[]{
				"",
				"Mogelijke namen:",
				"invis - Plaatst onzichtbaar block",
				"farmland - Plaatst farmland met wheat"
		};
		
		for (String string : strings) player.sendMessage(string);
		player.getInventory().addItem(new ItemBuilder(Material.ANVIL).setName("Use this to rename the coal block.").getItemStack());
	}
	
	/*
	 * Inspiration from http://dev.bukkit.org/bukkit-plugins/anticroptrample/. 
	 */
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled = false)
	public void onTrample(PlayerInteractEvent event){
	    if (event.getAction() == Action.PHYSICAL){
	    	Block block = event.getClickedBlock();
			if (block == null){
				return;
			}  
		
			Material material = block.getType(); 
			
			if (material == Material.SOIL){
				event.setUseInteractedBlock(PlayerInteractEvent.Result.DENY);
				event.setCancelled(true);       
				
				//Set soil as not hydrated
				block.setType(Material.SOIL);
				block.setData((byte) 0);
				final Block block2 = block;
				new BukkitRunnable(){
					public void run(){
						//Set soil as hydrated
						block2.setData((byte) 7);
					}
				}.runTaskLater(Main.getInstance(), 2*20);
				
				//The code below slowly grows the weat plant.
				Location loc = block.getLocation();
				final Block wheat = new Location(Var.WORLD, loc.getX(), loc.getY() + 1, loc.getZ()).getBlock();
				wheat.setType(Material.CROPS);
		        new BukkitRunnable(){
		        	public void run(){
		        		byte data = wheat.getData();
		        		
		        		Logger.log(LogLevel.DEBUG, "Crops", "Someone trampled me! Current current growing state: " + data);
		        		
		        		//I used >= just for safety, it shouldn't matter.
		        		if (data >= 7){
		        			this.cancel(); 
		        			return;
		        		}
		        		wheat.setData((byte) (data + 1));
		        	}
		        }.runTaskTimer(Main.getInstance(), 0, 20);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void resetDoors(PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK &&
				(event.getClickedBlock().getType() == Material.TRAP_DOOR ||
				event.getClickedBlock().getType() == Material.IRON_TRAPDOOR)){
			
			final Block block = event.getClickedBlock();
			Player player = event.getPlayer();
			
			//If the trapdoor is one of the safe trapdoors, let the player open it.
			for (Location loc : Var.SAFE_TRAPDOORS){
				Block safe = loc.getBlock();
				if (block.getLocation().equals(safe.getLocation())){
					if (player.getGameMode() == GameMode.CREATIVE) player.sendMessage("Safe trapdoor!");
					return;
				}
			}
			
			//If player is holding a beetroot, let them open the trapdoor.
			if (player.getInventory().getItemInMainHand().getType() == Material.BEETROOT){
				player.sendMessage("Permanently closed trapdoor!");
				return;
			}
			
			//If the player is in creative mode, send them a message on how to permanently open the trapdoor.
			if (player.getGameMode() == GameMode.CREATIVE)
				player.sendMessage("Right click with a beetroot to permanently open or close a trapdoor. If this is a safe trapdoor, please add it to the list of safe trapdoors.");
			
			event.setCancelled(true);
		}
	}

}