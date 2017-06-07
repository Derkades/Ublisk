package xyz.derkades.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.modules.Voting;
import xyz.derkades.ublisk.utils.IconMenu;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.IconMenu.OptionClickEvent;

public class VotingMenu {
	
	private static IconMenu menu = new IconMenu("Voting", 1*9, new IconMenu.OptionClickEventHandler(){
		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			UPlayer player = new UPlayer(event.getPlayer());
			if (name.contains("points")){
				event.setWillClose(false);
			} else {
				if (Voting.playerOpeningBox != null){
					player.sendMessage(Message.VOTE_BOX_BUSY);
					event.setWillDestroy(false);
				} else if (!player.hasVotingPoints(3)){
					player.sendMessage(Message.VOTE_BOX_INSUFFICIENT_POINTS);
					event.setWillDestroy(false);
				} else {
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
