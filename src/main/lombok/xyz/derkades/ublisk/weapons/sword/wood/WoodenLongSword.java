package xyz.derkades.ublisk.weapons.sword.wood;

import org.bukkit.Material;

import xyz.derkades.ublisk.weapons.WeaponRarity;
import xyz.derkades.ublisk.weapons.abilities.Meteorite;
import xyz.derkades.ublisk.weapons.sword.AttackSpeed;
import xyz.derkades.ublisk.weapons.sword.Sword;

public class WoodenLongSword extends Sword {

	public WoodenLongSword() {
		super("Wooden Long Sword", Material.GLASS, WeaponRarity.COMMON, "Slower than average but quite powerful", AttackSpeed.SLOW, 5,
				-1, -1, new Meteorite(50));
	}

}
