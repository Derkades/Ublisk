package com.robinmc.ublisk.iconmenus;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Cooldown;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.third_party.IconMenu;
import com.robinmc.ublisk.utils.third_party.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.variable.CMessage;
import com.robinmc.ublisk.utils.variable.Message;

public class ClassMenu {
	
	private static IconMenu menu = new IconMenu("Class menu", 1*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			Player player = event.getPlayer();
			UUID uuid = player.getUniqueId();
			if (Cooldown.chooseClass(player)){
				player.sendMessage(Message.CLASS_COOLDOWN.get());
				return;
			} else {
				Cooldown.chooseClassStart(player);
				if (name.equals("swordsman")){
					Config.set("class." + uuid, "Swordsman");
					player.sendMessage(CMessage.changedClass("Swordsman"));
				} else if (name.equals("archer")){
					Config.set("class." + uuid, "Archer");
					player.sendMessage(CMessage.changedClass("Archer"));
				} else if (name.equals("sorcerer")){
					Config.set("class." + uuid, "Sorcerer");
					player.sendMessage(CMessage.changedClass("Sorceer"));
				} else if (name.equals("paladin")){
					Config.set("class." + uuid, "Paladin");
					player.sendMessage(CMessage.changedClass("Paladin"));
				} else {
					player.sendMessage(Message.ERROR_MENU.get());
				}
			}
		}
	}, Main.getInstance());
	
	public static void open(Player player){
		Logger.log(LogLevel.INFO, "Menu", "ClassMenu has been opened for " + player.getName());
		fillMenu();
		menu.open(player);
	}
	
	private static void fillMenu(){
		menu.setOption(1, new ItemStack(Material.DIAMOND_SWORD), "Swordsman", "Damage: ■■■■", "Defence: ■■□□", "Magic: □□□□", "Walk speed: ■■□□", "Range: □□□□");
		menu.setOption(3, new ItemStack(Material.BOW), "Archer", "Damage: ■■□□", "Defence: ■□□□", "Magic: ■□□□", "Walk speed: ■■■■", "Range: ■■■■");
		menu.setOption(5, new ItemStack(Material.STICK), "Sorcerer", "Damage: ■□□□", "Defence: ■□□□", "Magic: ■■■■", "Walk speed: ■■□□", "Range: □□□□");
		menu.setOption(7, new ItemStack(Material.SHIELD), "Paladin", "Damage: ■■■□", "Defence: ■■■■", "Magic: □□□□", "Walk speed: □□□□", "Range: □□□□");
	}

}
