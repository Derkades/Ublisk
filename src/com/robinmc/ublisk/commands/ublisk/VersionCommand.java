package com.robinmc.ublisk.commands.ublisk;

import java.io.File;
import java.text.SimpleDateFormat;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;

public class VersionCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		File pluginJar = new File(Main.getInstance().getDataFolder().getParentFile(), "Ublisk.jar");
		long lastModified = pluginJar.lastModified();
		
		String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis());
		String lastModifiedDate = new SimpleDateFormat("dd-MM-yyyy").format(lastModified);
		
		SimpleDateFormat format;
		if (currentDate.equals(lastModifiedDate)){
			format = new SimpleDateFormat("hh:mm:ss");
		} else {
			format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		}

		String formatted = format.format(lastModified);
		player.sendMessage("Last updated: " + formatted);
	}

	@Override
	protected String getDescription() {
		return "Displays the last date at which the Ublisk plugin was changed.";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"version", "changed"};
	}

}
