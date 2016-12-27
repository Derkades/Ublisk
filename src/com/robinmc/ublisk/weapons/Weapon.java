package com.robinmc.ublisk.weapons;

import static net.md_5.bungee.api.ChatColor.BOLD;
import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.DARK_GREEN;
import static net.md_5.bungee.api.ChatColor.DARK_PURPLE;
import static net.md_5.bungee.api.ChatColor.GOLD;
import static net.md_5.bungee.api.ChatColor.GRAY;
import static net.md_5.bungee.api.ChatColor.GREEN;
import static net.md_5.bungee.api.ChatColor.WHITE;
import static net.md_5.bungee.api.ChatColor.YELLOW;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.robinmc.ublisk.weapons.abilities.Ability;
import com.robinmc.ublisk.weapons.sword.Sword;

public abstract class Weapon {
	
	private String name;
	private Material material;
	private WeaponRarity rarity;
	private String tagline;
	
	private int damage;
	private double movementSpeed;
	private double knockbackResistance;
	
	private Ability leftClickAbility;
	private Ability rightClickAbility;
	
	/**
	 * Represents a weapon of any type.
	 * @param name Weapon display name, for example "Diamond Long Sword"
	 * @param material
	 * @param rarity
	 * @param tagline
	 * @param damage Damage dealt by attacks, in half-hearts. For example: 3 means 3 half-hearts so 1.5 hearts. Must be a value higher than 0.
	 * @param movementSpeed Speed of movement. Value higher than 0. For vanilla movement speed use -1.
	 * @param knockbackResistance The chance to resist knockback from attacks, explosions, and projectiles. 1.0 is 100% chance for resistance. For vanilla use -1
	 */
	protected Weapon(String name, Material material, WeaponRarity rarity, String tagline, int damage, double movementSpeed, double knockbackResistance, Ability leftClickAbility, Ability rightClickAbility){
		if (name == null){
			throw new IllegalArgumentException("Weapon name must not be null"); 
		}
		this.name = name;
		
		if (material == null){
			throw new IllegalArgumentException("Material must not be null");
		}
		this.material = material;
		
		if (rarity == null){
			throw new IllegalArgumentException("Weapon rarity must not be null");
		}
		this.rarity = rarity;
		
		if (tagline == null){
			throw new IllegalArgumentException("Tagline must not be null. If you don't want a tagline use \"\"");
		}
		this.tagline = tagline;
		
		if (damage < 0){
			throw new IllegalArgumentException("Damage must not be less than 0");
		}
		this.damage = damage;
		
		if (movementSpeed < 0 && movementSpeed != -1){
			throw new IllegalArgumentException("Movement speed must be either -1 or a value higher than 0.");
		}
		this.movementSpeed = movementSpeed;
		
		if ((knockbackResistance < 0 || knockbackResistance > 1) && knockbackResistance != -1){
			throw new IllegalArgumentException("Knockback resistance must be either -1 or a value between 0 and 1.");
		}
		this.knockbackResistance = knockbackResistance;
	}
	
	public String getName(){
		return name;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public WeaponRarity getRarity(){
		return rarity;
	}
	
	public String getTagLine(){
		return tagline;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public double getMovementSpeed(){
		return movementSpeed;
	}
	
	public double getKnockbackResistance(){
		return knockbackResistance;
	}
	
	public Ability getLeftClickAbility(){
		return leftClickAbility;
	}
	
	public Ability getRightClickAbility(){
		return rightClickAbility;
	}
	
	public String getColoredName(){
		String color = WHITE + "";
		
		if (getRarity() == WeaponRarity.COMMON){
			color = GRAY + "";
		} else if (getRarity() == WeaponRarity.UNIQUE){
			color = GREEN + "";
		} else if (getRarity() == WeaponRarity.RARE){
			color = DARK_GREEN + "";
		} else if (getRarity() == WeaponRarity.EPIC){
			color = DARK_PURPLE + "" + BOLD;
		} else if (getRarity() == WeaponRarity.LEGENDARY){
			color = GOLD + "" + BOLD;
		}
		
		return color + this.getName();
	}
	
	public String[] getLore(){
		List<String> lore = new ArrayList<String>();
		lore.add(DARK_AQUA + getTagLine());
		lore.add("");
		
		lore.add(YELLOW + "Damage: " + GOLD + this.getDamage());
		
		if (this instanceof Sword){
			Sword sword = (Sword) this;
			lore.add(YELLOW + "Attack speed: " + GOLD + sword.getAttackSpeed().getName());
		}
		
		if (this.getMovementSpeed() != -1) //If movement speed is not the default value
			lore.add(YELLOW + "Movement speed: " + GOLD + this.getMovementSpeed());
		if (this.getKnockbackResistance() != -1)
			lore.add(YELLOW + "Knockback resistance: " + GOLD + this.getKnockbackResistance());
		
		return lore.toArray(new String[]{});
	}

}
