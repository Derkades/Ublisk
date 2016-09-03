package com.robinmc.ublisk.utils.inventory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Main;

public class InvUtils {
	
	public static String path = Main.getInstance().getDataFolder() + "\\inv";
	
	// https://bukkit.org/threads/save-inventory-to-config-file.164890/ Post #9 (Modified)
	public static void saveIntentory(Player player){
		YamlConfiguration c = new YamlConfiguration();
		c.set("inventory.armor", player.getInventory().getArmorContents());
		c.set("inventory.content", player.getInventory().getContents());
		try {
			c.save(new File(path, player.getName()+".yml"));
		} catch (IOException e) {
			// TODO Deal with exception
			e.printStackTrace();
		}
	}
	
	// https://bukkit.org/threads/save-inventory-to-config-file.164890/ Post #9 (Modified)
    @SuppressWarnings("unchecked")
    public static void restoreInventory(Player player){
    	File file = new File(path, player.getName()+".yml");
        YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
        List<ItemStack> content = (List<ItemStack>) c.get("inventory.armor");
        ItemStack[] array = content.toArray(new ItemStack[0]);
        player.getInventory().setArmorContents(array);
        content = (List<ItemStack>) c.get("inventory.content");
        array = content.toArray(new ItemStack[0]);
        player.getInventory().setContents(array);
        file.delete();
    }

}
