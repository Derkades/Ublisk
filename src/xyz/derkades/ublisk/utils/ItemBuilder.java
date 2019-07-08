package xyz.derkades.ublisk.utils;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.ublisk.utils.inventory.Item;

public class ItemBuilder extends xyz.derkades.derkutils.bukkit.ItemBuilder {

	public ItemBuilder(final Item item) {
		super(item.getItemStack());
	}

	public ItemBuilder(final Material material) {
		super(material);
	}

	public ItemBuilder(final ItemStack item) {
		super(item);
	}

	public ItemBuilder(final OfflinePlayer skullOwner) {
		super(skullOwner);
	}

	public ItemBuilder(final UPlayer player) {
		super(player.bukkit());
	}

}
