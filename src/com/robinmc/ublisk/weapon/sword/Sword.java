package com.robinmc.ublisk.weapon.sword;

import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.utils.inventory.InvUtils;
import com.robinmc.ublisk.utils.inventory.ItemBuilder;
import com.robinmc.ublisk.weapon.Weapon;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagList;

public abstract class Sword extends Weapon {
	
	/**
	 *  Determines speed at which attack strength recharges.
	 * <br><br>
	 * AttackSpeed.VANILLA - "Slow"
	 * <br>
	 * AttackSpeed.FASTER - "Medium"
	 * <br>
	 * AttackSpeed.FASTEST - "Fast"
	 */
	public abstract AttackSpeed getAttackSpeed();
	
	public ItemStack getItemStack(){
		ItemStack item = new ItemBuilder(this.getMaterial())
				.setName(this.getName())
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
