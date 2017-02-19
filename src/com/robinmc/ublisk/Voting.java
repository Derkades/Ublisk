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

import com.robinmc.ublisk.database.Tracker;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.ItemBuilder;
import com.robinmc.ublisk.utils.java.NumberUtils;

import net.md_5.bungee.api.ChatColor;

public class Voting implements Listener {

	private static Location oldPlayerLocation;
	private static boolean playerOpeningBox = false;

	public static void openVotingBox(Player player) {
		setPlayerOpeningBox(true);
		oldPlayerLocation = player.getLocation();
		player.teleport(new Location(Var.WORLD, 3.5, 71, -62.5, 0, 0));
	}

	public static int getRandomXP() {
		return NumberUtils.randomInteger(Var.VOTE_XP_MIN, Var.VOTE_XP_MAX);
	}

	public static int getRandomGold() {
		return NumberUtils.randomInteger(Var.VOTE_GOLD_MIN, Var.VOTE_GOLD_MAX);
	}

	public static int getRandomLife() {
		return NumberUtils.randomInteger(Var.VOTE_LIFE_MIN, Var.VOTE_LIFE_MAX);
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
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onVoteBoxOpen(PlayerInteractEvent event){
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
			
			ItemStack goldItem = new ItemBuilder(Material.GOLD_NUGGET)
					.setName(ChatColor.GOLD + "" + ChatColor.BOLD + "Gold: " + gold)
					.setAmount(gold)
					.getItemStack();
			
			ItemStack xpItem = new ItemBuilder(Material.EMERALD)
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
			
			UPlayer player = new UPlayer(event);
			
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
			
			player.tracker(Tracker.VOTE_BOX);
			
			Logger.log(LogLevel.DEBUG, "Gold: " + gold + " | XP: " + xp + " | Life: " + life);
		}
	}

}
