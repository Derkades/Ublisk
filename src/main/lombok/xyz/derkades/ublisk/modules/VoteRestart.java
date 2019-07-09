package xyz.derkades.ublisk.modules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;

public class VoteRestart extends UModule {
	
	@Override
	public void onEnable(){
		//plugin.getCommand("voterestart").setExecutor(new VoteRestartCommand());
		super.registerCommand("voterestart", new VoteRestartCommand());
	}

	private static List<String> restartVoters = new ArrayList<String>();
	private static boolean restarting = false;

	private static void restartIfPossible(){
		int online = Bukkit.getOnlinePlayers().size();
		int voters = restartVoters.size();
		if (voters > (online / 2) && !restarting){ //If the number of voters is more than half of the player count, and if not already restarting
			restarting = true;
			new BukkitRunnable(){
				public void run(){
					Ublisk.broadcastPrefixedMessage("The server will restart in 1 minute.");
				}
			}.runTaskLater(Main.getInstance(), 5*20);
			new BukkitRunnable(){
				public void run(){
					Ublisk.broadcastPrefixedMessage("The server will restart in 30 seconds.");
				}
			}.runTaskLater(Main.getInstance(), 5*20 + 30*20);
			new BukkitRunnable(){
				public void run(){
					Ublisk.broadcastPrefixedMessage("The server will restart in 10 seconds.");
				}
			}.runTaskLater(Main.getInstance(), 5*20 + 30*20 + 20*20);
			new BukkitRunnable(){
				public void run(){
					Ublisk.broadcastPrefixedMessage("The server will restart in 5 seconds.");
				}
			}.runTaskLater(Main.getInstance(), 5*20 + 30*20 + 20*20 + 5*20);
			new BukkitRunnable(){
				public void run(){
					Bukkit.getServer().shutdown();
				}
			}.runTaskLater(Main.getInstance(), 5*20 + 30*20 + 20*20 + 5*20 + 5*20);
		}
	}
	
	public static void voteForRestart(UPlayer player){
		restartVoters.add(player.getName());
		Ublisk.broadcastPrefixedMessage(player.getName() + " voted for a restart. Vote for a restart using /voterestart. Total votes: " + restartVoters.size());
		restartIfPossible();
	}
	
	public static boolean hasVotedForRestart(UPlayer player){
		return restartVoters.contains(player.getName());
	}
	
	public static class VoteRestartCommand implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if (!(sender instanceof Player)){
				sender.sendMessage(Message.NOT_A_PLAYER.toString());
			}
			
			UPlayer player = new UPlayer(sender);
			
			if (player.hasVotedForRestart()){
				player.sendMessage(Message.ALREADY_VOTED_RESTART);
				return true;
			}
			
			player.voteRestart();
			return true;
		}

	}

}
