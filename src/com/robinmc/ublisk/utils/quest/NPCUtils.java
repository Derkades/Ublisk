package com.robinmc.ublisk.utils.quest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.exception.NPCNotFoundException;
import com.robinmc.ublisk.utils.variable.CMessage;
import com.robinmc.ublisk.utils.variable.Var;

import de.inventivegames.npc.NPCLib;

public class NPCUtils {
	
	/**
	 * Spawns an NPC at the given location
	 * @param x
	 * @param y
	 * @param z
	 * @param name Name of NPC as will appear floating above it
	 * @param skin In-game name of the skin this NPC will have
	 */
	@Deprecated
	public void spawnNPC(int x, int y, int z, String name, String skin){
		Location loc = new Location(Var.world(), x, y, z);
		NPCLib.spawnPlayerNPC(loc, name, skin);
		
	}
	
	/**
	 * Spawns an NPC at the given location
	 * @param x
	 * @param y
	 * @param z
	 * @param pitch
	 * @param yaw
	 * @param name Name of NPC as will appear floating above it
	 * @param skin In-game name of the skin this NPC will have
	 */
	@Deprecated
	public void spawnNPC(double x, int y, double z, int pitch, int yaw, String name, String skin){
		Location loc = new Location(Var.world(), x, y, z, pitch, yaw);
		NPCLib.spawnPlayerNPC(loc, name, skin);
	}

	@Deprecated
	public void spawnNPC(double x, int y, double z, int pitch, int yaw, String name){
		Location loc = new Location(Var.world(), x, y, z, pitch, yaw);
		//NPCLib.spawnPlayerNPC(loc, name, skin);
		NPCLib.spawnNPC(EntityType.VILLAGER, loc, name);
	}
	
	@Deprecated
	public void spawnNPC(Location loc, String name, String skin){
		NPCLib.spawnPlayerNPC(loc, name, skin);
	}
	
	@Deprecated
	public void spawnNPC(NPCLocation npcloc, String name, String skin){
		NPCLib.spawnPlayerNPC(npcloc.getBukkitLocation(), name, skin);
	}
	
	public void spawnNPC(QuestCharacter npc){
		NPCLib.spawnPlayerNPC(npc.getLocation().getBukkitLocation(), npc.getName(), npc.getSkin());
	}
	
	public void msg(Player player, String npc, String msg){
		player.sendMessage(CMessage.npcMsg(npc, msg));
	}
	
	public void open(Player player, String name){
		try {
			Method m = getMethodFromName(name);
			m.invoke(player);
		} catch (ClassNotFoundException 
				| IllegalAccessException 
				| IllegalArgumentException 
				| InvocationTargetException 
				| SecurityException e){
			e.printStackTrace();
		} catch (NPCNotFoundException e) {
			player.sendMessage(CMessage.npcNotFound(name));
		}
	}
	
	private Method getMethodFromName(String className) throws ClassNotFoundException, NPCNotFoundException {
		Class<?> clazz;
		clazz = Class.forName(className);
		
		try {
			return clazz.getMethod("main");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new NPCNotFoundException();
		}
		
	}
	
}
