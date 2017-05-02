package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.robinmc.ublisk.utils.UPlayer;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class StatsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		UPlayer player = new UPlayer(sender);
		
		if (args.length == 1){
			player.sendMessage("Viewing other player's statistics is not yet supported. Please check back later."); // TODO Allow viewing of other player's stats
			return true;
		} else if (args.length == 0){
			BaseComponent[] hoverText = new ComponentBuilder("yes click me pls").create();
			
			BaseComponent[] text = new ComponentBuilder("Click here to open your statistics page in your browser.")
					.color(ChatColor.AQUA)
					.bold(true)
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText))
					.event(new ClickEvent(ClickEvent.Action.OPEN_URL, player.getStatsURL()))
					.create();
			
			player.sendMessage(text);
			
			return true;
		} else {
			return false;
		}
	}

}
