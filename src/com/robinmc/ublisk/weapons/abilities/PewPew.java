package com.robinmc.ublisk.weapons.abilities;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.Ublisk.Explosion;

@Deprecated
public class PewPew extends Ability {

	public PewPew() {
		super(1, 0);
	}

	@Override
	public boolean run(final UPlayer player) {
		Ublisk.createFakeExplosion(player.getTargetBlock(35).getLocation(), player, 20, 6, true, Explosion.BLAST_LARGE);
		return true;
	}

}
