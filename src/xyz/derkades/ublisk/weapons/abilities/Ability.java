package xyz.derkades.ublisk.weapons.abilities;

import xyz.derkades.ublisk.utils.UPlayer;

public abstract class Ability {

	private int mana;
	private int minLevel;
	private long cooldown;

	public Ability(int mana, int minLevel, long cooldown) {
		this.mana = mana;
		this.minLevel = minLevel;
	}

	public int getMana() {
		return mana;
	}

	public int getMinimumLevel() {
		return minLevel;
	}
	
	public long getCooldown() {
		return cooldown;
	}
	
	public String getName() {
		return this.getClass().getSimpleName();
	}

	public abstract boolean run(UPlayer player);
	
}
