package xyz.derkades.ublisk.weapons.sword;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagList;
import xyz.derkades.ublisk.utils.inventory.Item;
import xyz.derkades.ublisk.weapons.Weapon;
import xyz.derkades.ublisk.weapons.WeaponRarity;
import xyz.derkades.ublisk.weapons.abilities.Ability;

public abstract class Sword extends Weapon {

	private final AttackSpeed attackSpeed;

	protected Sword(final String name, final Material material, final WeaponRarity rarity, final String tagline, final AttackSpeed attackSpeed, final int damage, final double movementSpeed, final double knockbackResistance, final Ability ability) {
		super(name, material, rarity, tagline, damage, movementSpeed, knockbackResistance, null, ability);
		//Left click ability is null, because swords can't have a left click ability.

		this.attackSpeed = attackSpeed;
	}

	public AttackSpeed getAttackSpeed(){
		return this.attackSpeed;
	}

	@Override
	public ItemStack getItemStack(){
		final Item item = new Item(this.getMaterial())
				.setName(this.getColoredName())
				.setLore(this.getLore());

		final NBTTagList modifiers = new NBTTagList();

		final NBTTagCompound damage = new NBTTagCompound();
		damage.setString("AttributeName", "generic.attackDamage");
		damage.setString("Name", "generic.attackDamage");
		damage.setDouble("Amount", this.getDamage());
		damage.setInt("Operation", 1);
		damage.setInt("UUIDLeast", 652);
		damage.setInt("UUIDMost", 12098);
		modifiers.add(damage);

		if (this.getMovementSpeed() != -1){
			final NBTTagCompound speed = new NBTTagCompound();
			speed.setString("AttributeName", "generic.movementSpeed");
			speed.setString("Name", "generic.movementSpeed");
			speed.setDouble("Amount", this.getMovementSpeed());
			speed.setInt("Operation", 1);
			speed.setInt("UUIDLeast", 652);
			speed.setInt("UUIDMost", 12098);
			modifiers.add(speed);
		}

		if (this.getAttackSpeed().getValue() != -1){
			final NBTTagCompound attackSpeed = new NBTTagCompound();
			attackSpeed.setString("AttributeName", "generic.attackSpeed");
			attackSpeed.setString("Name", "generic.attackSpeed");
			attackSpeed.setDouble("Amount", this.getAttackSpeed().getValue());
			attackSpeed.setInt("Operation", 1);
			attackSpeed.setInt("UUIDLeast", 652);
			attackSpeed.setInt("UUIDMost", 12098);
			modifiers.add(attackSpeed);
		}

		if (this.getKnockbackResistance() != -1){
			final NBTTagCompound knockback = new NBTTagCompound();
			knockback.setString("AttributeName", "generic.knockbackResistance");
			knockback.setString("Name", "generic.knockbackResistance");
			knockback.setDouble("Amount", this.getKnockbackResistance());
			knockback.setInt("Operation", 1);
			knockback.setInt("UUIDLeast", 652);
			knockback.setInt("UUIDMost", 12098);
			modifiers.add(knockback);
		}

		final NBTTagCompound compound = item.getNBT();

		compound.set("AttributeModifiers", modifiers);
		compound.setInt("HideFlags", 7);
		compound.setBoolean("Unbreakable", true);

		item.setNBT(compound);

		item.setDamage(this.getDamage());

		return item.getItemStack();
	}

}
