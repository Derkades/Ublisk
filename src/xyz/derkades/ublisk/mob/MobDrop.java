package xyz.derkades.ublisk.mob;

import org.bukkit.Location;

import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.utils.inventory.Item;
import xyz.derkades.ublisk.utils.java.Random;

public class MobDrop {
	
	private Item item;
	private int min = 9001;
	private int max = 9001;
	private int percentage;
	
	/**
	 * Specifies a mob drop with random change of dropping
	 * @param item Item to be dropped
	 * @param percentage Random chance (1-100)
	 */
	public MobDrop(Item item, int percentage){
		this.item = item;
		this.percentage = percentage;
	}
	
	/**
	 * Specifies a mob drop with random change of dropping
	 * @param item Item to be dropped
	 * @param min Minimum for random item amount
	 * @param max Maximum for random item amount
	 */
	public MobDrop(Item item, int min, int max){
		this.min = min;
		this.max = max;
		this.item = item;
		this.percentage = -1;
	}
	
	public void drop(Location loc){
		if (min != 9001 && max != 9001){ //If min and max are set
			item.setAmount(Random.getRandomInteger(min, max));
		}
		
		
		if (canDrop()){
			org.bukkit.entity.Item entity = Var.WORLD.dropItemNaturally(loc, item.getItemStack());
			entity.setPickupDelay(10);
		}
	}
	
	private boolean canDrop(){
		if (percentage == -1){ //Percentage is set to -1 if no random percentage is specified, so we can just return true
			return true;
		}
		
		double d = percentage / 100.0; // 100% - 1.0 | 50% - 0.5
		return Random.getRandomDouble() <= d;
	}
	
	public Item getItem(){
		return item;
	}

}