package xyz.derkades.ublisk.quest;

import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.quest.npc.Alvin;
import xyz.derkades.ublisk.quest.npc.Arzhur;
import xyz.derkades.ublisk.quest.npc.Asher;
import xyz.derkades.ublisk.quest.npc.David;
import xyz.derkades.ublisk.quest.npc.Dianh;
import xyz.derkades.ublisk.quest.npc.Jerrijn;
import xyz.derkades.ublisk.quest.npc.Merek;
import xyz.derkades.ublisk.quest.npc.Rasmus;
import xyz.derkades.ublisk.quest.npc.Roy;
import xyz.derkades.ublisk.quest.npc.TestNPC;
import xyz.derkades.ublisk.quest.npc.Ulric;
import xyz.derkades.ublisk.quest.npc.Zoltar;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Logger.LogLevel;

public abstract class NPC {
	
	private static final NPC[] NPC_LIST = new NPC[]{
			new TestNPC(),
			
			new David(),
			new Merek(),
			new Ulric(),
			new Arzhur(),
			new Asher(),
			new Rasmus(),
			new Dianh(),
			new Zoltar(),
			new Alvin(),
			
			new Jerrijn(),
			new Roy(),
	};
	
	public abstract String getName();
	
	public abstract Location getLocation();
	
	public abstract Profession getProfession();
	
	public abstract boolean canWalk();
	
	public boolean isEnabled(){
		return true;
	}

	public abstract void talk(UPlayer player);
	
	public void spawn(){
		if (!isEnabled()) throw new UnsupportedOperationException("Cannot spawn a disabled NPC.");
		
		Location location = this.getLocation();
		if (location == null)
			location = new Location(Var.WORLD, 2.5, 71, -16.5, 0, 0);
		
		Villager villager = Var.WORLD.spawn(location, Villager.class);
		villager.setCustomName(ChatColor.DARK_GREEN + this.getName());
		villager.setSilent(true);
		villager.setInvulnerable(true);
		villager.setCustomNameVisible(true);
		villager.setBreed(false);
		if (!canWalk()) 
			villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000*20, 255, true));
		if (getProfession() != null)
			villager.setProfession(getProfession());
		Logger.log(LogLevel.DEBUG, "NPC", "Spawned " + this.getName() + " at (" + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + ")");
	}
	
	public static NPC fromName(String text){
		if (text == null) {
			throw new IllegalArgumentException();
		}
		
		for (NPC npc : NPC.getAllNPCs()) {
			if (text.contains(npc.getName())) {
				return npc;
			}
		}
		
		return null;
		
	}
	
	public static void respawnAll(){
		for (Villager villager : Var.WORLD.getEntitiesByClass(Villager.class))
			villager.remove();

		for (NPC npc : NPC.getAllNPCs()){
			if (npc.isEnabled()) npc.spawn();
		}
	}
	
	public static NPC[] getAllNPCs(){
		return NPC_LIST;
	}

}
