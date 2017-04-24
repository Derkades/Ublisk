package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.Time;
import com.robinmc.ublisk.utils.UPlayer;

public class TimeCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		if (args[0].equalsIgnoreCase("day")){
			while (true) {
				if (Time.isDay()) {
					break;
				}
				Time.add(100);
			}
		} else if (args[0].equalsIgnoreCase("night")){
			while (true) {
				if (!Time.isDay()) {
					break;
				}
				Time.add(100);
			}
		} else {
			player.sendMessage(Message.WRONG_USAGE);
		}
	}

	@Override
	protected String getDescription() {
		return "Change time to day or night";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"time"};
	}

}
