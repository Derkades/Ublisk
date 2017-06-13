package xyz.derkades.ublisk.utils;

import xyz.derkades.ublisk.Main;

public abstract class Menu extends xyz.derkades.derkutils.bukkit.IconMenu {

	public Menu(String name, int size, UPlayer player) {
		super(Main.getInstance(), name, size, player.getPlayer());
	}

}
