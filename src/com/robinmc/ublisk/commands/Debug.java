package com.robinmc.ublisk.commands;

import java.io.File;
import java.text.SimpleDateFormat;

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

import com.robinmc.ublisk.Loot;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.WorldEditCUI;
import com.robinmc.ublisk.chat.Trigger;
import com.robinmc.ublisk.mob.Mob;
import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.utils.DataFile;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.Lag;
import com.robinmc.ublisk.utils.PacketListener;
import com.robinmc.ublisk.utils.Time;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.exception.GroupNotFoundException;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;
import com.robinmc.ublisk.utils.inventory.ItemBuilder;
import com.robinmc.ublisk.utils.perm.Permission;
import com.robinmc.ublisk.utils.perm.PermissionGroup;
import com.robinmc.ublisk.weapons.Weapon;
import com.robinmc.ublisk.weapons.abilities.TestAbility;
import com.robinmc.ublisk.weapons.sword.Sword;

import net.md_5.bungee.api.ChatColor;

public class Debug implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			UPlayer player = new UPlayer(sender);
			if (player.hasPermission(Permission.COMMAND_DEBUG)) {
				if (args.length == 3) {
					if (args[0].equals("group")) {
						PermissionGroup group;
						try {
							group = PermissionGroup.fromString(args[2]);
						} catch (GroupNotFoundException e) {
							player.sendMessage("unknown group");
							return true;
						}
						UPlayer target;
						try {
							target = new UPlayer(args[1]);
						} catch (PlayerNotFoundException e) {
							player.sendMessage("player not found");
							return true;
						}
						player.sendMessage("old: " + group.getName());
						target.setGroup(group);
						player.sendMessage("group successful: " + target.getGroup().getName());
						return true;
					} else {
						player.sendMessage(Message.WRONG_USAGE);
						return true;
					}
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("xp")) {
						int xp = Integer.parseInt(args[1]);
						Exp.set(player.getPlayer(), xp);
						return true;
					} else if (args[0].equalsIgnoreCase("hunger")) {
						Player player2 = Bukkit.getPlayer(args[1]);
						player.sendMessage("Food: " + player2.getFoodLevel());
						return true;
					} else if (args[0].equals("refreshxp")) {
						Player target = Bukkit.getPlayer(args[1]);
						player.refreshXP();
						player.sendMessage("XP refreshed!");
						player.sendMessage("Config XP: " + Exp.get(target));
						player.sendMessage("With division: " + Math.round(Exp.get(target) / Var.XP_DIVISION));
						player.sendMessage("Bukkit level: " + Exp.getLevel(target));
						return true;
					} else if (args[0].equals("life")) {
						int life = Integer.parseInt(args[1]);
						player.setLifeCrystals(life);
						return true;
					} else if (args[0].equals("vote")) {
						UPlayer target;
						try {
							target = new UPlayer(args[1]);
						} catch (PlayerNotFoundException e) {
							player.sendMessage("player not found");
							return true;
						}
						player.sendMessage("Voting points: " + target.getVotingPoints());
						return true;
					} else if (args[0].equals("inv")) {
						UPlayer target;
						try {
							target = new UPlayer(args[1]);
						} catch (PlayerNotFoundException e) {
							player.sendMessage(Message.PLAYER_NOT_FOUND);
							return true;
						}
						PlayerInventory inv = target.getPlayer().getInventory();
						player.getPlayer().openInventory(inv);
						return true;
					} else if (args[0].equals("skull")) {
						new ItemBuilder(args[1]).addToInventory(player);
						return true;
					} else if (args[0].equals("disablepl")){
						Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance().getServer().getPluginManager().getPlugin(args[1]));
						return true;
					} else if (args[0].equals("enablepl")){
						Main.getInstance().getServer().getPluginManager().enablePlugin(Main.getInstance().getServer().getPluginManager().getPlugin(args[1]));
						return true;
					} else {
						player.sendMessage(Message.WRONG_USAGE);
						return true;
					}
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("kill")) {
						Ublisk.broadcastMessage(Message.ENTITIES_REMOVED);
						Mob.removeMobs();
						return true;
					} else if (args[0].equals("loot")) {
						Loot.getRandomLoot().spawn();
						return true;
					} else if (args[0].equals("removeloot")) {
						Loot.removeLoot();
						return true;
					} else if (args[0].equals("lag")) {
						player.sendMessage("TPS: " + Lag.getTPS());
						return true;
					} else if (args[0].equals("list")) {
						for (Entity entity : Var.WORLD.getEntities()) {
							player.sendMessage(entity.getName() + " : " + entity.getCustomName() + " : "
									+ entity.getLocation().getBlockX() + " : " + entity.getLocation().getBlockZ()
									+ " : " + entity.getLocation().getChunk());
						}
						return true;
					} else if (args[0].equals("day")) {
						while (true) {
							if (Time.isDay()) {
								break;
							}
							Time.add(100);
						}
						return true;
					} else if (args[0].equals("npc")) {
						NPC.respawnAll();
						return true;
					} else if (args[0].equals("triggers")) {
						for (Trigger trigger : Trigger.values())
							player.sendMessage(trigger.getTrigger() + ": " + trigger.getMessage());
						return true;
					} else if (args[0].equals("ability")) {
						player.doAbility(new TestAbility());
						return true;
					} else if (args[0].equals("pack")) {
						player.sendMessage("This command is deprecated. Please use /pack instead.");
						return true;
					} else if (args[0].equals("actionbar")) {
						player.sendActionBarMessage(ChatColor.RED + "TEST!");
						return true;
					} else if (args[0].equals("rinv")) {
						final Block block = player.getLocation().getBlock();
						block.setType(Material.STONE);
						new BukkitRunnable() {

							public void run() {
								block.setType(Material.AIR);
							}
						}.runTaskLater(Main.getInstance(), 5);
						return true;
					} else if (args[0].equals("sword")) {
						PlayerInventory inv = player.getInventory();

						for (Weapon weapon : Weapon.getWeapons()) {
							if (weapon instanceof Sword) {
								inv.addItem(((Sword) weapon).getItemStack());
							}
						}
						return true;
					} else if (args[0].equals("weapontest")) {
						for (Weapon weapon : Weapon.getWeapons()) {
							player.sendMessage(
									Weapon.itemStackIsWeapon(player.getInventory().getItemInMainHand(), weapon));
						}
						return true;
					} else if (args[0].equals("version")) {
						File pluginJar = new File(Main.getInstance().getDataFolder().getParentFile(), "Ublisk.jar");
						long lastModified = pluginJar.lastModified();
						SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
						String formatted = format.format(lastModified);
						player.sendMessage("Last updated: " + formatted);
						return true;
					} else if (args[0].equals("saferl")){
						WorldEditCUI.onDisable();
						PacketListener.closeAllOpenSockets();
						for (DataFile file : DataFile.values()) file.save();
						Bukkit.getServer().reload();
						return true;
					} else if (args[0].equals("save")){
						for (DataFile file : DataFile.values()) file.save();
						return true;
					} else {
						player.sendMessage(Message.WRONG_USAGE);
						return true;
					}
				} else {
					player.sendMessage(Message.WRONG_USAGE);
					return true;
				}
			} else {
				player.sendMessage(Message.NO_PERMISSION);
				return true;
			}

		} else {
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}
	}

}
