package xyz.derkades.ublisk.money;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.utils.inventory.CustomItem;
import xyz.derkades.ublisk.utils.inventory.Item;

public class MoneyItem extends CustomItem {
	
	private Type type;
	
	public MoneyItem(Type type) {
		super(type.toString());
		
		this.setName(ChatColor.GOLD + type.getName());
		this.setLore(ChatColor.YELLOW + "Value: $" + type.getValue());
	}
	
	public Type getMoneyType() {
		return type;
	}
	
	public int getValue() {
		return type.getValue();
	}
	
	public static MoneyItem fromItem(Item item){
		CustomItem customItem = CustomItem.fromItem(item);
		Type type = Type.valueOf(customItem.getIdentifier());
		return new MoneyItem(type);
	}
	
	public static boolean isMoneyItem(Item item) {
		try {
			CustomItem customItem = CustomItem.fromItem(item);
			
			for (Type type : Type.values()) {
				if (type.toString().equals(customItem.getIdentifier())) {
					return true;
				}
			}
			
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public enum Type {
		
		/**
		 * Gold dust ($1)
		 */
		DUST("Gold Dust", 1),
		
		/**
		 * Gold nugget ($10)
		 */
		NUGGET("Gold Nugget", 10),
		
		/**
		 * Gold coin ($100)
		 */
		COIN("Gold Coin", 100),
		
		/**
		 * Gold chunk ($500)
		 */
		CHUNK("Gold Chunk", 500),
		
		/**
		 * Gold bar ($1000)
		 */
		/* FOO */ BAR("Gold Bar", 1000),
		
		/**
		 * Gold block ($1000)
		 */
		BLOCK("Gold Block", 10000);
		
		private String name;
		private int value;
		
		Type(String name, int value){
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}
		
		public int getValue() {
			return value;
		}
	}

}
