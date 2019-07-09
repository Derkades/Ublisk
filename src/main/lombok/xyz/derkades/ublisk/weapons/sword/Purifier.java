package xyz.derkades.ublisk.weapons.sword;

import org.bukkit.Material;

import xyz.derkades.ublisk.weapons.WeaponRarity;
import xyz.derkades.ublisk.weapons.abilities.SmokeShield;

public class Purifier extends Sword {

	public Purifier() {
		super("Purifier", Material.NETHER_QUARTZ_ORE, WeaponRarity.EPIC, "pretty cool sword", AttackSpeed.FAST, 10, -1, -1, new SmokeShield());
	}

}
