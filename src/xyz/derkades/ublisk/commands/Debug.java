package xyz.derkades.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import xyz.derkades.ublisk.DataFile;
import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.permission.Permission;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.exception.PlayerNotFoundException;
import xyz.derkades.ublisk.utils.inventory.Item;

public class Debug implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			UPlayer player = new UPlayer(sender);
			if (player.hasPermission(Permission.COMMAND_DEBUG)) {
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("xp")) {
						int xp = Integer.parseInt(args[1]);
						player.setXP(xp);
						return true;
					} else if (args[0].equals("refreshxp")) {
						UPlayer target;
						try {
							target = new UPlayer(args[1]);
						} catch (PlayerNotFoundException e) {
							player.sendMessage("player not found");
							return true;
						}

						player.updateXPBar();
						player.sendMessage("XP refreshed!");
						player.sendMessage("XP: " + target.getXP());
						player.sendMessage("Level: " + target.getLevel());
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
						player.getInventory().addItem(new Item(args[1]));
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
				} else if (args.length == 1){
					if (args[0].equals("save")){
						for (DataFile file : DataFile.values()){
							file.save();
						}
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
