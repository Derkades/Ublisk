package com.robinmc.ublisk.utils.inventory;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Main;

import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class InvUtils {
	
	public static String path = Main.getInstance().getDataFolder() + "\\inv";
	
	// https://bukkit.org/threads/save-inventory-to-config-file.164890/ Post #9 (Modified)
	public static void saveIntentory(String path, Player player){
		YamlConfiguration c = new YamlConfiguration();
		c.set("inventory.armor", player.getInventory().getArmorContents());
		c.set("inventory.content", player.getInventory().getContents());
		try {
			c.save(new File(path, player.getName()+".yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// https://bukkit.org/threads/save-inventory-to-config-file.164890/ Post #9 (Modified)
    public static void restoreInventory(String path, Player player){
    	File file = new File(path, player.getName()+".yml");
        YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
        ItemStack[] content = c.getList("inventory.armor").toArray(new ItemStack[]{});
        player.getInventory().setArmorContents(content);
        content = c.getList("inventory.content").toArray(new ItemStack[]{});
        player.getInventory().setContents(content);
        file.delete();
    }
    
	public static ItemStack applyCompound(ItemStack item, NBTTagCompound compound){
		net.minecraft.server.v1_11_R1.ItemStack nms = CraftItemStack.asNMSCopy(item);
		nms.setTag(compound);
		return CraftItemStack.asBukkitCopy(nms);
	}
	
	public static NBTTagCompound getCompound(ItemStack item){
		return CraftItemStack.asNMSCopy(item).getTag();
	}

}
