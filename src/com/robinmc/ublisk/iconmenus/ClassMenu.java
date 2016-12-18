package com.robinmc.ublisk.iconmenus;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Cooldown;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.DataFile;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;

@Deprecated
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
					DataFile.CLASSES.set("class." + uuid, "Swordsman");
					player.sendMessage(Message.Complicated.changedClass("Swordsman"));
				} else if (name.equals("archer")){
					DataFile.CLASSES.set("class." + uuid, "Archer");
					player.sendMessage(Message.Complicated.changedClass("Archer"));
				} else if (name.equals("sorcerer")){
					DataFile.CLASSES.set("class." + uuid, "Sorcerer");
					player.sendMessage(Message.Complicated.changedClass("Sorcerer"));
				} else if (name.equals("paladin")){
					DataFile.CLASSES.set("class." + uuid, "Paladin");
					player.sendMessage(Message.Complicated.changedClass("Paladin"));
				} else {
					player.sendMessage(Message.ERROR_MENU.get());
				}
			}
		}
	});
	
	public static void open(Player player){
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
