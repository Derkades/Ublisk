package xyz.derkades.ublisk.commands.ublisk;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.derkades.ublisk.Town;
import xyz.derkades.ublisk.utils.UPlayer;

public class TownCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		List<BaseComponent> list = new ArrayList<BaseComponent>();
		
		for (Town town : Town.values()){
			TextComponent component = new TextComponent(town.getName() + "   ");
			double x = town.getSpawnLocation().getX();
			double y = town.getSpawnLocation().getY();
			double z = town.getSpawnLocation().getZ();
			component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp @p " + x + " " + y + " " + z));
			
			list.add(component);
		}

		BaseComponent[] components = list.toArray(new BaseComponent[]{});
		
		player.sendMessage(components);
	}

	@Override
	protected String getDescription() {
		return "Town teleporter - click on a town name";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"town", "towns", "towntp"};
	}

}
