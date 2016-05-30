package com.robinmc.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.Music;
import com.robinmc.ublisk.Songs;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;

public class MusicMenu {
	
	private static IconMenu menu = new IconMenu("Select song", 1*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			final Player player = event.getPlayer();
			if (name.contains("bouncy")){
				Music.play(player, Songs.BOUNCY_BALLS);
			} else if (name.contains("comptine")){
				Music.play(player, Songs.COMPTINE_DUN_AUTRE_ETE);
			} else if (name.contains("magic")){
				Music.play(player, Songs.KIND_OF_MAGIC);
			} else {
				player.sendMessage(Messages.menuErrorWrongItem());
			}
		}
	}, Main.getInstance());
	
	public static void open(Player player){
		Console.sendMessage("[Menus] MainMenu has been opened for " + player.getName());
		fillMenu();
		menu.open(player);
	}
	
	private static void fillMenu(){
		menu.setOption(0, new ItemStack(Material.SLIME_BALL), "Bouncy balls");
		menu.setOption(1, new ItemStack(Material.SAPLING), "Comptine d'un autre été");
		menu.setOption(2, new ItemStack(Material.POTION), "A kind of magic");
	}

}
