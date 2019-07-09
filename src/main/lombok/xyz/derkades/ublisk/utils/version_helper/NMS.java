package xyz.derkades.ublisk.utils.version_helper;

import org.bukkit.block.Chest;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@Deprecated
public interface NMS {

	public void setTarget(Creature creature, LivingEntity target);

	public void setTargetToClosestPlayer(Creature creature, Player player);

	public void sendActionBarMessage(Player player, String message);

	public void sendTitle(Player player, String title, String subtitle);

	public void setChestName(Chest chest, String name);

}
