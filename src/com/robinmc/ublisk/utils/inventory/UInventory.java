package com.robinmc.ublisk.utils.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
	 * @return 
	 * <ul>
	 * 	<li>Item in off hand if no item in main hand</li>
	 * 	<li>Item in main hand otherwise</li>
	 * </ul>
	 */
	public Item getItemInHand(){
		if (inv.getItemInMainHand().getType() == Material.AIR){
			return new Item(inv.getItemInOffHand());
		} else {
			return new Item(inv.getItemInMainHand());
		}
	}
	
	public boolean contains(Item item){
		return inv.contains(item.getItemStack());
	}
	
	public boolean contains(Item item, int amount){
		return inv.containsAtLeast(item.getItemStack(), amount);
	}
	
	public boolean containsExact(Item item, int amount){
		return inv.contains(item.getItemStack(), amount);
	}
	
	public boolean contains(Material material){
		return inv.contains(material);
	}
	
	public boolean contains(Material material, int amount){
		return inv.contains(material, amount);
	}
	
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
	 * @return True if the player's inventory contains at least x of the item. Amount is specified by setting item.setAmount() prior to calling this method.
	 */
	public boolean containsItems(Item... items){
		for (Item item : items){
			if (!inv.contains(item.getType(), item.getAmount())){
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
	
	public UInventory removeItem(Item item){
		inv.remove(item.getItemStack());
		return this;
	}
	
	public UInventory removeAllItemsWithType(Material material){
		inv.remove(material);
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

}
