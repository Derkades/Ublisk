package com.robinmc.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Voting;

public class VotingMenu {
	
	private static IconMenu menu = new IconMenu("Voting", 1*9, new IconMenu.OptionClickEventHandler(){
		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			UPlayer player = UPlayer.get(event.getPlayer());
			if (name.contains("points")){
				event.setWillClose(false);
			} else {
				if (Voting.isPlayerOpeningBox()){
					player.sendMessage(Message.VOTE_BOX_BUSY);
					event.setWillDestroy(false);
				} else if (!player.hasVotingPoints(3)){
					player.sendMessage(Message.VOTE_BOX_INSUFFICIENT_POINTS);
					event.setWillDestroy(false);
				} else {
					//player.removeVotingPoints(3);
					player.setVotingPoints(player.getVotingPoints() - 3);
					Voting.openVotingBox(player.getPlayer());
				}
			}
		}
	});
	
	public static void open(UPlayer player){
		fillMenu(player);
		menu.open(player);
	}
	
	private static void fillMenu(UPlayer player){
		int points = player.getVotingPoints();
		menu.setOption(3, new ItemStack(Material.PAPER, points), 
				"Voting points", 
				"You have " + points + " voting points");
		menu.setOption(5, new ItemStack(Material.CHEST), 
				"Open vote box", 
				"Costs 3 voting points. Rewards: ",
				"- XP (20-100)",
				"- Gold nugget (0-50)",
				"- Life crystals (0-2)");
	}

}
