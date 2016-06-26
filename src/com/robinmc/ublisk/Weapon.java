package com.robinmc.ublisk;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Console;

public enum Weapon {
	
	
	//Format: "minecraft:stone 1 0 {AttributeModifiers:[{Attrib...IDLeast:138836}]}"
	//So, remove the /give @p
	//Put a \ before every "
	OLD_WOODEN_SWORD(Classes.SWORDSMAN, "minecraft:wooden_sword 1 0 {HideFlags:2,display:{Name:\"Old Wooden Sword\",Lore:[\"Attack Damage: 1\"]},AttributeModifiers:[{AttributeName:\"generic.attackDamage\",Name:\"generic.attackDamage\",Amount:1,Operation:0,UUIDMost:64262,UUIDLeast:178260}]}"),
	OLD_STONE_SWORD(Classes.SWORDSMAN, "minecraft:stone_sword 1 0 {HideFlags:2,display:{Name:\"Old Stone Sword\",Lore:[\"Attack Damage: 2\"]},AttributeModifiers:[{AttributeName:\"generic.attackDamage\",Name:\"generic.attackDamage\",Amount:2,Operation:0,UUIDMost:64262,UUIDLeast:178260}]}"),
	OLD_IRON_SWORD(Classes.SWORDSMAN, "minecraft:iron_sword 1 0 {HideFlags:2,display:{Name:\"Old Iron Sword\",Lore:[\"Attack Damage: 3\"]},AttributeModifiers:[{AttributeName:\"generic.attackDamage\",Name:\"generic.attackDamage\",Amount:3,Operation:0,UUIDMost:64262,UUIDLeast:178260}]}");
	
	private Classes c;
	private String cmd;
	
	Weapon(Classes c, String cmd){
		this.c = c;
		this.cmd = cmd;
	}
	
	public Classes getClazz(){
		return c;
	}
	
	public String getCmd(){
		return cmd;
	}
	
	public static void giveWeapon(Player player, Weapon weapon){
		Console.sendCommand("give " + player.getName() + " " + weapon.getCmd());
	}

}
