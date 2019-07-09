package xyz.derkades.ublisk.listeners;

import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.GOLD;

import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.utils.UPlayer;

public class PlayerInteractEntity implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void tracker(PlayerInteractEntityEvent event){
		if (event.getHand() != EquipmentSlot.HAND) return;
		
		UPlayer player = new UPlayer(event);
		player.tracker(PlayerInfo.ENTITY_CLICK);
	}
	
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		if (event.getHand() != EquipmentSlot.HAND) return;
		
		Entity entity = event.getRightClicked();
		
		final UPlayer player = new UPlayer(event);
		
		if (entity instanceof ArmorStand && player.getGameMode() != GameMode.CREATIVE){
			event.setCancelled(true);
		}
		
		if (entity instanceof Player && player.isSneaking()){			
			UPlayer target = new UPlayer(entity);
			
			BaseComponent[] stats = new ComponentBuilder("View statistics")
					.bold(true)
					.color(DARK_AQUA)
					.event(new HoverEvent(
							HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("Click to open website").color(GOLD).create()))
					.event(new ClickEvent(
							ClickEvent.Action.OPEN_URL,
							"http://ublisk.robinmc.com/stats/player.php?player=" + target.getName()))
					.create();
			
			BaseComponent[] addAsFriend = new ComponentBuilder("Add as friend")
					.bold(true)
					.color(DARK_AQUA)
					.event(new HoverEvent(
							HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("Click to run /friend add " + target.getName()).color(GOLD).create()))
					.event(new ClickEvent(
							ClickEvent.Action.RUN_COMMAND,
							"/friend add " + target.getName()))
					.create();
			
			BaseComponent[] inviteToGuild = new ComponentBuilder("Invite to guild")
					.bold(true)
					.color(DARK_AQUA)
					.event(new HoverEvent(
							HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("Click to run /guild invite " + target.getName()).color(GOLD).create()))
					.event(new ClickEvent(
							ClickEvent.Action.RUN_COMMAND,
							"/guild invite " + target.getName()))
					.create();
			
			player.sendSpacers(2);
			player.sendMessage(stats);
			player.sendMessage(addAsFriend);
			player.sendMessage(inviteToGuild);
		}

	}

}
