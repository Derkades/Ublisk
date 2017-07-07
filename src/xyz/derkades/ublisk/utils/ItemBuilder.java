package xyz.derkades.ublisk.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.ublisk.utils.inventory.Item;

public class ItemBuilder extends xyz.derkades.derkutils.bukkit.ItemBuilder {
	
	public ItemBuilder(Item item) {
		super(item.getItemStack());
	}
	
	public ItemBuilder(Material material) {
		super(material);
	}
	
	public ItemBuilder(ItemStack item) {
		super(item);
	}
	
	public ItemBuilder(String skullOwner) {
		super(skullOwner);
	}

}
