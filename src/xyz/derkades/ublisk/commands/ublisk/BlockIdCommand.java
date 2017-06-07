package xyz.derkades.ublisk.commands.ublisk;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import xyz.derkades.ublisk.utils.UPlayer;

public class BlockIdCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		Block block = player.getPlayer().getTargetBlock(((Set<Material>) null), 10);
		@SuppressWarnings("deprecation")
		int id = block.getTypeId();
		@SuppressWarnings("deprecation")
		byte data = block.getData();
		player.sendMessage(ChatColor.BOLD + "" + id + ":" + data);
	}

	@Override
	protected String getDescription() {
		return "Tells you the id of the block you are looking at right now";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"ip"};
	}

}
