package xyz.derkades.ublisk.commands.ublisk;

import java.io.IOException;

import org.kohsuke.github.GHIssue;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.modules.GitHubModule;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.URunnable;
import xyz.derkades.ublisk.utils.Ublisk;

public class GitHubCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		if (args.length == 0){
			player.sendMessage("Wrong usage: /u " + this.getAliases()[0] + " [description]");
			return;
		}
		
		final String description = String.join(" ", args);
		
		new URunnable(){
			public void run(){
				try {
					GHIssue issue = GitHubModule.createIssue(player.getName(), description);
					player.sendMessage(Ublisk.getPrefix() + "Your message has been recorded. We'll take a look at it soon! View your issue at " + issue.getHtmlUrl());
				} catch (IOException e) {
					player.sendMessage(ChatColor.RED + "An error has occured :(");
					e.printStackTrace();
				}
			}
		}.runAsync();

	}

	@Override
	protected String getDescription() {
		return "Creates github issue";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"github", "todo"};
	}

}
