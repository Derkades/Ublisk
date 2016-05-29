package com.robinmc.ublisk;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Console;

public enum Weapon {
	
	
	//Format: "minecraft:stone 1 0 {AttributeModifiers:[{Attrib...IDLeast:138836}]}"
	//So, remove the /give @p
	OldWoodenSword("askidchnsa"),
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
