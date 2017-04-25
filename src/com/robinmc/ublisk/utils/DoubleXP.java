package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.PacketListener.PacketRecievedListener;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;

public class DoubleXP {

	private static float DOUBLE_XP_PERCENTAGE = 0.0f;
	private static int DOUBLE_XP_SECONDS_LEFT = 0;
	private static final int DOUBLE_XP_TOTAL_SECONDS = 150;

	public static boolean isActive() {
		return DoubleXP.DOUBLE_XP_PERCENTAGE != 0.0f;
	}

	public static void startDoubleXP(final UPlayer player) {
		if (DoubleXP.isActive()) {
			Bukkit.broadcastMessage(Message.DOUBLE_XP_ALREADY_ACTIVE.toString());
			new URunnable(){
				public void run(){
					startDoubleXP(player);
				}
			}.runLater(5*60*20);
			return;
		}

		//Bukkit.broadcastMessage("Double XP started thanks to " + player.getName());
		Ublisk.broadcastPrefixedMessage("Double XP started thanks to " + player.getName());
		
		DoubleXP.DOUBLE_XP_PERCENTAGE = 1.0f;
		DoubleXP.DOUBLE_XP_SECONDS_LEFT = DoubleXP.DOUBLE_XP_TOTAL_SECONDS;

		new BukkitRunnable() {

			public void run() {
				//Every second: remove 1 second from `DOUBLE_XP_SECONDS_LEFT` and recalculate percentage.
				DoubleXP.DOUBLE_XP_SECONDS_LEFT--;
				float percent = ((float) DoubleXP.DOUBLE_XP_SECONDS_LEFT) / ((float) DoubleXP.DOUBLE_XP_TOTAL_SECONDS);
				DoubleXP.DOUBLE_XP_PERCENTAGE = percent;
				Logger.log(LogLevel.DEBUG, "Seconds left: " + DoubleXP.DOUBLE_XP_SECONDS_LEFT + " | Total seconds: "
						+ DoubleXP.DOUBLE_XP_TOTAL_SECONDS + " | Percentage float: " + DoubleXP.DOUBLE_XP_PERCENTAGE);
				if (DoubleXP.DOUBLE_XP_SECONDS_LEFT == 0) {
					this.cancel();
					DoubleXP.DOUBLE_XP_PERCENTAGE = 0.0f;
					//Bukkit.broadcastMessage("Double XP ended");
					Ublisk.broadcastPrefixedMessage("Double XP has ended.");
				}
			}
		}.runTaskTimer(Main.getInstance(), 0L, 1 * 20L);
	}

	public static String getDoubleXPSidebarString() {
		return Ublisk.getProgressString(DOUBLE_XP_PERCENTAGE);
	}

	public static void startDoubleXPPacketListener() {
		PacketListener.listenForPacket(45678, 16, new PacketRecievedListener() {

			@Override
			public void onPacketRecieved(String message) {
				Logger.log(LogLevel.DEBUG, message);
				try {
					DoubleXP.startDoubleXP(new UPlayer(message));
				} catch (PlayerNotFoundException e) {
					//Bukkit.broadcastMessage("The player who started DoubleXP is not online.");
					Ublisk.broadcastPrefixedMessage("The player who started Double XP is not online.");
				}
			}

		});
	}

}
