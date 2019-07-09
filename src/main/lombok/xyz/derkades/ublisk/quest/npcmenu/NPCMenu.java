package xyz.derkades.ublisk.quest.npcmenu;

import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;

public abstract class NPCMenu extends Menu {

	public NPCMenu(String name, int size, UPlayer player) {
		super(name, size, player);
	}
	
	/*private Option[] options;
	
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
	}*/

}
