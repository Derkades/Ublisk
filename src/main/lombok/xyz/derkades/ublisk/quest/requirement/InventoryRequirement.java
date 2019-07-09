package xyz.derkades.ublisk.quest.requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.ublisk.utils.ItemBuilder;
import xyz.derkades.ublisk.utils.UPlayer;

public class InventoryRequirement extends QuestRequirement {

	private final List<ItemStack> requiredItems;

	public InventoryRequirement(final ItemStack... requiredItems) {
		this.requiredItems = new ArrayList<>();
		this.requiredItems.addAll(Arrays.asList(requiredItems));
	}

	public InventoryRequirement() {
		this.requiredItems = new ArrayList<>();
	}

	public void add(final ItemStack item) {
		this.requiredItems.add(item);
	}

	public void add(final Material material, final int amount) {
		this.requiredItems.add(new ItemBuilder(material).amount(amount).create());
	}

	@Override
	public Boolean apply(final UPlayer player) {
		for (final ItemStack requiredItem : this.requiredItems) {
			if (!player.bukkit().getInventory().contains(requiredItem, requiredItem.getAmount())) {
				return false;
			}
		}
		return true;
	}

}
