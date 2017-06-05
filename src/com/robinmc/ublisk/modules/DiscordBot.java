package com.robinmc.ublisk.modules;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.robinmc.ublisk.DataFile;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Logger.LogLevel;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class DiscordBot extends UModule {
	
	private IDiscordClient client;
	private boolean enabled = false;
	
	@Override
	public void onEnable(){
		if (!DataFile.MYSQL.getConfig().getBoolean("discord.enabled")){
			super.log(this, LogLevel.WARNING, "Discord bot is not enabled in the config!");
			return;
		}
		
		if (Var.DEBUG){
			super.log(this, LogLevel.WARNING, "Debug mode is enabled. Bot startup has been cancelled");
			return;
		}
		
		String token = DataFile.MYSQL.getConfig().getString("discord.token");
		
		try {
			client = new ClientBuilder().withToken(token).login();
		} catch (DiscordException e) {
			super.log(this, LogLevel.SEVERE, "An error occured while trying to connect with Discord. Shutting down..");
			e.printStackTrace();
			this.terminate();
		}
		
		super.log(this, LogLevel.INFO, "Discord module has been initialized successfully!");
		enabled = true;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event){
		if (!enabled){
			super.log(this, LogLevel.DEBUG, "Skipping discord bot for this message - module has not been initialized successfully.");
			return;
		}
		
		Player player = event.getPlayer();
		String message = "[" + player.getName() + "] " + event.getMessage();
		
		IChannel channel = client.getChannelByID("310155421719724052");
		
		try {
			channel.sendMessage(message);
		} catch (MissingPermissionsException e) {
			e.printStackTrace();
		} catch (RateLimitException e) {
			e.printStackTrace();
		} catch (DiscordException e) {
			e.printStackTrace();
		}
	}

}
