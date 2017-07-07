package xyz.derkades.ublisk.iconmenus;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.derkutils.bukkit.ItemBuilder;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.settings.Setting;

public class SettingsMenu extends Menu {

	public SettingsMenu(UPlayer player) {
		super("Settings", 9, player);
		
		int slot = 0;
		for (Setting setting : Setting.values()){
			//If setting is on, set dye color to green, otherwise to gray
			short damage;
			if (player.getSetting(setting)){
				damage = 10;
			} else {
				damage = 8;
			}
			
			items.put(slot, new ItemBuilder(Material.INK_SACK)
					.data(damage)
					.name(setting.getName())
					.lore(setting.getInfo())
					.create());
			
			slot++;
		}
		
		items.put(8, new ItemBuilder(Material.BARRIER).name(ChatColor.RED + "Back").create());
	}

	@Override
	public boolean onOptionClick(OptionClickEvent event) {
		String name = event.getName();
		final UPlayer player = new UPlayer(event.getPlayer());
		
		if (name.equalsIgnoreCase("back")){
			new MainMenu(player).open();
			return false;
		}
		
		Setting setting = Setting.fromName(name);
		
		//If setting is set to true, set to false and if set to false, set to true
		player.setSetting(setting, !player.getSetting(setting));
			
		String enabledDisabled;
		if (player.getSetting(setting))
			enabledDisabled = "enabled";
		else 
			enabledDisabled = "disabled";
			
		player.sendMessage(Ublisk.getPrefix("Settings") + name + " has been " + enabledDisabled + ".");
		
		this.open(); //Re-open menu to refresh settings
		
		return false;
	}
	
}
