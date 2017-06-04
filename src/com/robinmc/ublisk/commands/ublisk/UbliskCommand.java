package com.robinmc.ublisk.commands.ublisk;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.permission.PermissionGroup;
import com.robinmc.ublisk.utils.UPlayer;

import net.md_5.bungee.api.ChatColor;

public abstract class UbliskCommand {
	
	/*
	Spawn loot:	
	Loot.getRandomLoot().spawn();
	}, "Spawn a loot chest", "loot"),
	
	SPAWN_ALL_LOOT
			for (LootChest loot : Loot.getLootChests()){
				loot.spawn();
			}
	}, "Spawns ALL loot chests. Avoid using this.", "lootall"),
	
	CIRCLE
			for (Location location : Shapes.generateCircle(Direction.HORIZONTAL, player.getLocation(), 100, 3.5)){
				Ublisk.spawnParticle(Particle.FLAME, location, 1, 0, 0, 0, 0);
				player.sendMessage(location.getX() + " : " + location.getY() + " : " + location.getZ());
			}
	}, "Summons particles in a circle", "circle")*/
	
	private static final UbliskCommand[] COMMANDS = {
			new BlockIdCommand(),
			new CustomItemCommand(),
			new DebugCommand(),
			new EntityListCommand(),
			new FixGrassCommand(),
			new FreezeCommand(),
			new GitHubCommand(),
			new InvalidEntityCommand(),
			new RemoveInvisibleCommand(),
			new RemoveLootCommand(),
			new RemoveMobsCommand(),
			new RespawnNPCCommand(),
			new SpawnLootCommand(),
			new SwordCommand(),
			new TimeCommand(),
			new TownCommand(),
			new TPSCommand(),
			new TriggeredCommand(),
			new VersionCommand(),
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
			
			if (player.getGroup() != PermissionGroup.BUILDER && player.getGroup() != PermissionGroup.MODERATOR && player.getGroup() != PermissionGroup.OWNER){
				player.sendMessage("u no has permisions");
				return true;
			}
			
			if (args.length == 0){
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
						//Remove first argument, since that is the command name
						int n = args.length - 1;
						String[] newArgs = new String[n];
						System.arraycopy(args, 1, newArgs, 0, n);
						
						ubliskCommand.onCommand(player, newArgs);
						return true;
					}
				}
			}
			return false;		
		}
		
	}

}
