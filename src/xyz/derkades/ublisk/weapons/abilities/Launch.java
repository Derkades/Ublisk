package xyz.derkades.ublisk.weapons.abilities;

import org.bukkit.Particle;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;

public class Launch extends Ability {

	public Launch() {
		super(1, 1, 7*20);
	}

	@Override
	public boolean run(UPlayer player) {
		if (!player.isOnGround()){
			player.sendMessage(ChatColor.RED + "You must be on a solid block to use this ability.");
			return false;
		}

		Ublisk.spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 5, 0, 0, 0, 0.2);
		
		player.setVelocity(player.getLocation().getDirection().multiply(3));
		player.setVelocity(new Vector(player.getVelocity().getX(), 1.5D, player.getVelocity().getZ()));
		player.givePotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3*20, 4);
		
		return true;
	}

}
