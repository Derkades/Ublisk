package xyz.derkades.ublisk.weapons.abilities;

import xyz.derkades.ublisk.utils.UPlayer;

public abstract class Ability {

	private int mana;
	private int minLevel;

	public Ability(int mana, int minLevel) {
		this.mana = mana;
		this.minLevel = minLevel;
	}

	public int getMana() {
		return mana;
	}

	public int getMinimumLevel() {
		return minLevel;
	}

	public abstract boolean run(UPlayer player);
	
}
