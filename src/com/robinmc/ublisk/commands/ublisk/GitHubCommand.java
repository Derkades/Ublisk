package com.robinmc.ublisk.commands.ublisk;

import java.io.IOException;

import org.kohsuke.github.GHIssue;

import com.robinmc.ublisk.modules.GitHubModule;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

import net.md_5.bungee.api.ChatColor;

public class GitHubCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		if (args.length == 0){
			player.sendMessage("Wrong usage: /u " + this.getAliases()[0] + " [description]");
		}
		String description = String.join(" ", args);
		
		try {
			GHIssue issue = GitHubModule.createIssue(description);
			player.sendMessage(Ublisk.getPrefix() + "Your message has been recorded. We'll take a look at it soon! View your issue at " + issue.getHtmlUrl());
		} catch (IOException e) {
			player.sendMessage(ChatColor.RED + "An error has occured :(");
			e.printStackTrace();
		}
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
