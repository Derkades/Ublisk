package com.robinmc.ublisk.weapon;

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

import com.robinmc.ublisk.weapon.sword.Sword;

public abstract class Weapon {
	
	public abstract String getName();
	
	public abstract Material getMaterial();
	
	public abstract WeaponRarity getRarity();
	
	public abstract String getTagLine();
	
	/**
	 * <b>Damage dealt by attacks, in half-hearts. For example: 3 means 3 half-hearts so 1.5 hearts.</b>
	 * <br><br>
	 * Minimum: 0.0
	 * <br>
	 * Maximum: --
	 */
	public abstract int getDamage();
	
	/**
	 * Speed of movement. <b>For vanilla use -1</b>
	 * <br><br>
	 * Minimum: 0.0
	 * <br>
	 * Maximum: --
	 */
	public abstract double getMovementSpeed();
	
	/**
	 * The chance to resist knockback from attacks, explosions, and projectiles. 1.0 is 100% chance for resistance. <b>For vanilla use -1</b>
	 * <br><br>
	 * Minimum: 0.0
	 * <br>
	 * Maximum: 1.0
	 */
	public abstract double getKnockbackResistance();
	
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
