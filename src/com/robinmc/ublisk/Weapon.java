package com.robinmc.ublisk;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Console;

public enum Weapon {
	
	
	//Format: "minecraft:stone 1 0 {AttributeModifiers:[{Attrib...IDLeast:138836}]}"
	//So, remove the /give @p
	//Put a \ before every "
	OLD_WOODEN_SWORD("minecraft:wooden_sword 1 0 {HideFlags:2,display:{Name:\"Old Wooden Sword\",Lore:[\"Attack Damage: 1\"]},AttributeModifiers:[{AttributeName:\"generic.attackDamage\",Name:\"generic.attackDamage\",Amount:1,Operation:0,UUIDMost:64262,UUIDLeast:178260}]}"),
	OLD_STONE_SWORD("minecraft:stone_sword 1 0 {HideFlags:2,display:{Name:\"Old Stone Sword\",Lore:[\"Attack Damage: 2\"]},AttributeModifiers:[{AttributeName:\"generic.attackDamage\",Name:\"generic.attackDamage\",Amount:2,Operation:0,UUIDMost:64262,UUIDLeast:178260}]}"),
	OLD_IRON_SWORD("minecraft:iron_sword 1 0 {HideFlags:2,display:{Name:\"Old Iron Sword\",Lore:[\"Attack Damage: 3\"]},AttributeModifiers:[{AttributeName:\"generic.attackDamage\",Name:\"generic.attackDamage\",Amount:3,Operation:0,UUIDMost:64262,UUIDLeast:178260}]}"),
	WOODEN_SWORD("minecraft:wooden_sword 1 0 {Unbreakable:1,display:{Name:\"Basic Sword\",Lore:[\"This weapons deals 10 extra magic damage\"]},AttributeModifiers:[{AttributeName:\"generic.movementSpeed\",Name:\"generic.movementSpeed\",Amount:10,Operation:1,UUIDMost:45995,UUIDLeast:100431}]}");
	
	private String cmd;
	
	Weapon(String cmd){
		this.cmd = cmd;
	}
	
	public String getCmd(){
		return cmd;
	}
	
	public void setCmd(String cmd){
		this.cmd = cmd;
	}
	
	public static void giveWeapon(Player player, Weapon weapon){
		Console.sendCommand("give " + player.getName() + " " + weapon.getCmd());
	}

}
