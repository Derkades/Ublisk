package com.robinmc.ublisk.commands.ublisk;

import org.bukkit.entity.Entity;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.UPlayer;

public class EntityListCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		for (Entity entity : Var.WORLD.getEntities()) {
			player.sendMessage(
					entity.getName() 
					+ " : " 
					+ entity.getCustomName() 
					+ " : "
					+ entity.getLocation().getBlockX() 
					+ " : " 
					+ entity.getLocation().getBlockZ()
					+ " : " 
					+ entity.getLocation().getChunk());
		}
	}

	@Override
	protected String getDescription() {
		return "Lists all entities with information";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"entity", "entities", "entityinfo", "entitiesinfo", "entitylist", "entitieslist"};
	}

}
