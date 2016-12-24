package com.robinmc.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.NotSetException;
import com.robinmc.ublisk.utils.settings.Setting;

public class SettingsMenu {

	private static IconMenu menu = new IconMenu("Settings", 1*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName();
			final UPlayer player = UPlayer.get(event);
		
			event.setWillClose(false);
			
			if (name.equalsIgnoreCase("back")){
				MainMenu.open(player);
				return;
			}
			
			Setting setting = Setting.fromName(name);
			
			try {
				//If setting is set to true, set to false and if set to false, set to true
				player.setSetting(setting, !player.getSetting(setting));
				
				String enabledDisabled;
				if (player.getSetting(setting)){
					enabledDisabled = "enabled";
				} else {
					enabledDisabled = "disabled";
				}
				
				player.sendMessage(Message.prefix("Settings") + name + " has been " + enabledDisabled + ".");
			} catch (NotSetException e) {
				player.setSetting(setting, false);
			}
			
		}
	});
	
	public static void open(UPlayer player){
		fillMenu(player);
		menu.open(player);
	}
	
	private static void fillMenu(UPlayer player){
		int slot = 0;
		for (Setting setting : Setting.values()){
			//If setting is on, set dye color to green, otherwise to gray
			short damage;
			try {
				if (player.getSetting(setting)){
					damage = 10;
				} else {
					damage = 8;
				}
			} catch (NotSetException e) {
				damage = 5;
			}
			
			ItemStack item = new ItemStack(Material.INK_SACK);
			item.setDurability(damage);
			
			menu.setOption(slot, item, setting.getName(), setting.getInfo());
			
			slot++;
		}
		
		menu.setOption(8, new ItemStack(Material.BARRIER), "Back");
	}
	
}
