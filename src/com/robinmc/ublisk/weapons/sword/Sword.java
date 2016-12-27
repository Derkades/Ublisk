package com.robinmc.ublisk.weapons.sword;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.utils.inventory.InvUtils;
import com.robinmc.ublisk.utils.inventory.ItemBuilder;
import com.robinmc.ublisk.weapons.Weapon;
import com.robinmc.ublisk.weapons.WeaponRarity;
import com.robinmc.ublisk.weapons.abilities.Ability;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagList;

public abstract class Sword extends Weapon {
	
	private AttackSpeed attackSpeed;
	
	protected Sword(String name, Material material, WeaponRarity rarity, String tagline, AttackSpeed attackSpeed, int damage, double movementSpeed, double knockbackResistance, Ability ability) {
		super(name, material, rarity, tagline, damage, movementSpeed, knockbackResistance, null, ability); 
		//Left click ability is null, because swords can't have a left click ability.
		
		this.attackSpeed = attackSpeed;
	}
	
	public AttackSpeed getAttackSpeed(){
		return attackSpeed;
	}
	
	public ItemStack getItemStack(){
		ItemStack item = new ItemBuilder(this.getMaterial())
				.setName(this.getColoredName())
				.setLore(this.getLore())
				.getItemStack();
		
		NBTTagList modifiers = new NBTTagList();

		NBTTagCompound damage = new NBTTagCompound();
		damage.setString("AttributeName", "generic.attackDamage");
		damage.setString("Name", "generic.attackDamage");
		damage.setDouble("Amount", this.getDamage());
		damage.setInt("Operation", 1);
		damage.setInt("UUIDLeast", 652);
		damage.setInt("UUIDMost", 12098);
		modifiers.add(damage);
		
		if (this.getMovementSpeed() != -1){
			NBTTagCompound speed = new NBTTagCompound();
			speed.setString("AttributeName", "generic.movementSpeed");
			speed.setString("Name", "generic.movementSpeed");
			speed.setDouble("Amount", this.getMovementSpeed());
			speed.setInt("Operation", 1);
			speed.setInt("UUIDLeast", 652);
			speed.setInt("UUIDMost", 12098);
			modifiers.add(speed);
		}
		
		if (this.getAttackSpeed().getValue() != -1){
			NBTTagCompound attackSpeed = new NBTTagCompound();
			attackSpeed.setString("AttributeName", "generic.attackSpeed");
			attackSpeed.setString("Name", "generic.attackSpeed");
			attackSpeed.setDouble("Amount", this.getAttackSpeed().getValue());
			attackSpeed.setInt("Operation", 1);
			attackSpeed.setInt("UUIDLeast", 652);
			attackSpeed.setInt("UUIDMost", 12098);
			modifiers.add(attackSpeed);
		}
		
		if (this.getKnockbackResistance() != -1){
			NBTTagCompound knockback = new NBTTagCompound();
			knockback.setString("AttributeName", "generic.knockbackResistance");
			knockback.setString("Name", "generic.knockbackResistance");
			knockback.setDouble("Amount", this.getKnockbackResistance());
			knockback.setInt("Operation", 1);
			knockback.setInt("UUIDLeast", 652);
			knockback.setInt("UUIDMost", 12098);
			modifiers.add(knockback);
		}
		
		NBTTagCompound compound = InvUtils.getCompound(item);
		
		compound.set("AttributeModifiers", modifiers);
		compound.setInt("HideFlags", 7);
		compound.setBoolean("Unbreakable", true);
		
		ItemStack nbtItem = InvUtils.applyCompound(item, compound);
		
		return nbtItem;
	}

}
