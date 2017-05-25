package com.robinmc.ublisk.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.PacketListener.PacketRecievedListener;
import com.robinmc.ublisk.utils.java.FileUtils;

import net.md_5.bungee.api.ChatColor;

public class Logger {

	public static void log(LogLevel logLevel, String name, Object message) {
		//Send console message 
		if (logLevel != LogLevel.CHAT){
			String consoleMessage = "[" + name + "] " + message;
			Main.getInstance().getLogger().log(logLevel.getLevel(), consoleMessage);
		}
		
		//Send message to online players if debug mode is enabled
		if (Var.DEBUG && logLevel != LogLevel.DEBUG && logLevel != LogLevel.CHAT){
			String chatMessage = "[" + logLevel + "] [" + name + "] " + message;
			if (logLevel == LogLevel.WARNING || logLevel == LogLevel.SEVERE){
				chatMessage = ChatColor.RED + chatMessage;
			} else {
				chatMessage = ChatColor.GRAY + chatMessage;
			}
			
			for (UPlayer player : Ublisk.getOnlinePlayers()){
				player.sendMessage(chatMessage);
			}
		}
		
		//Append to log file
		String timeStamp = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date());
		String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		String fileMessage = "[" + timeStamp + "] [" + logLevel + "] [" + name + "] " + message + "\n";
		
		if (logLevel == LogLevel.SEVERE || logLevel == LogLevel.WARNING) {
			File file = new File(Main.getInstance().getDataFolder() + "/logs/warning/", fileName + ".txt");
			FileUtils.appendStringToFile(file, fileMessage);
		}
		
		if (logLevel != LogLevel.DEBUG){ //Log everything except for debug messages in info log file.
			File file = new File(Main.getInstance().getDataFolder() + "/logs/info/", fileName + ".txt");
			FileUtils.appendStringToFile(file, fileMessage);
		}
	}

	public static void log(LogLevel logLevel, Object object) {
		log(logLevel, "Ublisk", object);
	}
	
	public static void startSiteLogger(){
		PacketListener.listenForPacket(6789, 256, new PacketRecievedListener(){

			@Override
			public void onPacketRecieved(String message) {
				Logger.log(LogLevel.INFO, "Site", message);
			}
			
		});
	}

	public static enum LogLevel {

		/**
		 * To be used for messages that will/should be removed later.
		 */
		DEBUG(Level.FINE),

		/**
		 * To be used for messages providing not very important information.
		 */
		INFO(Level.INFO),
		
		/**
		 * To be used for logging chat messages.
		 */
		CHAT(Level.INFO),

		/**
		 * To be used for messages providing more important information, such as
		 * unexpected player movement.
		 */
		WARNING(Level.WARNING),

		/**
		 * To be used to alert the owner of very important information, such as
		 * a critical error in the code or an unexpected reading from a file.
		 */
		SEVERE(Level.SEVERE);
		
		private Level level;
		
		LogLevel(Level level){
			this.level = level;
		}
		
		public Level getLevel(){
			return level;
		}

	}

}
