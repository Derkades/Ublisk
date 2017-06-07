package xyz.derkades.ublisk.iconmenus.help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.utils.IconMenu;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.IconMenu.OptionClickEvent;
import xyz.derkades.ublisk.utils.inventory.Item;

public class CommandsHelp {
	
	private static IconMenu menu = new IconMenu("Commands", 2*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(final OptionClickEvent event) {
			event.setWillClose(false);
			if (event.getName().equals("Back")){
				HelpMenu.open(new UPlayer(event.getPlayer()));
			}
		}
	});
	
	public static void open(UPlayer player){
		fillMenu();
		menu.open(player);
	}
	
	private static void fillMenu(){
		int i = 0;
		
		Map<String, String> commands = new HashMap<>();
		
		//Put these commands here in reverse order!!
		commands.put("AFK", "afk");
		commands.put("Private Messaging", "pm");
		commands.put("Guilds", "guild");
		
		for (Entry<String, String> entry : commands.entrySet()){
			ItemStack icon = new Item(Material.INK_SACK).setDamage(8).getItemStack();
			List<String> loreLines = new ArrayList<String>();
			loreLines.add(getDescription(entry.getValue()));
			
			loreLines.add("");
			
			String[] usageLines = getUsage(entry.getValue()).split("\n");
			for (String usageLine : usageLines) loreLines.add(ChatColor.RED + usageLine);
			
			menu.setOption(i, icon, entry.getKey() + " - /" + entry.getValue(), loreLines.toArray(new String[]{}));
			
			i++;
		}
		
		menu.setOption(17, new ItemStack(Material.BARRIER), "Back");
	}
	
	private static String getUsage(String command){
		return Main.getInstance().getCommand(command).getUsage();
	}
	
	private static String getDescription(String command){
		return Main.getInstance().getCommand(command).getDescription();
	}

}
