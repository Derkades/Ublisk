package xyz.derkades.ublisk.modules;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.PacketListener;
import xyz.derkades.ublisk.utils.PacketListener.PacketRecievedListener;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.URunnable;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.exception.PlayerNotFoundException;

public class DoubleXP extends UModule {
	
	@Override
	public void onEnable() {
		startDoubleXPPacketListener();
	}

	private static float DOUBLE_XP_PROGRESS = 0.0f;
	private static double DOUBLE_XP_SECONDS_LEFT = 0;
	private static final int DOUBLE_XP_TOTAL_SECONDS = 150;

	public static boolean isActive() {
		return DoubleXP.DOUBLE_XP_PROGRESS != 0.0f;
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

		Ublisk.broadcastPrefixedMessage("Double XP started thanks to " + player.getName());
		
		DoubleXP.DOUBLE_XP_PROGRESS = 1.0f;
		DoubleXP.DOUBLE_XP_SECONDS_LEFT = DoubleXP.DOUBLE_XP_TOTAL_SECONDS;
		
		final BossBar bar = Ublisk.createBossBar("Double XP - " + player.getName(), BarColor.GREEN, BarStyle.SOLID);
		Ublisk.showBossBar(bar, DoubleXP.DOUBLE_XP_TOTAL_SECONDS * 20, Ublisk.getOnlinePlayers());

		new BukkitRunnable() {

			public void run() {
				//Every second: remove 0.2 seconds from `DOUBLE_XP_SECONDS_LEFT` and recalculate percentage (0.2 seconds because this is ran 5 times every second).
				DoubleXP.DOUBLE_XP_SECONDS_LEFT--;
				float percent = ((float) DoubleXP.DOUBLE_XP_SECONDS_LEFT) / ((float) DoubleXP.DOUBLE_XP_TOTAL_SECONDS);
				DoubleXP.DOUBLE_XP_PROGRESS = percent;
				Logger.log(LogLevel.DEBUG, "Seconds left: " + DoubleXP.DOUBLE_XP_SECONDS_LEFT + " | Total seconds: "
						+ DoubleXP.DOUBLE_XP_TOTAL_SECONDS + " | Percentage float: " + DoubleXP.DOUBLE_XP_PROGRESS);
				if (DoubleXP.DOUBLE_XP_SECONDS_LEFT == 0) {
					this.cancel();
					DoubleXP.DOUBLE_XP_PROGRESS = 0.0f;
					Ublisk.broadcastPrefixedMessage("Double XP has ended.");
				}
				
				bar.setProgress(DOUBLE_XP_PROGRESS); //Update bossbar
			}
		}.runTaskTimer(Main.getInstance(), 0L, 4);
	}

	/*public static String getDoubleXPSidebarString() {
		return Ublisk.getProgressString(DOUBLE_XP_PERCENTAGE);
	}*/

	private void startDoubleXPPacketListener() {
		PacketListener.listenForPacket(45678, 16, new PacketRecievedListener() {

			@Override
			public void onPacketRecieved(String message) {
				Logger.log(LogLevel.DEBUG, message);
				try {
					DoubleXP.startDoubleXP(new UPlayer(message));
				} catch (PlayerNotFoundException e) {
					Ublisk.broadcastPrefixedMessage("The player who started Double XP is not online.");
				}
			}

		});
	}

}
