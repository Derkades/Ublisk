package com.robinmc.ublisk;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Console;

public enum Weapon {
	
	
	//Format: "minecraft:stone 1 0 {AttributeModifiers:[{Attrib...IDLeast:138836}]}"
	//So, remove the /give @p
	//Put a \ before every "
	OldWoodenSword("minecraft:diamond_sword 1 0 {AttributeModifiers:[{AttributeName:\"generic.movementSpeed\",Name:\"generic.movementSpeed\",Amount:1,Operation:0,UUIDMost:79614,UUIDLeast:150804}]}"),
	OldStoneSword("soaidcmsa"),
	OldIronSword("asdicnascd");
	
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
