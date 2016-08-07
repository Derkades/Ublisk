package com.robinmc.ublisk.utils.quest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.inventivetalent.mcwrapper.auth.GameProfileWrapper;
import org.inventivetalent.npclib.npc.living.human.NPCPlayer;
import org.inventivetalent.npclib.registry.NPCRegistry;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.exception.NPCNotFoundException;
import com.robinmc.ublisk.utils.variable.Var;

public class NPCUtils {
	
	private static NPCRegistry api;
	
	public void spawnNPC(QuestCharacter npc){
		GameProfileWrapper gpw = new GameProfileWrapper(null, npc.getName());
		CustomClassNPC customNPC = api.spawnPlayerNPC(npc.getLocation().getBukkitLocation(), CustomClassNPC.class, gpw);
		customNPC.setSkin(npc.getSkin());
	}
	
	public void spawnAll(){
		NPCUtils api = new NPCUtils();
		for (QuestCharacter npc : QuestCharacter.values()){
			api.spawnNPC(npc);
		}
	}
	
	public void despawnAll(){
		for (Entity entity : Var.world().getEntitiesByClasses(NPCPlayer.class)){
			NPCPlayer npc = (NPCPlayer) entity;
			npc.despawn();
		}
	}
	
	public void talk(Player player, QuestCharacter npc) throws NPCNotFoundException{
		if (npc.getQuestCharacterClass() == null){
			throw new NPCNotFoundException();
		}
		try {
			Method m = npc.getQuestCharacterClass().getClass().getMethod("talk", Player.class);
			m.invoke(player);
		} catch (IllegalAccessException 
				| IllegalArgumentException 
				| InvocationTargetException 
				| SecurityException e){
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			throw new NPCNotFoundException();
		}
	}
	
	public static void createNPCRegistry(){
		api = org.inventivetalent.npclib.NPCLib.createRegistry(Main.getInstance());
	}
	
}
