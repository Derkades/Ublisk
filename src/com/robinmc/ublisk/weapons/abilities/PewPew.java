package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.Particle;
import org.bukkit.Sound;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.Ublisk.Explosion;

public class PewPew extends Ability {

	public PewPew() {
		super(1, 0);
	}

	@Override
	public void run(final UPlayer player) {	
		Ublisk.createFakeExplosion(player.getTargetBlock(35).getLocation(), 20, 6, Explosion.BLAST_LARGE);
	}

}
