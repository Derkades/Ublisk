package com.robinmc.ublisk.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.NPCs;
import com.robinmc.ublisk.Perms;
import com.robinmc.ublisk.Weapon;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Exp;

public class Debug implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (player.hasPermission(Perms.DEBUG_COMMAND.getPerm())){
				if (args.length == 2){
					if (args[0].equalsIgnoreCase("xp")){
						int xp = Integer.parseInt(args[1]);
						Exp.set(player, xp);
						return true;
					} if (args[0].equalsIgnoreCase("hunger")){
						Player player2 = Bukkit.getPlayer(args[1]);
						player.sendMessage("Food: " + player2.getFoodLevel());
						return true;
					} else {
						player.sendMessage(Messages.wrongUsage());
						return true;
					}
				} else if (args.length == 1){
					if (args[0].equalsIgnoreCase("kill")){
						Bukkit.broadcastMessage(Messages.removedMobs());
						return true;
					} else if (args[0].equalsIgnoreCase("npcrespawn")){
						NPCs.despawnAll();
						NPCs.spawnAll();
						player.sendMessage("All NPCs have been respawned!");
						return true;
					} else if (args[0].equalsIgnoreCase("sword")){
						PlayerInventory inv = player.getInventory();
						inv.addItem(Weapon.oldWoodenSword());
						return true;
					} else if (args[0].equalsIgnoreCase("cmd")){
						UUID uuid = player.getUniqueId();
						if (HashMaps.disableCommandLog.get(uuid)){
							player.sendMessage("Enabled!");
							HashMaps.disableCommandLog.put(uuid, false);
						} else {
							player.sendMessage("Disabled!");
							HashMaps.disableCommandLog.put(uuid, true);
						}
						return true;
					} else if (args[0].equals("reload")){
						Config.reload();
						return true;
					} else {
						player.sendMessage(Messages.wrongUsage());
						return true;
					}
				} else {
					player.sendMessage(Messages.wrongUsage());
					return true;
				}
			} else {
				player.sendMessage(Messages.noPermission("developer"));
				return true;
			}
			
		} else {
			sender.sendMessage(Messages.noPlayer());
			return true;
		}
	}

}
