package xyz.derkades.ublisk.modules;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.derkutils.Random;
import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.exception.PlayerNotFoundException;
import xyz.derkades.ublisk.utils.inventory.Item;

public class Voting extends UModule {

	public static final int VOTE_XP_MIN = 20;
	public static final int VOTE_XP_MAX = 100;

	public static final int VOTE_GOLD_MIN = 0;
	public static final int VOTE_GOLD_MAX = 50;

	public static final int VOTE_LIFE_MIN = 0;
	public static final int VOTE_LIFE_MAX = 2;

	public static Location oldPlayerLocation = null;
	public static Player playerOpeningBox = null;

	public static void openVotingBox(final Player player) {
		playerOpeningBox = player;
		oldPlayerLocation = player.getLocation();
		player.teleport(new Location(Var.WORLD, 3.5, 71, -62.5, 0, 0));
	}

	public static int getRandomXP() {
		return Random.getRandomInteger(VOTE_XP_MIN, VOTE_XP_MAX);
	}

	public static int getRandomGold() {
		return Random.getRandomInteger(VOTE_GOLD_MIN, VOTE_GOLD_MAX);
	}

	public static int getRandomLife() {
		return Random.getRandomInteger(VOTE_LIFE_MIN, VOTE_LIFE_MAX);
	}

	public static boolean isVotingChest(final Block block) {
		final Location loc = block.getLocation();
		return loc.getBlockX() == 3 && loc.getBlockY() == 71 && loc.getBlockZ() == -55;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onVoteBoxOpen(final PlayerInteractEvent event){
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}

		final UPlayer player = new UPlayer(event);

		if (player.isSneaking()){
			return;
		}

		final Block block = event.getClickedBlock();
		if (Voting.isVotingChest(block)){
			final Chest chest = (Chest) block.getState();
			final Inventory inv = chest.getInventory();

			final int gold = Voting.getRandomGold();
			final int xp = Voting.getRandomXP();
			final int life = Voting.getRandomLife();

			final Item goldItem = new Item(Material.GOLD_NUGGET)
					.setName(ChatColor.GOLD + "" + ChatColor.BOLD + "Gold: " + gold)
					.setAmount(gold);

			final Item xpItem = new Item(Material.EXPERIENCE_BOTTLE)
					.setName(ChatColor.GREEN + "" + ChatColor.BOLD + "XP: " + xp)
					.setAmount(xp);

			final Item lifeItem = new Item(Material.NETHER_STAR)
					.setName(ChatColor.BOLD + "Life Crystals: " + life)
					.setAmount(life);

			inv.setItem(12, goldItem.getItemStack());
			inv.setItem(13, xpItem.getItemStack());
			inv.setItem(14, lifeItem.getItemStack());

			if (gold !=0){
				player.getInventory().addItem(Material.GOLD_NUGGET, gold);
			}

			if (xp != 0){
				player.addXP(xp);
			}

			if (life != 0){
				player.setLifeCrystals(player.getLifeCrystals() + life);
			}

			player.tracker(PlayerInfo.VOTE_BOX);

			Logger.log(LogLevel.DEBUG, "Voting", "Gold: " + gold + " | XP: " + xp + " | Life: " + life);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onItemMove(final InventoryMoveItemEvent event){
		event.setCancelled(playerOpeningBox != null &&
				event.getInitiator().getHolder() instanceof Player &&
				((Player) event.getInitiator().getHolder()).getName().equals(playerOpeningBox.getName()));
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onVote(final VotifierEvent event){
		final Vote vote = event.getVote();
		UPlayer player;
		try {
			player = new UPlayer(vote.getUsername());
		} catch (final PlayerNotFoundException e) {
			e.printStackTrace(); //TODO Deal with player not online exception
			return;
		}

		final int points = Random.getRandomInteger(1, 3);
		player.setVotingPoints(player.getVotingPoints() + points);
		Ublisk.broadcastPrefixedMessage(player.getName() + " has voted and got " + points + " points! Vote at " + Var.VOTE_URL);
		Logger.log(LogLevel.INFO, "Vote", player.getName() + " has voted at " + vote.getServiceName() + " (" + vote.getAddress() + ") and got " + points + " points.");
	}

	@EventHandler
	public void onInvClose(final InventoryCloseEvent event){
		if (event.getView().getTitle().contains("Box") && !event.getView().getTitle().contains("Shulker")){
			Voting.playerOpeningBox = null;
			new BukkitRunnable(){
				@Override
				public void run(){
					final HumanEntity human = event.getPlayer();
					human.teleport(Voting.oldPlayerLocation);
					Voting.oldPlayerLocation = null;
				}
			}.runTaskLater(Main.getInstance(), 2L);
		}
	}

}
