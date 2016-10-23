package com.robinmc.ublisk.quest.npcmenu;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.third_party.IconMenu;
import com.robinmc.ublisk.utils.third_party.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.third_party.IconMenu.OptionClickEventHandler;

public class NPCMenu {
	
	private String name;
	private Option[] options;
	
	private IconMenu menu;
	
	public NPCMenu(String name, final ClickAction action, Option... options){
		Logger.log(LogLevel.DEBUG, name);
		this.name = name;
		this.options = options;
		
		menu = new IconMenu(name, 6*9, new OptionClickEventHandler(){
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				ClickedOption option = new ClickedOption(
						event.getName(), 
						event.getPlayer(), 
						event.getItem().getType());
				action.click(option);
			}
			
		});
	}
	
	public void open(UPlayer player){
		Logger.log(LogLevel.INFO, "NPCMenu", name + " has been opened for " + player.getName());
		fillMenu(player);
		menu.open(player);
	}
	
	private void fillMenu(UPlayer player){
		menu.fillEdgesWithGlass();
		for (Option option : options){
			menu.setOption(option.getPosition(), option.getItemStack(), option.getName(), option.getLore());
		}
	}

}
