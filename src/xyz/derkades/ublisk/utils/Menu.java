package xyz.derkades.ublisk.utils;

import org.bukkit.entity.Player;

import xyz.derkades.ublisk.Main;

public abstract class Menu extends xyz.derkades.derkutils.bukkit.IconMenu {

	public Menu(String name, int size) {
		super(Main.getInstance(), name, size);
	}
	
	public Menu(String name, int size, Player player) {
		super(Main.getInstance(), name, size, player);
	}

}
