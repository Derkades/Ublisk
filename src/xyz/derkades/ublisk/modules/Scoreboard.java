package xyz.derkades.ublisk.modules;

import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.RED;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.ext.com.coloredcarrot.api.sidebar.Sidebar;
import xyz.derkades.ublisk.ext.com.coloredcarrot.api.sidebar.SidebarString;
import xyz.derkades.ublisk.utils.DoubleXP;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.URunnable;
import xyz.derkades.ublisk.utils.Ublisk;

public class Scoreboard extends UModule {
	
	@Override
	public void onEnable(){
		new UpdateScoreboard().runTimer(5*20, 2*20);
	}
	
	public static Sidebar getScoreboard(UPlayer player) {

		String r = ChatColor.RESET.toString();

		String redBold = RED + "" + BOLD;

		List<String> strings = new ArrayList<String>();

		strings.add(DARK_GRAY + "---------------");
		strings.add(redBold + "XP");
		strings.add(GRAY + "" + player.getXP() + " / " + CustomXP.getRequiredXP(player.getLevel() + 1));
		strings.add(r + " ");
		strings.add(redBold + "Health");
		strings.add(GRAY + "" + player.getHealth() + " / " + player.getMaxHealth());

		if (DoubleXP.isActive()) {
			strings.add(r + r + "");
			strings.add(redBold + "Double XP");
			strings.add(DoubleXP.getDoubleXPSidebarString());
		}

		if (player.getFriends().size() > 1) {
			boolean displayedOnlineFriends = false;
			for (OfflinePlayer friend : player.getFriends()) {
				if (friend != null && friend.isOnline()) {
					if (!displayedOnlineFriends){
						strings.add(redBold + "Online Friends");
						strings.add(r + r + r + "");
						displayedOnlineFriends = true;
					}
					UPlayer online = new UPlayer(friend);
					strings.add(ChatColor.DARK_AQUA + friend.getName() + DARK_GRAY + ": " + ChatColor.AQUA
							+ Math.round(online.getHealth()) + "HP");
				}
			}
		}
		
		strings.add(r + DARK_GRAY + "---------------");

		List<SidebarString> sidebarStrings = new ArrayList<SidebarString>();
		for (String string : strings)
			sidebarStrings.add(new SidebarString(string));

		String title = DARK_AQUA + "" + BOLD + "Information";
		
		return new Sidebar(title, Main.getInstance(), Integer.MAX_VALUE, sidebarStrings.toArray(new SidebarString[] {}));
	}
	
	private static class UpdateScoreboard extends URunnable {
		
		@Override
		public void run() {
			for (UPlayer player : Ublisk.getOnlinePlayers()) {
				Scoreboard.getScoreboard(player).showTo(player.getPlayer());
			}
		}
		
	}

}
