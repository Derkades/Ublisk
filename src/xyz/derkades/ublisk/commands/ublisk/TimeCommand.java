package xyz.derkades.ublisk.commands.ublisk;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.Time;
import xyz.derkades.ublisk.utils.UPlayer;

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
