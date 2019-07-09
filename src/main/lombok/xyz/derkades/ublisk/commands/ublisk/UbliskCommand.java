package xyz.derkades.ublisk.commands.ublisk;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.UPlayer;

public abstract class UbliskCommand {

	private static final UbliskCommand[] COMMANDS = {
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
			new SpectatorCommand(),
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
		public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
			if (!(sender instanceof Player)){
				sender.sendMessage(Message.NOT_A_PLAYER.toString());
			}

			final UPlayer player = new UPlayer(sender);

			if (args.length == 0){
				player.sendMessage("/u <command>");
				boolean alternatingColor = false;
				for (final UbliskCommand ubliskCommand : COMMANDS){
					if (alternatingColor){
						player.sendMessage(ChatColor.BLUE + String.join(", ", ubliskCommand.getAliases()) + "  |  " + ubliskCommand.getDescription());
					} else {
						player.sendMessage(ChatColor.YELLOW + String.join(", ", ubliskCommand.getAliases()) + "  |  " + ubliskCommand.getDescription());
					}
					alternatingColor = !alternatingColor;
				}
				return true;
			}

			final String commandLabel = args[0];

			for (final UbliskCommand ubliskCommand : COMMANDS){
				for (final String commandAlias : ubliskCommand.getAliases()){
					if (commandLabel.equalsIgnoreCase(commandAlias)){
						//Remove first argument, since that is the command name
						final int n = args.length - 1;
						final String[] newArgs = new String[n];
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
