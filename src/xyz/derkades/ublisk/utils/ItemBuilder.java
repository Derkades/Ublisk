package xyz.derkades.ublisk.utils;

import xyz.derkades.ublisk.utils.inventory.Item;

public class ItemBuilder extends xyz.derkades.derkutils.bukkit.ItemBuilder {
	
	public ItemBuilder(Item item) {
		super(item.getItemStack());
	}

}
