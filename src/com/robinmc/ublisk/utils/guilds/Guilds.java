package com.robinmc.ublisk.utils.guilds;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.java.MiscUtils;
import com.robinmc.ublisk.utils.scheduler.Scheduler;

public class Guilds {
	
	static final String PATH = Main.getInstance().getDataFolder() + "\\guilds";
	
	public static Map<UPlayer, Guild> INVITED_GUILD = new HashMap<>();
	
	public static Guild fromName(String name){
		return new Guild(name);
	}
	
	public static List<Guild> getGuilds(){
		File dir = new File(PATH);
		File[] directoryListing = dir.listFiles();
		
		List<Guild> guilds = new ArrayList<Guild>();
		
		for (File file : directoryListing) {
			String name = MiscUtils.getFileName(file);
			guilds.add(fromName(name));
		}
		
		return guilds;
	}
	
	public static void syncAllGuildsWithDatabase(){
		int delay = -5*20;
		for (final Guild guild : getGuilds()){
			delay = delay + 5*20;
			Scheduler.runTaskLater(delay, new Runnable(){
				public void run(){
					guild.syncInfoWithDatabase();
				}
			});
		}
	}

}
