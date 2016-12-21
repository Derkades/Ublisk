package com.robinmc.ublisk.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Loot;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.NewTracker;
import com.robinmc.ublisk.PlayerInfo;
import com.robinmc.ublisk.Scoreboard;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.abilities.Ability;
import com.robinmc.ublisk.chat.Trigger;
import com.robinmc.ublisk.mob.Mob;
import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.Lag;
import com.robinmc.ublisk.utils.Time;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.GroupNotFoundException;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;
import com.robinmc.ublisk.utils.inventory.item.ItemBuilder;
import com.robinmc.ublisk.utils.perm.Permission;
import com.robinmc.ublisk.utils.perm.PermissionGroup;
import com.robinmc.ublisk.weapon.SwordsmanWeapon;

import net.md_5.bungee.api.ChatColor;

public class Debug implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			UPlayer player = UPlayer.get(sender);
			if (player.hasPermission(Permission.COMMAND_DEBUG)){
				if (args.length == 3){
					if (args[0].equals("group")){
						PermissionGroup group;
						try {
							group = PermissionGroup.fromString(args[2]);
						} catch (GroupNotFoundException e) {
							player.sendMessage("unknown group");
							return true;
						}
						UPlayer target;
						try {
							target = UPlayer.get(args[1]);
						} catch (PlayerNotFoundException e) {
							player.sendMessage("player not found");
							return true;
						}
						player.sendMessage("old: " + group.getName());
						target.setGroup(group);
						player.sendMessage("group successful: " + target.getGroup().getName());
						return true;
					} else {
						player.sendMessage(Message.WRONG_USAGE.get());
						return true;
					}
				} else if (args.length == 2){
					if (args[0].equalsIgnoreCase("xp")){
						int xp = Integer.parseInt(args[1]);
						player.setXP(xp);
						return true;
					} else if (args[0].equalsIgnoreCase("hunger")){
						Player player2 = Bukkit.getPlayer(args[1]);
						player.sendMessage("Food: " + player2.getFoodLevel());
						return true;
					} else if (args[0].equals("refreshxp")){
						Player target = Bukkit.getPlayer(args[1]);
						player.refreshXP();
						player.sendMessage("XP refreshed!");
						player.sendMessage("Config XP: " + Exp.get(target));
						player.sendMessage("With division: " + Math.round(Exp.get(target) / Var.XP_DIVISION));
						player.sendMessage("Bukkit level: " + Exp.getLevel(target));
						return true;
					} else if (args[0].equals("life")){
						int life = Integer.parseInt(args[1]);
						player.setLifeCrystals(life);
						return true;
					} else if (args[0].equals("vote")){
						UPlayer target;
						try {
							target = UPlayer.get(args[1]);
						} catch (PlayerNotFoundException e) {
							player.sendMessage("player not found");
							return true;
						}
						player.sendMessage("Voting points: " + target.getVotingPoints());
						return true;
					} else if (args[0].equals("inv")){
						UPlayer target;
						try {
							target = UPlayer.get(args[1]);
						} catch (PlayerNotFoundException e){
							player.sendMessage(Message.PLAYER_NOT_FOUND);
							return true;
						}
						PlayerInventory inv = target.getPlayer().getInventory();
						player.getPlayer().openInventory(inv);
						return true;
					} else if (args[0].equals("skull")){
						player.getInventory().add(new ItemBuilder(args[1]).getItemStack());
						return true;
					} else {
						player.sendMessage(Message.WRONG_USAGE.get());
						return true;
					}
				} else if (args.length == 1){
					if (args[0].equalsIgnoreCase("kill")){
						Bukkit.broadcastMessage(Message.ENTITIES_REMOVED.get());
						Mob.removeMobs();
						return true;
					} else if (args[0].equalsIgnoreCase("cmd")){
						UUID uuid = player.getUniqueId();
						if (HashMaps.DISABLE_COMMAND_LOG.get(uuid)){
							player.sendMessage("Enabled!");
							HashMaps.DISABLE_COMMAND_LOG.put(uuid, false);
						} else {
							player.sendMessage("Disabled!");
							HashMaps.DISABLE_COMMAND_LOG.put(uuid, true);
						}
						return true;
					} else if (args[0].equals("loot")){
						Loot.getRandomLoot().spawn();
						return true;
					} else if (args[0].equals("removeloot")){
						Loot.removeLoot();
						return true;
					} else if (args[0].equals("health")){
						player.sendMessage(player.getHealth() + "");
						return true;
					} else if (args[0].equals("lag")){
						player.sendMessage("TPS: " + Lag.getTPS());
						return true;
					} else if (args[0].equals("list")){
						for (Entity entity : Var.WORLD.getEntities()){
							player.sendMessage(entity.getName() + " : " + entity.getCustomName() + " : " + entity.getLocation().getBlockX() + " : " + entity.getLocation().getBlockZ() + " : " + entity.getLocation().getChunk());
						}
						return true;
					} else if (args[0].equals("sword")){
						for (SwordsmanWeapon weapon : SwordsmanWeapon.values()){
							player.getInventory().addWeapon(weapon);
						}
						return true;
					} else if (args[0].equals("day")){
						while (true){
							if (Time.isDay()){
								break;
							}
							Time.add(100);
						}
						return true;
					} else if (args[0].equals("npc")){
						NPC.respawnAll();
						return true;
					} else if (args[0].equals("triggers")){
						for (Trigger trigger : Trigger.values())
							player.sendMessage(trigger.getTrigger() + ": " + trigger.getMessage());
						return true;
					} else if (args[0].equals("ability")){
						Ability.TEST.doAbility(player);
						return true;
					} else if (args[0].equals("pack")){
						player.sendMessage("This command is deprecated. Please use /pack instead.");
						return true;
					} else if (args[0].equals("actionbar")){
						player.sendActionBarMessage(ChatColor.RED + "TEST!");
						return true;
					} else if (args[0].equals("playerinfo")){
						PlayerInfo.syncInfo(player);
						return true;
					} else if (args[0].equals("rinv")){
						final Block block = player.getLocation().getBlock();
						block.setType(Material.STONE);
						new BukkitRunnable(){
							public void run(){
								block.setType(Material.AIR);
							}
						}.runTaskLater(Main.getInstance(), 5);
						return true;
					} else if (args[0].equals("sidebar")){
						Scoreboard.getScoreboard(player).showTo(player.getPlayer());
						return true;
					} else if (args[0].equals("trackerclick")){
						player.tracker(NewTracker.RIGHT_CLICKED);
						player.sendMessage(NewTracker.RIGHT_CLICKED.get(player.getUniqueId()));
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
