package com.robinmc.ublisk.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Weapon;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.EntityUtils;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.enums.Loot;
import com.robinmc.ublisk.utils.enums.Perms;
import com.robinmc.ublisk.utils.enums.Tracker;
import com.robinmc.ublisk.utils.quest.NPCUtils;
import com.robinmc.ublisk.utils.third_party.Lag;
import com.robinmc.ublisk.utils.variable.Message;
import com.robinmc.ublisk.utils.variable.Var;

public class Debug implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (player.hasPermission(Perms.COMMAND_DEBUG.getPerm())){
				if (args.length == 2){
					if (args[0].equalsIgnoreCase("xp")){
						int xp = Integer.parseInt(args[1]);
						Exp.set(player, xp);
						return true;
					} else if (args[0].equalsIgnoreCase("hunger")){
						Player player2 = Bukkit.getPlayer(args[1]);
						player.sendMessage("Food: " + player2.getFoodLevel());
						return true;
					} else if (args[0].equals("refreshxp")){
						Player target = Bukkit.getPlayer(args[1]);
						Exp.refresh(player);
						player.sendMessage("XP refreshed!");
						player.sendMessage("Config XP: " + Exp.get(target));
						player.sendMessage("With division: " + Math.round(Exp.get(player) / Var.xpDivision));
						player.sendMessage("Bukkit level: " + Exp.getLevel(player));
						return true;
					} else {
						player.sendMessage(Message.WRONG_USAGE.get());
						return true;
					}
				} else if (args.length == 1){
					if (args[0].equalsIgnoreCase("kill")){
						Bukkit.broadcastMessage(Message.ENTITIES_REMOVED.get());
						EntityUtils.removeMobs();
						return true;
					} else if (args[0].equalsIgnoreCase("npcrespawn")){
						NPCUtils api = new NPCUtils();
						api.spawnAll();
						api.despawnAll();
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
					} else if (args[0].equals("loot")){
						Loot.spawnRandomLoot();
						return true;
					} else if (args[0].equals("removeloot")){
						Loot.removeLoot();
						return true;
					} else if (args[0].equals("health")){
						player.sendMessage(player.getHealth() + "");
						return true;
					} else if (args[0].equals("sync")){
						Tracker.syncAll();
						return true;
					} else if (args[0].equals("despawnallnpc")){
						new NPCUtils().despawnAll();
						return true;
					} else if (args[0].equals("lag")){
						player.sendMessage("TPS: " + Lag.getTPS());
						return true;
					} else {
						player.sendMessage(Message.WRONG_USAGE.get());
						return true;
					}
				} else {
					player.sendMessage(Message.WRONG_USAGE.get());
					return true;
				}
			} else {
				player.sendMessage(Message.NO_PERMISSION.get());
				return true;
			}
			
		} else {
			sender.sendMessage(Message.NOT_A_PLAYER.get());
			return true;
		}
	}

}
