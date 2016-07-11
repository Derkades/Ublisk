package com.robinmc.ublisk.iconmenus;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Cooldown;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;

public class ClassMenu {
	
	private static IconMenu menu = new IconMenu("Class menu (you can change class later)", 1*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			Player player = event.getPlayer();
			UUID uuid = player.getUniqueId();
			if (Cooldown.chooseClass(player)){
				player.sendMessage(Messages.classCooldown());
				return;
			} else {
				Cooldown.chooseClassStart(player);
				if (name.equals("swordsman")){
					Config.set("class." + uuid, "Swordsman");
					player.sendMessage(Messages.changedClass("Swordsman"));
				} else if (name.equals("archer")){
					Config.set("class." + uuid, "Archer");
					player.sendMessage(Messages.changedClass("Archer"));
				} else if (name.equals("sorcerer")){
					Config.set("class." + uuid, "Sorcerer");
					player.sendMessage(Messages.changedClass("Sorceer"));
				} else if (name.equals("Paladin")){
					Config.set("class." + uuid, "Paladin");
					player.sendMessage(Messages.changedClass("Paladin"));
				} else {
					player.sendMessage(Messages.menuErrorWrongItem());
				}
			}
		}
	}, Main.getInstance());
	
	public static void open(Player player){
		Console.sendMessage("[Menus] ClassMenu has been opened for " + player.getName());
		fillMenu();
		menu.open(player);
	}
	
	private static void fillMenu(){
		menu.setOption(0, new ItemStack(Material.DIAMOND_SWORD), "Swordsman", "Damage: ■■■■", "Defence: ■■□□", "Magic: □□□□", "Walk speed: ■■□□", "Range: □□□□");
		menu.setOption(1, new ItemStack(Material.BOW), "Archer", "Damage: ■■□□", "Defence: ■□□□", "Magic: ■□□□", "Walk speed: ■■■■", "Range: ■■■■");
		menu.setOption(2, new ItemStack(Material.STICK), "Sorcerer", "Damage: ■□□□", "Defence: ■□□□", "Magic: ■■■■", "Walk speed: ■■□□", "Range: □□□□");
		menu.setOption(3, new ItemStack(Material.SHIELD), "Paladin", "Damage: ■■■□", "Defence: ■■■■", "Magic: □□□□", "Walk speed: □□□□", "Range: □□□□");
	}

}
