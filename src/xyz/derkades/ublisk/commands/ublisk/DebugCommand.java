package xyz.derkades.ublisk.commands.ublisk;

import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.caching.Cache;

public class DebugCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		//Feel free to delete everything below this to test your own stuff
		
		player.sendMessage(Cache.size());
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
