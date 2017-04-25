package com.robinmc.ublisk;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.database.PlayerInfo;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;
import com.robinmc.ublisk.utils.inventory.ItemBuilder;
import com.robinmc.ublisk.utils.java.Random;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import net.md_5.bungee.api.ChatColor;

public class Voting implements Listener {
	
	public static final int VOTE_XP_MIN = 20;
	public static final int VOTE_XP_MAX = 100;
	
	public static final int VOTE_GOLD_MIN = 0;
	public static final int VOTE_GOLD_MAX = 50;
	
	public static final int VOTE_LIFE_MIN = 0;
	public static final int VOTE_LIFE_MAX = 2;

	private static Location oldPlayerLocation;
	private static boolean playerOpeningBox = false;

	public static void openVotingBox(Player player) {
		setPlayerOpeningBox(true);
		oldPlayerLocation = player.getLocation();
		player.teleport(new Location(Var.WORLD, 3.5, 71, -62.5, 0, 0));
	}

	public static int getRandomXP() {
		//return NumberUtils.randomInteger(VOTE_XP_MIN, VOTE_XP_MAX);
		return Random.getRandomInteger(VOTE_XP_MIN, VOTE_XP_MAX);
	}

	public static int getRandomGold() {
		//return NumberUtils.randomInteger(VOTE_GOLD_MIN, VOTE_GOLD_MAX);
		return Random.getRandomInteger(VOTE_GOLD_MIN, VOTE_GOLD_MAX);
	}

	public static int getRandomLife() {
		//return NumberUtils.randomInteger(VOTE_LIFE_MIN, VOTE_LIFE_MAX);
		return Random.getRandomInteger(VOTE_LIFE_MIN, VOTE_LIFE_MAX);
	}

	public static boolean isVotingChest(Block block) {
		Location loc = block.getLocation();
		return loc.getBlockX() == 3 && loc.getBlockY() == 71 && loc.getBlockZ() == -55;
	}

	public static Location getOldPlayerLocation() {
		return oldPlayerLocation;
	}

	public static boolean isPlayerOpeningBox() {
		return playerOpeningBox;
	}

	public static void setPlayerOpeningBox(boolean playerOpeningBox) {
		Voting.playerOpeningBox = playerOpeningBox;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onVoteBoxOpen(PlayerInteractEvent event){
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}
		
		UPlayer player = new UPlayer(event);
		
		if (player.isSneaking()){
			return;
		}

		Block block = event.getClickedBlock();
		if (Voting.isVotingChest(block)){
			Chest chest = (Chest) block.getState();
			Inventory inv = chest.getInventory();
			
			int gold = Voting.getRandomGold();
			int xp = Voting.getRandomXP();
			int life = Voting.getRandomLife();
			
			ItemStack goldItem = new ItemBuilder(Material.GOLD_NUGGET)
					.setName(ChatColor.GOLD + "" + ChatColor.BOLD + "Gold: " + gold)
					.setAmount(gold)
					.getItemStack();
			
			ItemStack xpItem = new ItemBuilder(Material.EXP_BOTTLE)
					.setName(ChatColor.GREEN + "" + ChatColor.BOLD + "XP: " + xp)
					.setAmount(xp)
					.getItemStack();
			
			ItemStack lifeItem = new ItemBuilder(Material.NETHER_STAR)
					.setName(ChatColor.BOLD + "Life Crystals: " + life)
					.setAmount(life)
					.getItemStack();
			
			inv.setItem(12, goldItem);
			inv.setItem(13, xpItem);
			inv.setItem(14, lifeItem);
			
			if (gold !=0){
				new ItemBuilder(Material.GOLD_NUGGET)
				.setAmount(gold)
				.addToInventory(player);
			}
			
			if (xp != 0){
				player.addXP(xp);
			}
			
			if (life != 0){
				player.setLifeCrystals(player.getLifeCrystals() + life);
			}
			
			player.tracker(PlayerInfo.VOTE_BOX);
			
			Logger.log(LogLevel.DEBUG, "Gold: " + gold + " | XP: " + xp + " | Life: " + life);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onVote(VotifierEvent event){
		Vote vote = event.getVote();
		UPlayer player;
		try {
			player = new UPlayer(vote.getUsername());
		} catch (PlayerNotFoundException e) {
			e.printStackTrace(); //TODO Deal with player not online exception
			return;
		}
		
		int points = Random.getRandomInteger(1, 3);
		player.setVotingPoints(player.getVotingPoints() + points);
		Ublisk.broadcastPrefixedMessage(player.getName() + " has voted and got " + points + " points! Vote at " + Var.VOTE_URL);
		Logger.log(LogLevel.INFO, "Vote", player.getName() + " has voted at " + vote.getServiceName() + " (" + vote.getAddress() + ") and got " + points + " points.");
	}

}
