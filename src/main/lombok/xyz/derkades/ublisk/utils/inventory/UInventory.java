package xyz.derkades.ublisk.utils.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import xyz.derkades.ublisk.utils.Ublisk;

@Deprecated
public class UInventory {

	private final PlayerInventory inv;

	public UInventory(final PlayerInventory inv){
		this.inv = inv;
	}

	/**
	 * @return Item in player's main hand. This can be either left or right hand depending on user's settings.
	 */
	public Item getItemInMainHand(){
		return new Item(this.inv.getItemInMainHand());
	}

	/**
	 * @return Item in player's main hand. This can be either left or right hand depending on user's settings.
	 */
	public Item getItemInOffHand(){
		return new Item(this.inv.getItemInOffHand());
	}

	/**
	 * @return Item in main hand or the item in offhand if the item in main hand is empty.
	 */
	public Item getItemInHand(){
		if (this.inv.getItemInMainHand().getType() == Material.AIR){
			return new Item(this.inv.getItemInOffHand());
		} else {
			return new Item(this.inv.getItemInMainHand());
		}
	}

	/**
	 * Checks if the inventory contains at least one of an ItemStack matching the given item.
	 */
	public boolean contains(final Item item) {
		return this.contains(item, 1);
	}

	/**
	 * Checks if the inventory contains at least <i>amount</i> of <i>item</i>
	 */
	public boolean contains(final Item item, final int amount){
		return this.inv.containsAtLeast(item.getItemStack(), amount);
	}

	public boolean containsExact(final Item item, final int amount){
		return this.inv.contains(item.getItemStack(), amount);
	}

	/**
	 * Checks if the inventory contains any items with the given material.
	 */
	public boolean contains(final Material material){
		return this.inv.contains(material);
	}

	/**
	 * Checks if the inventory contains at least <i>amount</i> items with the given material.
	 */
	public boolean contains(final Material material, final int amount){
		return this.inv.contains(material, amount);
	}

	/**
	 * Checks if the inventory contains items with all given materials.
	 */
	public boolean containsMaterials(final Material... materials){
		for (final Material material : materials){
			if (!this.inv.contains(material)){
				return false;
			}
		}
		return true;
	}

	/**
	 * @param items List of items the inventory should contain
	 * @return True if the player's inventory contains at least {@link Item#getAmount()} of all items.
	 */
	public boolean containsItems(final Item... items){
		for (final Item item : items){
			if (!this.contains(item.getType(), item.getAmount())){
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds item to the first available slot in the player's:
	 * <ol>
	 *   <li>Hotbar</li>
	 *   <li>Inventory</li>
	 *   <li>Offhand</li>
	 *   <li>Armor slots</li>
	 * </ol>
	 * @param item
	 */
	public UInventory addItem(final Item item){
		this.inv.addItem(item.getItemStack());
		return this;
	}

	/**
	 * Adds a new ItemStack with the specified material and amount to the player inventory.
	 * @see #addItem(Item)
	 */
	public UInventory addItem(final Material material, final int amount){
		return this.addItem(new Item(material, amount));
	}

	public UInventory removeItem(final Item item){
		this.inv.remove(item.getItemStack());
		return this;
	}

	public UInventory removeAllItemsWithType(final Material material){
		this.inv.remove(material);
		return this;
	}

	public UInventory set(final int slot, final Item item){
		this.inv.setItem(slot, item.getItemStack());
		return this;
	}

	public UInventory remove(final Material material, final int amount){
		this.inv.remove(new ItemStack(material, amount));
		return this;
	}

	public UInventory removeAll(final Material material){
		this.inv.remove(material);
		return this;
	}

	public UInventory remove(final Item item){
		this.inv.remove(item.getItemStack());
		return this;
	}

	public UInventory setHelmet(final Item helmet){
		this.inv.setHelmet(helmet.getItemStack());
		return this;
	}

	public UInventory setHelmet(final Material material){
		this.inv.setHelmet(new ItemStack(material));
		return this;
	}

	public List<Item> getItems(final boolean includeStorage, final boolean includeArmor, final boolean includeExtra){
		final List<Item> items = new ArrayList<>();

		if (includeStorage){
			for (final ItemStack item : this.inv.getStorageContents()){
				items.add(new Item(item));
			}
		}

		if (includeArmor){
			for (final ItemStack item : this.inv.getArmorContents()){
				items.add(new Item(item));
			}
		}

		if (includeExtra){
			for (final ItemStack item : this.inv.getExtraContents()){
				items.add(new Item(item));
			}
		}

		return items;
	}

	public void clear(){
		for (final Item item : this.getItems(true, true, true)){
			this.removeAll(item.getType());
		}
	}

	public void dropItems(final Location location){
		for (final Item item : this.getItems(true, true, true)){
			Ublisk.dropItem(location, item);
		}
	}

}
