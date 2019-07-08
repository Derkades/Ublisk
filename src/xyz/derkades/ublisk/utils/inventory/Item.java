package xyz.derkades.ublisk.utils.inventory;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.minecraft.server.v1_14_R1.NBTBase;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagInt;

public class Item {

	private ItemStack item;

	public Item(final ItemStack item){
		this.item = item;
	}

	public Item(final String skullOwner){
		this.item = new ItemStack(Material.PLAYER_HEAD);
		//this.setDamage(3);
		this.setSkullOwner(skullOwner);
	}

	public Item(final Material material) {
		this.item = new ItemStack(material);
	}

	public Item(final Material material, final int amount) {
		this.item = new ItemStack(material, amount);
	}

	public Item(final org.bukkit.entity.Item itemEntity){
		this.item = itemEntity.getItemStack();
	}

	public Item setAmount(final int amount){
		this.item.setAmount(amount);
		return this;
	}

	public int getAmount(){
		return this.item.getAmount();
	}

	@Deprecated
	public Item setDamage(final int i){
		this.item.setDurability((short) i);
		return this;
	}

	@Deprecated
	public int getDamage(){
		return this.item.getDurability();
	}

	public Item setName(final String name){
		final ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(name);
		this.item.setItemMeta(meta);
		return this;
	}

	public Item setLore(final String... lore){
		return this.setLore(Arrays.asList(lore));
	}

	public Item setLore(final List<String> lore){
		final ItemMeta meta = this.item.getItemMeta();
		meta.setLore(lore);
		this.item.setItemMeta(meta);
		return this;
	}

	@SuppressWarnings("deprecation")
	public Item setSkullOwner(final String playerName){
		if (this.item.getItemMeta() instanceof SkullMeta){
			final SkullMeta meta = (SkullMeta) this.item.getItemMeta();
			meta.setOwner(playerName);
			this.item.setItemMeta(meta);
			return this;
		} throw new UnsupportedOperationException("Item is not a player skull");
	}

	public Item setLeatherArmorColor(final Color color){
		if (this.item.getItemMeta() instanceof LeatherArmorMeta){
			final LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
			meta.setColor(color);
			return this;
		} else {
			throw new UnsupportedOperationException("Item is not leather armor.");
		}
	}

	public NBTTagCompound getNBT(){
		final net.minecraft.server.v1_14_R1.ItemStack nms = CraftItemStack.asNMSCopy(this.item);
		final NBTTagCompound compound = nms.getTag();
		if (compound == null){
			return new NBTTagCompound();
		} else {
			return compound;
		}
	}

	public Item setNBT(final NBTTagCompound nbtTagCompound){
		final net.minecraft.server.v1_14_R1.ItemStack nms = CraftItemStack.asNMSCopy(this.item);
		nms.setTag(nbtTagCompound);
		this.item = CraftItemStack.asBukkitCopy(nms);
		return this;
	}

	public Item setNBTValue(final String key, final NBTBase value){
		final NBTTagCompound nbt = this.getNBT();
		nbt.set(key, value);
		return this.setNBT(nbt);
	}

	public Item setNBTValue(final String key, final boolean bool){
		final NBTTagCompound compound = this.getNBT();
		compound.setBoolean(key, bool);
		return this.setNBT(compound);
	}

	public Material getType(){
		return this.item.getType();
	}

	public Item setType(final Material type){
		this.item.setType(type);
		return this;
	}

	public ItemStack getItemStack(){
		return this.item;
	}

	public Item setDroppable(final boolean droppable){
		return this.setNBTValue(UbliskNBT.ITEM_DROPPABLE.toString(), droppable);
	}

	public boolean isDroppable(){
		final NBTTagCompound nbt = this.getNBT();
		return nbt.hasKey(UbliskNBT.ITEM_DROPPABLE.toString()) && nbt.getBoolean(UbliskNBT.ITEM_DROPPABLE.toString());
	}

	public Item setHealthBonusPercentage(final int percentage){
		return this.setNBTValue(UbliskNBT.HEALTH_BONUS.toString(), new NBTTagInt(percentage));
	}

	public int getHealthBonusPercentage(){
		final NBTTagCompound nbt = this.getNBT();
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
