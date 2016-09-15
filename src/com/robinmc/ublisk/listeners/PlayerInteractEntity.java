package com.robinmc.ublisk.listeners;

import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.GOLD;

import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.robinmc.ublisk.utils.UPlayer;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class PlayerInteractEntity implements Listener {
	
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
		
		if (event.isCancelled()){
			return;
		}
		
		Entity entity = event.getRightClicked();
		
		UPlayer player = UPlayer.get(event);
		
		if (entity instanceof ArmorStand && player.getGameMode() != GameMode.CREATIVE){
			event.setCancelled(true);
		}
		
		if (entity instanceof Player && player.isSneaking()){
			UPlayer target = UPlayer.get(entity);
			 
			/*
			TextComponent playerName = new TextComponent();
			playerName.setColor(ChatColor.DARK_AQUA);
			playerName.setBold(true);
			playerName.setHoverEvent(
					new HoverEvent(
							HoverEvent.Action.SHOW_TEXT, 
							new ComponentBuilder("Level " + Exp.getLevel(player) + " (" + Exp.get(player) + " XP)").color(ChatColor.GOLD).create()
							));
			
			TextComponent addAsFriend = new TextComponent();
			addAsFriend.setColor(ChatColor.AQUA);
			addAsFriend.setHoverEvent(
					new HoverEvent(
							HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("Click to add as friend").color(ChatColor.GOLD).create()
							));
			addAsFriend.setClickEvent(
					new ClickEvent(
							ClickEvent.Action.RUN_COMMAND, 
							"/f add " + target.getName()));
			
			TextComponent inviteToGuild = new TextComponent();
			inviteToGuild.setColor(ChatColor.AQUA);
			inviteToGuild.setHoverEvent(
					new HoverEvent(
							HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("Click to invite to guild").color(ChatColor.GOLD).create()
							));
			inviteToGuild.setClickEvent(
					new ClickEvent(
							ClickEvent.Action.SUGGEST_COMMAND,
							"Coming soon!"
							));
			
			TextComponent spacing = new TextComponent(" | ");
			spacing.setColor(ChatColor.WHITE);
			
			TextComponent options = new TextComponent();
			options.addExtra(addAsFriend);
			options.addExtra(spacing);
			options.addExtra(inviteToGuild);
			
			player.spigot().sendMessage(playerName);
			player.spigot().sendMessage(options);
			*/
			
			BaseComponent[] stats = new ComponentBuilder("Click here to view statistics")
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
							"friend add " + target.getName()))
					.create();
			
			// TODO Invite to guild clickable message
			
			player.sendSpacers(2);
			player.sendMessage(stats);
			player.sendMessage(addAsFriend);
		}

	}

}
