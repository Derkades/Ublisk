package xyz.derkades.ublisk.utils.inventory;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InvUtils {
	
	public static void saveIntentory(String path, Player player){
		YamlConfiguration config = new YamlConfiguration();
		config.set("inventory.armor", player.getInventory().getArmorContents());
		config.set("inventory.content", player.getInventory().getContents());
		try {
			config.save(new File(path, player.getName() + ".yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static void restoreInventory(String path, Player player){
    	File file = new File(path, player.getName()+".yml");
        YamlConfiguration cconfig = YamlConfiguration.loadConfiguration(file);
        ItemStack[] content = cconfig.getList("inventory.armor").toArray(new ItemStack[]{});
        player.getInventory().setArmorContents(content);
        content = cconfig.getList("inventory.content").toArray(new ItemStack[]{});
        player.getInventory().setContents(content);
        file.delete();
    }

}
