package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.chat.Trigger;
import com.robinmc.ublisk.utils.UPlayer;

public class TriggeredCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		for (Trigger trigger : Trigger.values()){
			player.sendMessage(trigger.getTrigger() + ": " + trigger.getMessage());
		}
	}

	@Override
	protected String getDescription() {
		return "Lists triggers";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"triggers", "triggered"};
	}

}
