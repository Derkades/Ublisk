package com.robinmc.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.Music;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;

public class MainMenu {
	
	private static IconMenu menu = new IconMenu("Main menu", 3*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			final Player player = event.getPlayer();
			if (name.contains("music")){
				try { //TODO: Update code with new settings api
					if (Config.getBoolean("settings.music." + player.getUniqueId())){
						Config.set("settings.music." + player.getUniqueId(), false);
						player.sendMessage(Message.MUSIC_DISABLED.get());
					} else {
						Config.set("settings.music." + player.getUniqueId(), true);
						player.sendMessage(Message.MUSIC_ENABLED.get());
						String town = Config.getString("last-town." + player.getUniqueId());
				        Music.playSong(player, town);
					}
				} catch (Exception e){
					Config.set("settings.music." + player.getUniqueId(), false);
					player.sendMessage(Message.MUSIC_DISABLED.get());
				}
			} else {
				player.sendMessage(Message.ERROR_MENU.get());
			}
		}
	}, Main.getInstance());
	
	public static void open(Player player){
		Console.sendMessage("[Menus] MainMenu has been opened for " + player.getName());
		fillMenu();
		menu.open(player);
	}
	
	private static void fillMenu(){
		menu.setOption(0, new ItemStack(Material.JUKEBOX), "Toggle music");
	}

}
