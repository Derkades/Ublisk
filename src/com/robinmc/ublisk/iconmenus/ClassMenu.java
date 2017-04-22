package com.robinmc.ublisk.iconmenus;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.DataFile;
import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.UPlayer;

public class ClassMenu {
	
	private static IconMenu menu = new IconMenu("Class menu", 1*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			UPlayer player = new UPlayer(event.getPlayer());
			UUID uuid = player.getUniqueId();
			if (HashMaps.COOLDOWN_CLASS.get(player.getUniqueId())){
				player.sendMessage(Message.CLASS_COOLDOWN);
				return;
			} else {
				startCooldown(player);
				if (name.equals("swordsman")){
					DataFile.CLASSES.getConfig().set("class." + uuid, "Swordsman");
					player.sendMessage("You have changed class to Swordsman");
				} else if (name.equals("archer")){
					DataFile.CLASSES.getConfig().set("class." + uuid, "Archer");
					player.sendMessage("You have changed class to Archer");
				} else if (name.equals("sorcerer")){
					DataFile.CLASSES.getConfig().set("class." + uuid, "Sorcerer");
					player.sendMessage("You have changed class to Sorcerer");
				} else if (name.equals("paladin")){
					DataFile.CLASSES.getConfig().set("class." + uuid, "Paladin");
					player.sendMessage("You have changed class to Paladin");
				} else {
					player.sendMessage(Message.ERROR_MENU);
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
	
	private static void startCooldown(UPlayer player){
		final UUID uuid = player.getUniqueId();
		HashMaps.COOLDOWN_CLASS.put(uuid, true);
		new BukkitRunnable(){
			public void run(){
				HashMaps.COOLDOWN_CLASS.put(uuid, false);
			}
		}.runTaskLater(Main.getInstance(), 15*60*20);
	}

}
