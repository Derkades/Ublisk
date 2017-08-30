package xyz.derkades.ublisk.utils.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import xyz.derkades.ublisk.utils.Ublisk;

public class UInventory {
	
	private PlayerInventory inv;
	
	public UInventory(PlayerInventory inv){
		this.inv = inv;
	}
	
	/**
	 * @return Item in player's main hand. This can be either left or right hand depending on user's settings.
	 */
	public Item getItemInMainHand(){
		return new Item(inv.getItemInMainHand());
	}
	
	/**
	 * @return Item in player's main hand. This can be either left or right hand depending on user's settings.
	 */
	public Item getItemInOffHand(){
		return new Item(inv.getItemInOffHand());
	}
	
	/**
	 * @return Item in main hand or the item in offhand if the item in main hand is empty.
	 */
	public Item getItemInHand(){
		if (inv.getItemInMainHand().getType() == Material.AIR){
			return new Item(inv.getItemInOffHand());
		} else {
			return new Item(inv.getItemInMainHand());
		}
	}
	
	/**
	 * Checks if the inventory contains at least one of an ItemStack matching the given item.
	 */
	public boolean contains(Item item) {
		return this.contains(item, 1);
	}
	
	/**
	 * Checks if the inventory contains at least <i>amount</i> of <i>item</i>
	 */
	public boolean contains(Item item, int amount){
		return inv.containsAtLeast(item.getItemStack(), amount);
	}
	
	public boolean containsExact(Item item, int amount){
		return inv.contains(item.getItemStack(), amount);
	}
	
	/**
	 * Checks if the inventory contains any items with the given material.
	 */
	public boolean contains(Material material){
		return inv.contains(material);
	}
	
	/**
	 * Checks if the inventory contains at least <i>amount</i> items with the given material.
	 */
	public boolean contains(Material material, int amount){
		return inv.contains(material, amount);
	}
	
	/**
	 * Checks if the inventory contains items with all given materials.
	 */
	public boolean containsMaterials(Material... materials){
		for (Material material : materials){
			if (!inv.contains(material)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param items List of items the inventory should contain
	 * @return True if the player's inventory contains at least {@link Item#getAmount()} of all items.
	 */
	public boolean containsItems(Item... items){
		for (Item item : items){
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
	public UInventory addItem(Item item){
		inv.addItem(item.getItemStack());
		return this;
	}
	
	/**
	 * Adds a new ItemStack with the specified material and amount to the player inventory.
	 * @see #addItem(Item)
	 */
	public UInventory addItem(Material material, int amount){
		return this.addItem(new Item(material, amount));
	}
	
	public UInventory removeItem(Item item){
		inv.remove(item.getItemStack());
		return this;
	}
	
	public UInventory removeAllItemsWithType(Material material){
		inv.remove(material);
		return this;
	}
	
	public UInventory set(int slot, Item item){
		inv.setItem(slot, item.getItemStack());
		return this;
	}

	public UInventory remove(Material material, int amount){
		inv.remove(new ItemStack(material, amount));
		return this;
	}
	
	public UInventory removeAll(Material material){
		inv.remove(material);
		return this;
	}
	
	public UInventory remove(Item item){
		inv.remove(item.getItemStack());
		return this;
	}
	
	public UInventory setHelmet(Item helmet){
		inv.setHelmet(helmet.getItemStack());
		return this;
	}
	
	public UInventory setHelmet(Material material){
		inv.setHelmet(new ItemStack(material));
		return this;
	}
	
	public List<Item> getItems(boolean includeStorage, boolean includeArmor, boolean includeExtra){
		List<Item> items = new ArrayList<>();
		
		if (includeStorage){
			for (ItemStack item : inv.getStorageContents()){
				items.add(new Item(item));
			}
		}
		
		if (includeArmor){
			for (ItemStack item : inv.getArmorContents()){
				items.add(new Item(item));
			}
		}
		
		if (includeExtra){
			for (ItemStack item : inv.getExtraContents()){
				items.add(new Item(item));
			}
		}
		
		return items;
	}
	
	public void clear(){
		for (Item item : this.getItems(true, true, true)){
			this.removeAll(item.getType());
		}
	}
	
	public void dropItems(Location location){
		for (Item item : this.getItems(true, true, true)){
			Ublisk.dropItem(location, item);
		}
	}

}
