package xyz.derkades.ublisk.quest.npcmenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ClickedOption {

	private String name;
	private Player player;
	private Material type;
	
	ClickedOption(String name, Player player, Material type){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public Player getClicker(){
		return player;
	}
	
	public Material getItemType(){
		return type;
	}

}
