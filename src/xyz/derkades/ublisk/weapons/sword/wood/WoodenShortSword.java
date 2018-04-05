package xyz.derkades.ublisk.weapons.sword.wood;

import org.bukkit.Material;

import xyz.derkades.ublisk.weapons.WeaponRarity;
import xyz.derkades.ublisk.weapons.abilities.ChannelTest;
import xyz.derkades.ublisk.weapons.sword.AttackSpeed;
import xyz.derkades.ublisk.weapons.sword.Sword;

public class WoodenShortSword extends Sword {

	public WoodenShortSword() {
		super("Wooden Short Sword", Material.GLASS, WeaponRarity.COMMON, "Insert description here", AttackSpeed.FAST, 3, -1, -1, new ChannelTest());
	}

}
