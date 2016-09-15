package com.robinmc.ublisk.utils.quest;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.inventivetalent.mcwrapper.auth.GameProfileWrapper;
import org.inventivetalent.npclib.npc.living.human.NPCPlayer;
import org.inventivetalent.npclib.registry.NPCRegistry;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.npc.Arzhur;
import com.robinmc.ublisk.npc.Dianh;
import com.robinmc.ublisk.npc.Merek;
import com.robinmc.ublisk.npc.Rasmus;
import com.robinmc.ublisk.npc.Ulric;
import com.robinmc.ublisk.npc.Zoltar;
import com.robinmc.ublisk.utils.variable.Message;
import com.robinmc.ublisk.utils.variable.Var;

public class NPCUtils {
	
	private static NPCRegistry api;
	
	public void spawnNPC(QuestCharacter npc){
		//ClassPool.getDefault().insertClassPath(new LoaderClassPath(CustomEntityInterface.class.getClassLoader()));
		GameProfileWrapper gpw = new GameProfileWrapper(UUID.randomUUID(), npc.getName());
		NPCPlayer customNPC = api.spawnPlayerNPC(npc.getLocation().getBukkitLocation(), NPCPlayer.class, gpw);
		customNPC.setSkin(npc.getSkin());
	}
	
	public void spawnAll(){
		NPCUtils api = new NPCUtils();
		for (QuestCharacter npc : QuestCharacter.values()){
			api.spawnNPC(npc);
		}
	}
	
	public void despawnAll(){
		for (Entity entity : Var.WORLD.getEntitiesByClasses(NPCPlayer.class)){
			NPCPlayer npc = (NPCPlayer) entity;
			npc.despawn();
		}
	}
	
	public void talk(Player player, QuestCharacter npc){
		// XXX: Better solution than this
		String name = npc.getName();
		if (npc.getName().equals("Merek")){
			new Merek().talk(player);
		} else if (name.equals("Ulric")){
			new Ulric().talk(player);
		} else if (name.equals("Arzhur")){
			new Arzhur().talk(player);
		} else if (name.equals("Asher")){
			player.sendMessage(Message.Complicated.Quests.npcNotFound(npc.getName()));
		} else if (name.equals("Rasmus")){
			new Rasmus().talk(player);
		} else if (name == "Dianh"){
			new Dianh().talk(player);
		} else if (name == "Zoltar"){
			new Zoltar().talk(player);
		}
	}

	public static void createNPCRegistry(){
		api = org.inventivetalent.npclib.NPCLib.createRegistry(Main.getInstance());
	}
	
}
