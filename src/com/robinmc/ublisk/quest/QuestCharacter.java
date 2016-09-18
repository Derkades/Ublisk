package com.robinmc.ublisk.quest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.robinmc.ublisk.quest.npc.Alvin;
import com.robinmc.ublisk.quest.npc.Arzhur;
import com.robinmc.ublisk.quest.npc.David;
import com.robinmc.ublisk.quest.npc.Dianh;
import com.robinmc.ublisk.quest.npc.Merek;
import com.robinmc.ublisk.quest.npc.Rasmus;
import com.robinmc.ublisk.quest.npc.Ulric;
import com.robinmc.ublisk.quest.npc.Zoltar;
import com.robinmc.ublisk.utils.ULocation;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.NPCNotFoundException;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.variable.Message;
import com.robinmc.ublisk.utils.variable.Var;

import net.md_5.bungee.api.ChatColor;

public enum QuestCharacter {
	
	DAVID(new David(), "David", new ULocation(72.5, 67, -2.5, 13, 0)),
	MEREK(new Merek(), "Merek", new ULocation(33, 67, -38, -70, 0)),
	ULRIC(new Ulric(), "Ulric", new ULocation(38.5, 67, -26.5, -145, 0)),
	ARZHUR(new Arzhur(), "Arzhur", new ULocation(111.5, 68, -103.5, -20, 0)),
	//ASHER(null, "Asher", new NPCLocation(449.3, 70, -10.5, 75, 5)),
	RASMUS(new Rasmus(), "Rasmus", ULocation.UNKNOWN_NPC_LOCATION), // TODO Rasmus coordinates
	DIANH(new Dianh(), "Dianh", ULocation.UNKNOWN_NPC_LOCATION),// TODO coordinaten van Dianh
	ZOLTAR(new Zoltar(), "Zoltar", ULocation.UNKNOWN_NPC_LOCATION),// TODO coordinaten van Zoltar
	ALVIN(new Alvin(), "Alvin", ULocation.UNKNOWN_NPC_LOCATION); // TODO Alvin coordinates
	
	private QuestCharacterClass qcc;
	private String name;
	private Location loc;
	
	QuestCharacter(QuestCharacterClass qcc, String name, ULocation loc){
		this.qcc = qcc;
		this.name = name;
		this.loc = loc.get();
	}
	
	public QuestCharacterClass getQuestCharacterClass(){
		return qcc;
	}

	public String getName(){
		return name;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public void spawn(){
		Villager villager = Var.WORLD.spawn(loc, Villager.class);
		villager.setCustomName(ChatColor.DARK_GREEN + name);
		villager.setSilent(true);
		villager.setInvulnerable(true);
		villager.setCustomNameVisible(true);
		villager.setBreed(false);
		villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000*20, 255, true));
		villager.teleport(loc);
		Logger.log(LogLevel.DEBUG, villager.getName() + "" + villager.getLocation().getBlockX() + "" + villager.getLocation().getBlockZ());
	}
	
	public void talk(UPlayer player){
		boolean success = true;
		
		try {
			Logger.log(LogLevel.DEBUG, this.getName() + "  " + this.getQuestCharacterClass());
			Method method = qcc.getClass().getMethod("talk", UPlayer.class);
			method.invoke(qcc.getClass().newInstance(), player);
			Logger.log(LogLevel.INFO, "NPC", player.getName() + " talked to " + name);
		} catch (NoSuchMethodException 
				| SecurityException 
				| IllegalAccessException 
				| IllegalArgumentException 
				| InvocationTargetException 
				| InstantiationException e) {
			e.printStackTrace();
			success = false;
		}
		
		if (!success){
			player.sendMessage(Message.Complicated.Quests.npcNotFound(name));
		}
	}
	
	public static QuestCharacter fromName(String text) throws NPCNotFoundException {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		
		for (QuestCharacter npc : QuestCharacter.values()) {
			if (text.contains(npc.name)) {
				return npc;
			}
		}
		
		throw new NPCNotFoundException();
		
	}
	
	@Deprecated
	public static List<String> getAllNames(){
		List<String> list = new ArrayList<String>();
		for (QuestCharacter npc : QuestCharacter.values()){
			list.add(npc.getName());
		}
		return list;
	}
	
}
