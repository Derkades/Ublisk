package com.robinmc.ublisk.weapons.abilities;

import com.robinmc.ublisk.utils.UPlayer;

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

	public abstract void run(UPlayer player);
	
}
