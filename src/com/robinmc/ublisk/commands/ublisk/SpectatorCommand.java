package com.robinmc.ublisk.commands.ublisk;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;

import com.robinmc.ublisk.utils.UPlayer;

public class SpectatorCommand extends UbliskCommand {

	private static final Map<UUID, GameMode> PREVIOUS_GAMEMODE = new HashMap<>();
	
	@Override
	protected void onCommand(UPlayer player, String[] args) {
		if (player.getGameMode() != GameMode.SPECTATOR){
			PREVIOUS_GAMEMODE.put(player.getUniqueId(), player.getGameMode());
			player.setGameMode(GameMode.SPECTATOR);
		} else {
			player.setGameMode(PREVIOUS_GAMEMODE.get(player.getUniqueId()));
			PREVIOUS_GAMEMODE.remove(player.getUniqueId());
		}
	}

	@Override
	protected String getDescription() {
		return "Toggles spectator mode";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"spectator", "3", "spec"};
	}

}
