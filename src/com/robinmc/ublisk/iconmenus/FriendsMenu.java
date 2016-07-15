package com.robinmc.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Friends;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.UUIDUtils;

import de.tr7zw.itemnbtapi.NBTItem;

public class FriendsMenu {
	
	private static IconMenu menu = new IconMenu("Friends", 3*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			Player player = event.getPlayer();
			player.sendMessage(Messages.menuErrorWrongItem());
			//TODO: Action for clicking on a friend
		}
	}, Main.getInstance());
	
	public static void open(Player player){
		Console.sendMessage("[Menus] FriendsMenu has been opened for " + player.getName());
		fillMenu(player);
		menu.open(player);
	}
	
	private static void fillMenu(Player player){
		int i = 0;
		for (String string : Friends.get(player)){
			String pn = UUIDUtils.getNameFromIdString(string);
			
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1);
			head.setDurability((short) 3); //Durability value 3 is to get a human head instead of a skeleton head
			NBTItem nbt = new NBTItem(head);
			nbt.setString("SkullOwner", pn); //Uses the friend's name as skull owner
			head = nbt.getItem();
			
			menu.setOption(i, head, pn);
			
			i++;
			
			if (i > 18){
				break;
			}
			
		}
		
	}

}
