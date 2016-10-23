package com.robinmc.ublisk.quest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.quest.npc.Alvin;
import com.robinmc.ublisk.quest.npc.Arzhur;
import com.robinmc.ublisk.quest.npc.David;
import com.robinmc.ublisk.quest.npc.Dianh;
import com.robinmc.ublisk.quest.npc.Merek;
import com.robinmc.ublisk.quest.npc.Rasmus;
import com.robinmc.ublisk.quest.npc.TestNPC;
import com.robinmc.ublisk.quest.npc.Ulric;
import com.robinmc.ublisk.quest.npc.Zoltar;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.NPCNotFoundException;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

import net.md_5.bungee.api.ChatColor;

public enum QuestCharacter {
	
	TEST_NPC(new TestNPC(), "TestNPC", null, false),
	
	DAVID(new David(), "David", Profession.FARMER, false, 72.5, 67, -2.5, 13, 0),
	MEREK(new Merek(), "Merek", Profession.FARMER, false, 33, 67, -38, -70, 0),
	ULRIC(new Ulric(), "Ulric", Profession.FARMER, false, 38.5, 67, -26.5, -145, 0),
	ARZHUR(new Arzhur(), "Arzhur", null, false, 111.5, 68, -103.5, -20, 0), // TODO Profession
	//ASHER(null, "Asher", 449.3, 70, -10.5, 75, 5), // TODO Profession TODO QCC
	RASMUS(new Rasmus(), "Rasmus", null, false), // TODO Rasmus coordinates XXX Profession
	DIANH(new Dianh(), "Dianh", null, false),// TODO Dianh coordinates XXX Profession
	ZOLTAR(new Zoltar(), "Zoltar", null, false),// TODO Zoltar coordinates XXX Profession
	ALVIN(new Alvin(), "Alvin", null, true, 121.5, 72, 7.3, -161, 4); // XXX Profession
	
	private final QuestCharacterClass qcc;
	private final String name;
	private final Profession profession;
	private final boolean canWalk;
	private final Location loc;
	
	QuestCharacter(QuestCharacterClass qcc, String name, Profession profession, boolean canWalk, double x, double y, double z, int pitch, int yaw){
		this.qcc = qcc;
		this.name = name;
		this.profession = profession;
		this.canWalk = canWalk;
		this.loc = new Location(Var.WORLD, x, y, z, pitch, yaw);
	}
	
	QuestCharacter(QuestCharacterClass qcc, String name, Profession profession, boolean canWalk){
		this.qcc = qcc;
		this.name = name;
		this.profession = profession;
		this.canWalk = canWalk;
		this.loc = new Location(Var.WORLD, 2.5, 71, -16.5, 0, 0);
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
	
	public Profession getProfession(){
		return profession;
	}
	
	public boolean canWalk(){
		return canWalk;
	}
	
	public void spawn(){
		Villager villager = Var.WORLD.spawn(loc, Villager.class);
		villager.setCustomName(ChatColor.DARK_GREEN + name);
		villager.setSilent(true);
		villager.setInvulnerable(true);
		villager.setCustomNameVisible(true);
		villager.setBreed(false);
		if (!canWalk()) villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000*20, 255, true));
		villager.teleport(loc);
		if (getProfession() != null){
			villager.setProfession(getProfession());
		}
		Logger.log(LogLevel.DEBUG, "NPC", "Spawned " + getName() + " at (" + loc.getX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")");
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
	
	public static void respawnAll(){
		for (Villager villager : Var.WORLD.getEntitiesByClass(Villager.class))
			villager.remove();

		for (QuestCharacter npc : QuestCharacter.values())
			npc.spawn();
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
