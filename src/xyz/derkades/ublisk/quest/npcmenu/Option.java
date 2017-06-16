package xyz.derkades.ublisk.quest.npcmenu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Deprecated
public class Option {

	private int position;
	private Material material;
	private int amount;
	private String name;
	private String[] lore;
	
	public Option(int position, Material material, String name, String... lore){
		this.position = position;
		this.material = material;
		this.amount = 1;
		this.name = name;
		this.lore = lore;
	}
	
	Option(int position, Material material, int amount, String name, String... lore){
		this.position = position;
		this.material = material;
		this.amount = amount;
		this.name = name;
		this.lore = lore;
	}
	
	int getPosition(){
		return position;
	}
	
	ItemStack getItemStack(){
		return new ItemStack(material, amount);
	}
	
	String getName(){
		return name;
	}
	
	String[] getLore(){
		return lore;
	}

}
