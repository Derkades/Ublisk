package xyz.derkades.ublisk.weapons.sword.wood;

import org.bukkit.Material;

import xyz.derkades.ublisk.weapons.WeaponRarity;
import xyz.derkades.ublisk.weapons.abilities.SmokeShield;
import xyz.derkades.ublisk.weapons.sword.AttackSpeed;
import xyz.derkades.ublisk.weapons.sword.Sword;

public class BasicWoodenSword extends Sword {

	public BasicWoodenSword() {
		super("Basic Wooden Sword", Material.ACTIVATOR_RAIL, WeaponRarity.COMMON, "The sword you created yourself.", AttackSpeed.NORMAL, 1, -1, -1, new SmokeShield());
	}

}
