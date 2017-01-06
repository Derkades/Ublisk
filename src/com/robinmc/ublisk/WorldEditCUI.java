package com.robinmc.ublisk;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.TileEntityStructure;

public class WorldEditCUI implements Listener {

	// TODO Better integration with the rest of the plugin
	
	public HashMap<UUID, CUIBlock> blockStates;
	public WorldEditPlugin worldEditPlugin;

	@SuppressWarnings({
			"unchecked", "rawtypes"
	})
	public WorldEditCUI() {
		this.blockStates = new HashMap();
	}

	public void onEnable() {
		this.worldEditPlugin = ((WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit"));
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
		new BukkitRunnable() {

			public void run() {
				WorldEditCUI.this.updateSelections(0L);
			}
		}.runTaskTimer(Main.getInstance(), 10L, 30L);
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		updateSelections(1L);
	}

	@EventHandler
	public void onCommandEvent(PlayerCommandPreprocessEvent event) {
		updateSelections(5L);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (this.blockStates.containsKey(player.getUniqueId())) {
			CUIBlock oldState = (CUIBlock) this.blockStates.get(player.getUniqueId());
			oldState.location.getBlock().setTypeIdAndData(oldState.type.getId(), oldState.rawData, false);
			this.blockStates.remove(player.getUniqueId());
		}
	}

	public void updateSelections(long delay) {
		new BukkitRunnable() {

			@SuppressWarnings("deprecation")
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					Selection selection = WorldEditCUI.this.worldEditPlugin.getSelection(player);
					if ((selection != null) && (selection.getHeight() < 33) && (selection.getLength() < 33)) {
						Location loc = selection.getMinimumPoint().add(0.0D, -1.0D, 0.0D);
						if (WorldEditCUI.this.blockStates.containsKey(player.getUniqueId())) {
							WorldEditCUI.CUIBlock oldState = (WorldEditCUI.CUIBlock) WorldEditCUI.this.blockStates
									.get(player.getUniqueId());
							if ((!oldState.location.equals(loc))
									|| (!oldState.locationTop.equals(selection.getMaximumPoint()))) {
								oldState.location.getBlock().setTypeIdAndData(oldState.type.getId(), oldState.rawData,
										false);
								WorldEditCUI.this.blockStates.remove(player.getUniqueId());
							}
						} else {
							WorldEditCUI.this.blockStates.put(player.getUniqueId(), new WorldEditCUI.CUIBlock(
									loc.getBlock(), selection.getMaximumPoint()));
							loc.getBlock().setType(Material.STRUCTURE_BLOCK, false);
							BlockPosition pos = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
							TileEntityStructure structure = (TileEntityStructure) ((CraftPlayer) player)
									.getHandle().world.getTileEntity(pos);
							structure.a(WorldEditCUI.this.createNBTData(player, selection));
						}
					}
				}
			}
		}.runTaskLater(Main.getInstance(), delay);
	}

	public NBTTagCompound createNBTData(Player player, Selection selection) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setString("id", "Structure");
		nbttagcompound.setInt("x", selection.getMinimumPoint().getBlockX());
		nbttagcompound.setInt("y", Math.max(0, selection.getMinimumPoint().getBlockY() - 1));
		nbttagcompound.setInt("z", selection.getMinimumPoint().getBlockZ());
		nbttagcompound.setString("name", "WECUI");
		nbttagcompound.setString("author", "minelazz");
		nbttagcompound.setString("metadata", "");

		nbttagcompound.setInt("posX", 0);
		nbttagcompound.setInt("posY", 1);
		nbttagcompound.setInt("posZ", 0);
		nbttagcompound.setInt("sizeX",
				selection.getMaximumPoint().getBlockX() - selection.getMinimumPoint().getBlockX() + 1);
		nbttagcompound.setInt("sizeY",
				selection.getMaximumPoint().getBlockY() - selection.getMinimumPoint().getBlockY() + 1);
		nbttagcompound.setInt("sizeZ",
				selection.getMaximumPoint().getBlockZ() - selection.getMinimumPoint().getBlockZ() + 1);
		nbttagcompound.setString("rotation", "NONE");
		nbttagcompound.setString("mirror", "NONE");
		nbttagcompound.setString("mode", "SAVE");
		nbttagcompound.setByte("ignoreEntities", (byte) 1);

		nbttagcompound.setBoolean("powered", false);
		nbttagcompound.setBoolean("showair", false);
		nbttagcompound.setBoolean("showboundingbox", true);
		nbttagcompound.setFloat("integrity", 1.0F);
		nbttagcompound.setLong("seed", 0L);
		return nbttagcompound;
	}

	class CUIBlock {

		public Location location;
		public Location locationTop;
		public Material type;
		public byte rawData;

		@SuppressWarnings("deprecation")
		public CUIBlock(Block block, Location top) {
			this.type = block.getType();
			this.rawData = block.getData();
			this.location = block.getLocation();
			this.locationTop = top;
		}
	}
}
