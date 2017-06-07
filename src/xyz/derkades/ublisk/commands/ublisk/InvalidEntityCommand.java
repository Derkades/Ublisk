package xyz.derkades.ublisk.commands.ublisk;

import org.bukkit.entity.LivingEntity;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.mob.Mobs;
import xyz.derkades.ublisk.utils.UPlayer;

public class InvalidEntityCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Entity : X : Y : Z : Name");
		for (LivingEntity entity : Var.WORLD.getLivingEntities()){
			if (!Mobs.SPAWNED_MOBS.containsKey(entity.getUniqueId())){
				String color = "";
				if (Mobs.NON_MOB_ENTITIES.contains(entity.getType()))
					color = ChatColor.GREEN.toString();
				else 
					color = ChatColor.RED.toString();
				
				int x = (int) entity.getLocation().getX();
				int y = (int) entity.getLocation().getY();
				int z = (int) entity.getLocation().getZ();
				player.sendMessage(color + entity.getType() + " : " + x + " : " + y + " : " + z + " : " + entity.getName());
			}
		}
	}

	@Override
	protected String getDescription() {
		return "Lists invalid enties";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"invalidentities", "unknownentities", "invalidmobs"};
	}

}
