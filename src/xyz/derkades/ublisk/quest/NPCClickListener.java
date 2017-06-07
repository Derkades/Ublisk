package xyz.derkades.ublisk.quest;

import static org.bukkit.ChatColor.RED;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import xyz.derkades.ublisk.utils.UPlayer;

public class NPCClickListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onClick(PlayerInteractEntityEvent event){
		Entity entity = event.getRightClicked();
		
		if (!(entity instanceof Villager)){
			return;
		}
		
		event.setCancelled(true);
		
		UPlayer player = new UPlayer(event);
		
		NPC npc = NPC.fromName(entity.getCustomName());
		
		if (npc == null){
			player.sendPrefixedMessage("NPC", RED + "No dialogue could be found for an npc with name " + entity.getCustomName() + RED + ", please report this error.");
			return;
		}
			
		npc.talk(player);
	}

}
