package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.caching.Cache;
import com.robinmc.ublisk.utils.caching.CacheObject;

public class DebugCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		//Feel free to delete everything below this to test your own stuff
		
		for (CacheObject cache : Cache.CACHE_OBJECT_LIST){
			player.sendMessage(cache.identifier + " : " + cache.toString() + " : " +  cache.timeout);
		}
		
		player.sendSpacers(3);
		player.sendMessage(Cache.CACHE_OBJECT_LIST.size());
	}

	@Override
	protected String getDescription() {
		return "Command for debugging purposes";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"debug"};
	}

}
