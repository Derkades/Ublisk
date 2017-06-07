package xyz.derkades.ublisk.weapons.abilities;

import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.Ublisk.Explosion;

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
