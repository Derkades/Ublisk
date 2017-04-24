package com.robinmc.ublisk.commands.ublisk;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.DataFile;
import com.robinmc.ublisk.Loot;
import com.robinmc.ublisk.Loot.LootChest;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.Town;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.chat.Trigger;
import com.robinmc.ublisk.ext.com.bobacadodl.imgmessage.ImageChar;
import com.robinmc.ublisk.mob.Mob;
import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.utils.Lag;
import com.robinmc.ublisk.utils.Time;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.shapes.Direction;
import com.robinmc.ublisk.utils.shapes.Shapes;
import com.robinmc.ublisk.weapons.Weapon;
import com.robinmc.ublisk.weapons.sword.Sword;
import com.sun.org.apache.bcel.internal.generic.NEW;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public abstract class UbliskCommand {
	
	/*TEST(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			player.sendMessage("test");	
		}
	}, "Sends a test message", "test", "test2"),
	
	TOWN(new CommandRunnable(){
		public void run(UPlayer player, String[] args){

		}
	}, "EZ town teleporter", "town"),
	
	NIGHT(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			while (true) {
				if (!Time.isDay()) {
					break;
				}
				Time.add(100);
			}
		}
	}, "Changes time to night", "night"),
	
	DAY(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			while (true) {
				if (Time.isDay()) {
					break;
				}
				Time.add(100);
			}
		}
	}, "Changes time to day", "day", "d"),
	
	ID(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			Block block = player.getPlayer().getTargetBlock(((Set<Material>) null), 10);
			@SuppressWarnings("deprecation")
			int id = block.getTypeId();
			@SuppressWarnings("deprecation")
			byte data = block.getData();
			player.sendMessage(ChatColor.BOLD + "" + id + ":" + data);
		}
	}, "Tells you the id of the block you are looking at right now", "id"),
	
	SAVE(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			for (DataFile file : DataFile.values()){
				file.save();
				player.sendMessage("Saved " + file.toString());
			}
		}
	}, "Saves data files to disk", "save"),
	
	VERSION(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			File pluginJar = new File(Main.getInstance().getDataFolder().getParentFile(), "Ublisk.jar");
			long lastModified = pluginJar.lastModified();
			
			String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis());
			String lastModifiedDate = new SimpleDateFormat("dd-MM-yyyy").format(lastModified);
			
			SimpleDateFormat format;
			if (currentDate.equals(lastModifiedDate)){
				format = new SimpleDateFormat("hh:mm:ss");
			} else {
				format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			}

			String formatted = format.format(lastModified);
			player.sendMessage("Last updated: " + formatted);
		}
	}, "Displays the last date at which the Ublisk plugin was changed.", "version", "changed"),
	
	SWORDS(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			PlayerInventory inv = player.getInventory();

			for (Weapon weapon : Weapon.getWeapons()) {
				if (weapon instanceof Sword) {
					inv.addItem(((Sword) weapon).getItemStack());
				}
			}
		}
	}, "Gives all swords in Weapon enum", "sword", "swords"),
	
	REMOVE_INVISIBLE(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			final Block block = player.getLocation().getBlock();
			block.setType(Material.STONE);
			new BukkitRunnable() {

				public void run() {
					block.setType(Material.AIR);
				}
				
			}.runTaskLater(Main.getInstance(), 5);
		}
	}, "Removes invisible blocks (stand inside the invisible block)", "rinv"),
	 
	ACTION_BAR_TEST(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			player.sendActionBarMessage(ChatColor.RED + "TEST!");
		}
	}, "Sends an action bar message. For developers only.", "actionbar"),
	
	TRIGGERED(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			for (Trigger trigger : Trigger.values()){
				player.sendMessage(trigger.getTrigger() + ": " + trigger.getMessage());
			}
		}
	}, "Lists chat triggers", "triggers"),
	
	RESPAWN_NPC(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			NPC.respawnAll();
		}
	}, "Respawns all NPCs", "npc", "respawnnpc"),
	
	LIST_ENTITIES(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			for (Entity entity : Var.WORLD.getEntities()) {
				player.sendMessage(
						entity.getName() 
						+ " : " 
						+ entity.getCustomName() 
						+ " : "
						+ entity.getLocation().getBlockX() 
						+ " : " 
						+ entity.getLocation().getBlockZ()
						+ " : " 
						+ entity.getLocation().getChunk());
			}
		}
	}, "Displays information about all entities in the world.", "entities"),
	
	TPS(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			player.sendMessage("TPS: " + Lag.getTPS());
		}
	}, "Gets current tps", "lag", "tps"),
	
	REMOVE_LOOT(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			for (LootChest loot : Loot.getLootChests()){
				loot.remove();
			}
			player.sendMessage("Removed loot.");
		}
	}, "Remove all loot chests", "removeloot"),
	
	SPAWN_LOOT(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			Loot.getRandomLoot().spawn();
		}
	}, "Spawn a loot chest", "loot"),
	
	SPAWN_ALL_LOOT(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			for (LootChest loot : Loot.getLootChests()){
				loot.spawn();
			}
		}
	}, "Spawns ALL loot chests. Avoid using this.", "lootall"),
	
	REMOVE_MOBS(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			Ublisk.broadcastMessage(Message.ENTITIES_REMOVED);
			Mob.removeMobs();
		}
	}, "Removes entities, potentially improving server performance.", "kill", "removemobs", "clearlag"),
	
	FANCY_TEXT_TEST(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			final String[] fancyStrings = new String[]{
					"                       ",
					" a a aa  a   a aaa a a ",
					" a a a a a   a a   a a ",
					" a a aa  a   a aaa aa  ",
					" a a a a a   a   a aa  ",
					" aaa aa  aaa a aaa a a ",
					"                       "
			};
			for (String string : fancyStrings){
				player.sendMessage(string.replace("a", ChatColor.AQUA + "" + ChatColor.BOLD + ImageChar.DARK_SHADE.getChar()).replace(" ", ChatColor.GREEN + "" + ChatColor.BOLD + ImageChar.DARK_SHADE.getChar()));
			}
		}
	}, "Sends fancy Ublisk message", "fancymessage"),
	
	CIRCLE(new CommandRunnable(){
		public void run(UPlayer player, String[] args){			
			for (Location location : Shapes.generateCircle(Direction.HORIZONTAL, player.getLocation(), 100, 3.5)){
				Ublisk.spawnParticle(Particle.FLAME, location, 1, 0, 0, 0, 0);
				player.sendMessage(location.getX() + " : " + location.getY() + " : " + location.getZ());
			}
		}
	}, "Summons particles in a circle", "circle"),
	
	GROUP(new CommandRunnable(){
		public void run(UPlayer player, String[] args){
			
		}
	}, "Sets player group", "group", "rank");

	;
	
	private CommandRunnable executor;
	private String description;
	private String[] commands;
	
	UbliskCommand(CommandRunnable executor, String description, String... commands){
		this.executor = executor;
		this.description = description;
		this.commands = commands;
	}*/
	
	private static final UbliskCommand[] COMMANDS = {
			new TownCommand(),
	};
	
	protected abstract void onCommand(UPlayer player, String[] args);
	
	protected abstract String getDescription();
	
	protected abstract String[] getAliases();
	
	public static class Executor implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if (!(sender instanceof Player)){
				sender.sendMessage(Message.NOT_A_PLAYER.toString());
			}
			
			UPlayer player = new UPlayer(sender);
			
			if (args.length != 1){
				player.sendMessage("/u <command>");
				boolean alternatingColor = false;
				for (UbliskCommand ubliskCommand : COMMANDS){
					if (alternatingColor){
						player.sendMessage(ChatColor.BLUE + Strings.join(ubliskCommand.getAliases(), ", ") + "  |  " + ubliskCommand.getDescription());
					} else {
						player.sendMessage(ChatColor.YELLOW + Strings.join(ubliskCommand.getAliases(), ", ") + "  |  " + ubliskCommand.getDescription());
					}
					alternatingColor = !alternatingColor;
				}
				return true;
			}
			
			String commandLabel = args[0];
			
			for (UbliskCommand ubliskCommand : COMMANDS){
				for (String commandAlias : ubliskCommand.getAliases()){
					if (commandLabel.equalsIgnoreCase(commandAlias)){
						List<String> argsList = Arrays.asList(args);
						argsList.remove(0); //Remove first argument, since that is the command name
						
						ubliskCommand.onCommand(player, argsList.toArray(new String[]{}));
						return true;
					}
				}
			}
			return false;		
		}
		
	}

}