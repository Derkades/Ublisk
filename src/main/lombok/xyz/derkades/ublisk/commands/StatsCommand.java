package xyz.derkades.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.exception.PlayerNotFoundException;

public class StatsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		UPlayer player = new UPlayer(sender);
		
		if (args.length == 1){
			UPlayer target = null;
			try {
				target = new UPlayer(args[1]);
			} catch (PlayerNotFoundException e) {
				player.sendMessage(Message.PLAYER_NOT_FOUND);
				return true;
			}
			
			BaseComponent[] hoverText = new ComponentBuilder("yes click me pls").create();
			
			BaseComponent[] text = new ComponentBuilder("Click here to open " + target.getName() + "'s statistics page in your browser.")
					.color(ChatColor.AQUA)
					.bold(true)
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText))
					.event(new ClickEvent(ClickEvent.Action.OPEN_URL, target.getStatsURL()))
					.create();
			
			player.sendMessage(text);
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
