package com.robinmc.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Voting;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.third_party.IconMenu;
import com.robinmc.ublisk.utils.third_party.IconMenu.OptionClickEvent;

public class VotingMenu {
	
	private static IconMenu menu = new IconMenu("Voting", 1*9, new IconMenu.OptionClickEventHandler(){
		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			Player player = event.getPlayer();
			if (name.contains("points")){
				event.setWillClose(false);
			} else {
				if (Voting.isPlayerOpeningBox()){
					player.sendMessage("someone is already opening box"); // TODO Proper message
					event.setWillDestroy(false);
				} else if (!Voting.hasVotingPoints(player, 3)){
					player.sendMessage("not enough points"); // TODO Proper message
					event.setWillDestroy(false);
				} else {
					Voting.removeVotingPoints(player, 3);
					Voting.openVotingBox(player);
				}
			}
		}
	}, Main.getInstance());
	
	public static void open(Player player){
		Logger.log(LogLevel.INFO, "Menu", "Voting has been opened for " + player.getName());
		fillMenu(player);
		menu.open(player);
	}
	
	private static void fillMenu(Player player){
		int points = Voting.getVotingPoints(player);
		menu.setOption(3, new ItemStack(Material.PAPER, points), 
				"Voting points", 
				"You have " + points + " voting points");
		menu.setOption(5, new ItemStack(Material.CHEST), 
				"Open vote box", 
				"Costs 5 voting points. Rewards: ",
				"- XP (20-100)",
				"- Gold nugget (0-50)",
				"- Life crystals (0-2)");
	}

}
