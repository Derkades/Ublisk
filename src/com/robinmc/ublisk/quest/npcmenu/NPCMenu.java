package com.robinmc.ublisk.quest.npcmenu;

import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEventHandler;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;

public class NPCMenu {
	
	private Option[] options;
	
	private IconMenu menu;
	
	public NPCMenu(String name, final ClickAction action, Option... options){
		Logger.log(LogLevel.DEBUG, name);
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
