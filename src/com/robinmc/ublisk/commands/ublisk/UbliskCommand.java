package com.robinmc.ublisk.commands.ublisk;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;

import net.md_5.bungee.api.ChatColor;

public abstract class UbliskCommand {
	
	/*	
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
	
	CIRCLE(new CommandRunnable(){
		public void run(UPlayer player, String[] args){			
			for (Location location : Shapes.generateCircle(Direction.HORIZONTAL, player.getLocation(), 100, 3.5)){
				Ublisk.spawnParticle(Particle.FLAME, location, 1, 0, 0, 0, 0);
				player.sendMessage(location.getX() + " : " + location.getY() + " : " + location.getZ());
			}
		}
	}, "Summons particles in a circle", "circle")

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
