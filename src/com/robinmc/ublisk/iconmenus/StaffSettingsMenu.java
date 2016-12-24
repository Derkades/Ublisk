package com.robinmc.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.NotSetException;
import com.robinmc.ublisk.utils.settings.StaffSetting;

public class StaffSettingsMenu {

	private static IconMenu menu = new IconMenu("Staff Settings", 1*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName();
			final Player player = event.getPlayer();
		
			event.setWillClose(false);
			
			if (name.equalsIgnoreCase("back")){
				MainMenu.open(UPlayer.get(player));
				return;
			}
			
			StaffSetting setting = StaffSetting.fromName(name);
			
			try {
				//If setting is set to true, set to false and if set to false, set to true
				setting.put(player, !setting.get(player));
				
				String enabledDisabled;
				if (setting.get(player)){
					enabledDisabled = "enabled";
				} else {
					enabledDisabled = "disabled";
				}
				
				player.sendMessage(Message.prefix("Settings") + name + " has been " + enabledDisabled + ".");
			} catch (NotSetException e) {
				setting.put(player, false);
			}
		}
	});
	
	public static void open(UPlayer player){
		fillMenu(player.getPlayer());
		menu.open(player);
	}
	
	private static void fillMenu(Player player){
		int slot = 0;
		for (StaffSetting setting : StaffSetting.values()){
			//If setting is on, set dye color to green, otherwise to gray
			short damage;
			try {
				if (setting.get(player)){
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
