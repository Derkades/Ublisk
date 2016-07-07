package com.robinmc.ublisk.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.Classes;
import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.Weapon;
import com.robinmc.ublisk.utils.NPCUtils;

public class Ulric {
	
	public static void ulric(Player player){
		NPCUtils npc = new NPCUtils();
		PlayerInventory inv = player.getInventory();
		
		if (	inv.containsAtLeast(new ItemStack(Material.LOG), 10) &&
				inv.containsAtLeast(new ItemStack(Material.STRING), 16) &&
				inv.containsAtLeast(new ItemStack(Material.GOLD_NUGGET), 10)){
			//player has required materials
			Classes c = Classes.getClass(player);
			if (c == Classes.ARCHER){
				player.sendMessage("Bows are not implemented yet. Please choose another class");
			} else if (c == Classes.SWORDSMAN){
				//Weapon.giveWeapon(player, Weapon.OLD_WOODEN_SWORD);
			} else {
				player.sendMessage(Messages.generalError());
			}
		} else {
			npc.msg(player, "Ulric", "I can make a weapon for you if you bring me the required materials.");
		}
	}

}
