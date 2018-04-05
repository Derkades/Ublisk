package xyz.derkades.ublisk.weapons.abilities;

import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;

public class ChannelTest extends Ability {

	public ChannelTest() {
		super(5, 0, 10_000);
	}

	@Override
	public boolean run(UPlayer player) {
		Ublisk.runAsync(() -> {
			while (!player.isMoving()) {
				player.sendMessage("Not moving");
				Ublisk.sleep(500);
			}
			player.sendMessage("moving");
		});
		return true;
	}

}
