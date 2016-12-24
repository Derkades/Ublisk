package com.robinmc.ublisk;

import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.RED;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.coloredcarrot.api.sidebar.Sidebar;
import com.coloredcarrot.api.sidebar.SidebarString;
import com.robinmc.ublisk.utils.DoubleXP;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

import net.md_5.bungee.api.ChatColor;

public class Scoreboard extends BukkitRunnable {
	
	@Override
	public void run() {
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			Scoreboard.getScoreboard(player).showTo(player.getPlayer());
		}
	}
	
	public static Sidebar getScoreboard(UPlayer player){		
		
		String r = ChatColor.RESET.toString();
		
		String redBold = RED + "" + BOLD;
		
		List<String> strings = new ArrayList<String>();
		
		strings.add(DARK_GRAY + "---------------");
		strings.add(redBold + "Class");
		strings.add(GRAY + player.getClazz().getName());
		strings.add(" ");
		strings.add(redBold + "XP");
		strings.add(GRAY + "" + player.getXP());
		
		if (DoubleXP.isActive()){
			strings.add(r + DARK_GRAY + "---------------");
			strings.add(redBold + "Double XP");
			strings.add(DoubleXP.getDoubleXPSidebarString());
		}
		
		if (player.getFriends().size() > 1){
			strings.add(r + r + DARK_GRAY + "---------------");
			strings.add(redBold + "Online Friends");
			for (OfflinePlayer friend : player.getFriends()){				
				if (friend != null && friend.isOnline()){
					UPlayer online = UPlayer.get((Player) friend);
					strings.add(ChatColor.DARK_AQUA + friend.getName() + DARK_GRAY + ": " + ChatColor.AQUA + Math.round(online.getHealth()) + "HP");
				}
			}
		}
		
		List<SidebarString> sidebarStrings = new ArrayList<SidebarString>();
		for (String string : strings) sidebarStrings.add(new SidebarString(string));
		
		String title = DARK_AQUA + "" + BOLD + "Information";
		
		int aLot = 9999*9999;
		
		return new Sidebar(title, Main.getInstance(), aLot, sidebarStrings.toArray(new SidebarString[]{}));
		
	}

}
