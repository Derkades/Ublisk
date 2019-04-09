package xyz.derkades.ublisk.iconmenus.help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.derkutils.bukkit.menu.OptionClickEvent;
import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.utils.ItemBuilder;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;

public class CommandsHelp extends Menu {

	public CommandsHelp(UPlayer player) {
		super("Help", 2*9, player);
		
		int i = 0;
		
		Map<String, String> commands = new HashMap<>();
		
		//Put these commands here in reverse order!!
		commands.put("AFK", "afk");
		commands.put("Private Messaging", "pm");
		commands.put("Guilds", "guild");
		
		for (Entry<String, String> entry : commands.entrySet()){

			List<String> loreLines = new ArrayList<String>();
			loreLines.add(getDescription(entry.getValue()));
			
			loreLines.add("");
			
			String[] usageLines = getUsage(entry.getValue()).split("\n");
			for (String usageLine : usageLines) loreLines.add(ChatColor.RED + usageLine);
			
			ItemStack icon = new ItemBuilder(Material.LIGHT_GRAY_DYE)
					.name(entry.getKey() + " - /" + entry.getValue())
					.lore(loreLines)
					.create();
			
			items.put(i, icon);
			
			i++;
		}
		
		items.put(17, new ItemBuilder(Material.BARRIER).name(ChatColor.RED + "Back").create());
	}

	@Override
	public boolean onOptionClick(OptionClickEvent event) {
		if (event.getName().equals("Back")){
			new HelpMenu(new UPlayer(event)).open();
		}
		return false;
	}
	
	private static String getUsage(String command){
		return Main.getInstance().getCommand(command).getUsage();
	}
	
	private static String getDescription(String command){
		return Main.getInstance().getCommand(command).getDescription();
	}

}
