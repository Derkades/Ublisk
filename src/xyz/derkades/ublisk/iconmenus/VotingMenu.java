package xyz.derkades.ublisk.iconmenus;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.modules.Voting;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;

public class VotingMenu extends Menu {

	public VotingMenu(UPlayer player) {
		super("Voting", 9, player);
	}

	@Override
	public List<MenuItem> getMenuItems(Player bukkitPlayer) {
		UPlayer player = new UPlayer(bukkitPlayer);
		int points = player.getVotingPoints();
		int amount;
		if (points > 0 && points < 65) amount = points; else amount = 1;
		return Arrays.asList(
				new MenuItem(3, new ItemStack(Material.PAPER, amount), 
				"Voting points", 
				"You have " + points + " voting points"),
				
				new MenuItem(5, Material.CHEST, 
				"Open vote box", 
				"Costs 3 voting points. Rewards: ",
				"- XP (20-100)",
				"- Gold nugget (0-50)",
				"- Life crystals (0-2)")
			);
	}

	@Override
	public boolean onOptionClick(OptionClickEvent event) {
		String name = event.getName().toLowerCase();
		UPlayer player = new UPlayer(event.getPlayer());
		if (name.contains("points")){
			return false;
		} else {
			if (Voting.playerOpeningBox != null){
				player.sendMessage(Message.VOTE_BOX_BUSY);
				return false;
			} else if (!player.hasVotingPoints(3)){
				player.sendMessage(Message.VOTE_BOX_INSUFFICIENT_POINTS);
				return false;
			} else {
				player.setVotingPoints(player.getVotingPoints() - 3);
				Voting.openVotingBox(player.getPlayer());
				return true;
			}
		}
	}

}
