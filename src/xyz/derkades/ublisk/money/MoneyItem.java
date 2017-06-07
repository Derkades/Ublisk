package xyz.derkades.ublisk.money;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.utils.inventory.Item;

public enum MoneyItem {
	
	/**
	 * Gold dust ($1)
	 */
	DUST("Gold Dust", Material.GLOWSTONE_DUST, 1),
	
	/**
	 * Gold nugget ($10)
	 */
	NUGGET("Gold Nugget", Material.GOLD_NUGGET, 10),
	
	/**
	 * Gold coin ($100)
	 */
	COIN("Gold Coin", Material.CHORUS_FRUIT_POPPED, 100),
	
	/**
	 * Gold chunk ($500)
	 */
	CHUNK("Gold Chunk", Material.INK_SACK, 500),
	
	/**
	 * Gold bar ($1000)
	 */
	/* FOO */ BAR("Gold Bar", Material.GOLD_INGOT, 1000),
	
	/**
	 * Gold block ($1000)
	 */
	BLOCK("Gold Block", Material.GOLD_BLOCK, 10000);
	
	private String name;
	private Material material;
	private int value;
	
	MoneyItem(String name, Material material, int value){
		this.name = name;
		this.value = value;
		this.material = material;
	}
	
	public String getName(){
		return name;
	}
	
	public int getValue(){
		return value;
	}
	
	public Item getItem(){
		return getItem(1);
	}
	
	public Item getItem(int amount){
		int damage = 0;
		if (this == MoneyItem.CHUNK){
			damage = 11; //Yellow dye
		}
		
		return new Item(material)
				.setName(ChatColor.GOLD + name)
				.setLore(ChatColor.YELLOW + "Value: $" + value)
				.setDamage(damage)
				.setAmount(amount);
	}
	
	public static MoneyItem fromItem(Item item){
		for (MoneyItem money : MoneyItem.values()){
			if (money.getItem().getType() == item.getType()){
				return money;
			}
		}
		return null;
	}

}
