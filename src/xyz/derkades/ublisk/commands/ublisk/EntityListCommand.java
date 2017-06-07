package xyz.derkades.ublisk.commands.ublisk;

import org.bukkit.entity.Entity;

import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.utils.UPlayer;

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
