package xyz.derkades.ublisk.utils.inventory;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.minecraft.server.v1_13_R2.NBTBase;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagInt;

public class Item {
	
	private ItemStack item;
	
	public Item(ItemStack item){
		this.item = item;
	}
	
	public Item(String skullOwner){
		item = new ItemStack(Material.PLAYER_HEAD);
		//this.setDamage(3);
		this.setSkullOwner(skullOwner);
	}
	
	public Item(Material material) {
		this.item = new ItemStack(material);
	}
	
	public Item(Material material, int amount) {
		this.item = new ItemStack(material, amount);
	}
	
	public Item(org.bukkit.entity.Item itemEntity){
		this.item = itemEntity.getItemStack();
	}

	public Item setAmount(int amount){
		item.setAmount(amount);
		return this;
	}
	
	public int getAmount(){
		return item.getAmount();
	}
	
	@Deprecated
	public Item setDamage(int i){
		item.setDurability((short) i);
		return this;
	}
	
	@Deprecated
	public int getDamage(){
		return item.getDurability();
	}
	
	public Item setName(String name){
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return this;
	}
	
	public Item setLore(String... lore){
		return this.setLore(Arrays.asList(lore));
	}
	
	public Item setLore(List<String> lore){
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public Item setSkullOwner(String playerName){
		if (item.getItemMeta() instanceof SkullMeta){
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			meta.setOwner(playerName);
			item.setItemMeta(meta);
			return this;
		} throw new UnsupportedOperationException("Item is not a player skull");
	}
	
	public Item setLeatherArmorColor(Color color){
		if (item.getItemMeta() instanceof LeatherArmorMeta){
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(color);
			return this;
		} else {
			throw new UnsupportedOperationException("Item is not leather armor.");
		}
	}
	
	public NBTTagCompound getNBT(){
		net.minecraft.server.v1_13_R2.ItemStack nms = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = nms.getTag();
		if (compound == null){
			return new NBTTagCompound();
		} else {
			return compound;
		}
	}
	
	public Item setNBT(NBTTagCompound nbtTagCompound){
		net.minecraft.server.v1_13_R2.ItemStack nms = CraftItemStack.asNMSCopy(item);
		nms.setTag(nbtTagCompound);
		item = CraftItemStack.asBukkitCopy(nms);
		return this;
	}
	
	public Item setNBTValue(String key, NBTBase value){
		NBTTagCompound nbt = this.getNBT();
		nbt.set(key, value);
		return this.setNBT(nbt);
	}
	
	public Item setNBTValue(String key, boolean bool){
		NBTTagCompound compound = this.getNBT();
		compound.setBoolean(key, bool);
		return this.setNBT(compound);
	}
	
	public Material getType(){
		return item.getType();
	}
	
	public Item setType(Material type){
		item.setType(type);
		return this;
	}
	
	public ItemStack getItemStack(){
		return item;
	}
	
	public Item setDroppable(boolean droppable){
		return this.setNBTValue(UbliskNBT.ITEM_DROPPABLE.toString(), droppable);
	}
	
	public boolean isDroppable(){
		NBTTagCompound nbt = this.getNBT();
		return nbt.hasKey(UbliskNBT.ITEM_DROPPABLE.toString()) && nbt.getBoolean(UbliskNBT.ITEM_DROPPABLE.toString());
	}
	
	public Item setHealthBonusPercentage(int percentage){
		return this.setNBTValue(UbliskNBT.HEALTH_BONUS.toString(), new NBTTagInt(percentage));
	}
	
	public int getHealthBonusPercentage(){
		NBTTagCompound nbt = this.getNBT();
		if (nbt.hasKey(UbliskNBT.HEALTH_BONUS.toString())){
			return this.getNBT().getInt(UbliskNBT.HEALTH_BONUS.toString());
		} else {
			return 0;
		}
	}
	
	@Override
	public String toString(){
		return String.format("Item[type=%s,amount=%s,data=%s]", this.getType().toString(), String.valueOf(this.getAmount()), String.valueOf(this.getDamage())); 
	}

}
