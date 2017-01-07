package com.robinmc.ublisk.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.PacketListener.PacketRecievedListener;
import com.robinmc.ublisk.utils.java.FileUtils;

import net.md_5.bungee.api.ChatColor;

public class Logger {

	public static void log(LogLevel logLevel, String name, Object object) {
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		String timeStamp = df.format(new Date());
		
		String fileNameTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		String consoleMessage = "[" + name + "] " + object;
		String fileMessage = "[" + timeStamp + "] [" + logLevel + "] " + consoleMessage + "\n";
		String chatMessage = ChatColor.GRAY + "[" + logLevel + "] " + consoleMessage;

		if (logLevel == LogLevel.SEVERE || logLevel == LogLevel.WARNING) {
			Bukkit.getLogger().log(Level.WARNING, consoleMessage);
			File file = new File(Main.getInstance().getDataFolder() + "\\logs\\warning\\", fileNameTime + ".txt");
			FileUtils.appendStringToFile(file, fileMessage);
		} else Bukkit.getLogger().log(Level.INFO, consoleMessage);

		File file = new File(Main.getInstance().getDataFolder() + "\\logs\\info\\", fileNameTime + ".txt");
		FileUtils.appendStringToFile(file, fileMessage);

		//Send message to online players
		for (UPlayer player : Ublisk.getOnlinePlayers())
			if (logLevel != LogLevel.DEBUG)
				player.sendMessage(chatMessage);
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
		DEBUG("Debug"),

		/**
		 * To be used for messages providing not very important information.
		 */
		INFO("Info"),

		/**
		 * To be used for messages providing more important information, such as
		 * unexpected player movement.
		 */
		WARNING("Warning"),

		/**
		 * To be used to alert the owner of very important information, such as
		 * a critical error in the code or an unexpected reading from a file.
		 */
		SEVERE("Severe");

		private String string;

		LogLevel(String string) {
			this.string = string;
		}

		public String getString() {
			return string;
		}

	}

}
