package com.robinmc.ublisk;

import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.RED;

import java.util.ArrayList;
import java.util.List;

import com.coloredcarrot.api.sidebar.Sidebar;
import com.coloredcarrot.api.sidebar.SidebarString;
import com.robinmc.ublisk.utils.UPlayer;

public class Scoreboard {
	
	public static Sidebar getScoreboard(UPlayer player){		
		
		String redBold = RED + "" + BOLD;
		
		String[] strings = new String[]{
				DARK_GRAY + "---------------",
				redBold + "Class",
				GRAY + player.getClazz().getName(),
				"",
				redBold + "XP",
				GRAY + "" + player.getXP()};
		
		List<SidebarString> sidebarStrings = new ArrayList<SidebarString>();
		for (String string : strings) sidebarStrings.add(new SidebarString(string));
		
		String title = DARK_AQUA + "" + BOLD + "Information";
		
		int aLot = 9999*9999;
		
		return new Sidebar(title, Main.getInstance(), aLot, sidebarStrings.toArray(new SidebarString[]{}));
		
	}

}
