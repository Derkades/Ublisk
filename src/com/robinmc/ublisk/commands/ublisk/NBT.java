package com.robinmc.ublisk.commands.ublisk;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

import com.robinmc.ublisk.utils.UPlayer;

import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class NBT extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		Entity entity = player.getLocation().getChunk().getEntities()[0];
		net.minecraft.server.v1_11_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		NBTTagCompound tag = new NBTTagCompound();
		nmsEntity.c(tag);
		player.sendMessage(tag.getString("CustomName"));
	}

	@Override
	protected String getDescription() {
		return "asdasd";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"nbt"};
	} 

}
