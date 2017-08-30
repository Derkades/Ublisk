package xyz.derkades.ublisk.utils.inventory;

import org.bukkit.Material;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagString;

public class CustomItem extends Item {
	
	private static final Material CUSTOM_ITEM = Material.BONE;
	
	public CustomItem(String identifier){
		super(CUSTOM_ITEM);
		this.setIdentifier(identifier);
	}
	
	public void setIdentifier(String identifier) {
		this.setNBTValue(UbliskNBT.IDENTIFIER.toString(), new NBTTagString(identifier));
	}
	
	public String getIdentifier() {
		if (this.getNBT().hasKey(UbliskNBT.IDENTIFIER.toString())) {
			return this.getNBT().getString(UbliskNBT.IDENTIFIER.toString());
		} else {
			return null;
		}
	}
	
	private CustomItem(String identifier, boolean hasNbt) {
		super(CUSTOM_ITEM);
		
		if (!hasNbt) {	
			throw new AssertionError("This constructor is only used internally and should always have hasNbt=true.");
		}
	}
	
	public static CustomItem fromItem(Item item){
		if (item.getType() != CUSTOM_ITEM){
			throw new IllegalArgumentException("The provided item is not a custom item.");
		}
		
		NBTTagCompound nbt = item.getNBT();
		
		if (!nbt.hasKey(UbliskNBT.IDENTIFIER.toString())){
			throw new IllegalArgumentException("The provided item does not have an item identifier in its NBT");
		}
		
		String identifier = nbt.getString(UbliskNBT.IDENTIFIER.toString());
		
		return new CustomItem(identifier, true);
	}

}
