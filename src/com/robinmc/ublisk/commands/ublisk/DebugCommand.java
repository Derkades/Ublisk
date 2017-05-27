package com.robinmc.ublisk.commands.ublisk;

import org.bukkit.attribute.Attribute;

import com.robinmc.ublisk.utils.UPlayer;

public class DebugCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		//Feel free to delete everything below this to test your own stuff
		
		player.sendMessage("Health: " + player.getHealth());
		player.sendMessage("Health scale: " + player.getPlayer().getHealthScale());
		player.sendMessage("Max health attribute: " + player.getAttribute(Attribute.GENERIC_MAX_HEALTH));
		player.sendMessage("Level: " + player.getLevel());
		player.sendMessage("Correct max health: " + player.getMaxHealth());
		player.sendMessage("Displayed health (= health / health attribute * health scale): " + player.getHealth() / (player.getAttribute(Attribute.GENERIC_MAX_HEALTH) * player.getPlayer().getHealthScale()));
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
