package xyz.derkades.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.type.Farmland;
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
import org.bukkit.material.Crops;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.iconmenus.MainMenu;
import xyz.derkades.ublisk.modules.Loot;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.URunnable;
import xyz.derkades.ublisk.utils.inventory.Item;
import xyz.derkades.ublisk.utils.inventory.UInventory;

public class PlayerInteract implements Listener {

	@EventHandler(ignoreCancelled = false)
	public void onInteract(final PlayerInteractEvent event){
		final UPlayer player = new UPlayer(event);
		final Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR){
			final UInventory inv = player.getInventory();
			final Material item = inv.getItemInMainHand().getType();
			if (item == Material.CHEST && !player.isInBuilderMode()){
				new MainMenu(player).open();
				event.setCancelled(true);
			} else if (item == Material.END_CRYSTAL){
				player.openEnderchest();
				event.setCancelled(true);
			}
		}
	}

	//ignoreCancelled = true - Still track clicks if they are cancelled
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
	public void tracker(final PlayerInteractEvent event){
		final UPlayer player = new UPlayer(event);
		final Action action = event.getAction();

		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
			player.tracker(PlayerInfo.RIGHT_CLICKED);
		}

		if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK){
			player.tracker(PlayerInfo.LEFT_CLICKED);
		}

		if (action == Action.RIGHT_CLICK_BLOCK){
			if (event.getClickedBlock().getType() == Material.CHEST){
				final Chest chest = (Chest) event.getClickedBlock().getState();
				if (Loot.isLoot(chest)){
					player.tracker(PlayerInfo.LOOT_FOUND);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void spellTest(final PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD){
				final Block block = event.getClickedBlock();
				if (block.getType() == Material.COBBLESTONE){
					@SuppressWarnings("deprecation")
					final
					FallingBlock fall = block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
					final Vector velocity = fall.getVelocity();
					velocity.setY(velocity.getY() + 1.0);
					fall.setVelocity(velocity);
					block.setType(Material.AIR);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
						final Location loc = block.getLocation();
						loc.setY(loc.getY() + 1);
						final ThrownPotion potion = (ThrownPotion) Var.WORLD.spawnEntity(loc, EntityType.SPLASH_POTION);
						final PotionEffect effect = new PotionEffect(PotionEffectType.HARM, 1, 2);
						potion.getEffects().add(effect);
					}, 2*20);
				}
			}
		}
	}

	@EventHandler(priority=EventPriority.LOW)
	public void staffTool(final PlayerInteractEvent event){
		final ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();


		if (itemInHand.getType() != Material.COAL_ORE || event.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}

		final Player player = event.getPlayer();
		final String itemName = itemInHand.getItemMeta().getDisplayName();
		final Block block = event.getClickedBlock();

		event.setCancelled(true);

		if (itemName == null){
			sendStaffToolInfoMessage(player);
			return;
		}

		if (itemName.contains("farmland")){
			block.setType(Material.FARMLAND);
			((Farmland) block).setMoisture(7);
			final Location loc = block.getLocation();
			final Block wheat = new Location(Var.WORLD, loc.getX(), loc.getY() + 1, loc.getZ()).getBlock();
			wheat.setType(Material.WHEAT);
			((Crops) wheat.getState()).setState(CropState.RIPE);
		//} else if (itemName.contains("invis")){
		//	block.setData((byte) 0);
		//	block.setType(Material.MOVING_PISTON);
		//	event.getPlayer().sendMessage("Placed invisible block. To remove invisible block, type /u rinv while standing inside an invisible block.");
		} else if (itemName.contains("coal")){
			block.setType(Material.GLASS);
			new URunnable(){
				@Override
				public void run(){
					block.setType(Material.COAL_ORE);
				}
			}.runLater(1*20);
		} else if (itemName.contains("lighting")){
			//final Chunk chunk = ((CraftChunk) block.getChunk()).getHandle();
			//chunk.initLighting();
			player.sendMessage("this feature is temporarily unavailable");
		} else {
			sendStaffToolInfoMessage(player);
		}
	}

	private static void sendStaffToolInfoMessage(final Player player){
		final String[] strings = new String[]{
				"",
				"Mogelijke namen:",
		//		"invis - Plaatst onzichtbaar block",
				"farmland - Plaatst farmland met wheat",
				"coal - Plaatst coal ore",
				"lighting - Force lighting updates in a chunk"
		};

		for (final String string : strings) player.sendMessage(string);
		new UPlayer(player).getInventory().addItem(new Item(Material.ANVIL).setName("Use this to rename the coal block."));
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled = false)
	public void onTrample(final PlayerInteractEvent event){
	    if (event.getAction() == Action.PHYSICAL){
	    	final Block block = event.getClickedBlock();
			if (block == null){
				return;
			}

			final Material material = block.getType();

			if (material == Material.FARMLAND){
				event.setUseInteractedBlock(PlayerInteractEvent.Result.DENY);
				event.setCancelled(true);

				//Set soil as not hydrated
				block.setType(Material.FARMLAND);
				final Farmland farmland = (Farmland) block;
				farmland.setMoisture(0);
				new BukkitRunnable(){
					@Override
					public void run(){
						//Set soil as hydrated
						farmland.setMoisture(farmland.getMaximumMoisture());
					}
				}.runTaskLater(Main.getInstance(), 2*20);

				//The code below slowly grows the weat plant.
				final Location loc = block.getLocation();
				final Block wheat = new Location(Var.WORLD, loc.getX(), loc.getY() + 1, loc.getZ()).getBlock();
				wheat.setType(Material.WHEAT);
				final Crops crops = (Crops) wheat.getState();
		        new BukkitRunnable(){
		        	@Override
					public void run(){
		        		final int data = wheat.getData();

		        		Logger.log(LogLevel.DEBUG, "Crops", "Someone trampled me! Current current growing state: " + data);

		        		//I used >= just for safety, it shouldn't matter.
		        		if (data >= 7){
		        			this.cancel();
		        			return;
		        		}

		        		if (data == 0) {
		        			crops.setState(CropState.SEEDED);
		        		} else if (data == 1) {
		        			crops.setState(CropState.GERMINATED);
		        		} else if (data == 2) {
		        			crops.setState(CropState.VERY_SMALL);
		        		} else if (data == 3) {
		        			crops.setState(CropState.SMALL);
		        		} else if (data == 4) {
		        			crops.setState(CropState.MEDIUM);
		        		} else if (data == 5) {
		        			crops.setState(CropState.TALL);
		        		} else if (data == 6) {
		        			crops.setState(CropState.VERY_TALL);
		        		} else if (data == 7) {
		        			crops.setState(CropState.RIPE);
		        		} else {
		        			throw new AssertionError();
		        		}
		        	}
		        }.runTaskTimer(Main.getInstance(), 0, 20);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void chestOpen(final PlayerInteractEvent event){
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK ||
				event.getClickedBlock().getType() != Material.CHEST)
			return;

		final Chest chest = (Chest) event.getClickedBlock().getState();

		final UPlayer player = new UPlayer(event);
		if (player.isInBuilderMode()){
			if (!Loot.isLoot(chest)) {
				//Send message if chest is not loot
				player.sendMessage(ChatColor.RED + "Warning: Regular players won't be able to open this chest.");
			}
			return;
		}

		if (!Loot.isLoot(chest)){
			event.setCancelled(true); //Cancel chest right click if chest is not loot
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void removePinkWool(final PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK &&
				event.getMaterial() == Material.PINK_WOOL){
			event.setCancelled(true);
			new URunnable(){
				@Override
				public void run(){
					event.getPlayer().kickPlayer("java.net.ConnectException: Connection refused: no further information");
				}
			}.runLater(2*20);
		}
	}

}