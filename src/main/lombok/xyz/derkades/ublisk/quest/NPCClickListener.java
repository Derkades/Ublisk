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
	public void onClick(final PlayerInteractEntityEvent event){
		final Entity entity = event.getRightClicked();

		if (!(entity instanceof Villager)){
			return;
		}

		event.setCancelled(true);

		final UPlayer player = new UPlayer(event);

		final NPC npc = NPC.fromName(entity.getCustomName());

		if (npc == null){
			player.sendPrefixedMessage("NPC", RED + "No dialogue could be found for an npc with name " + entity.getCustomName() + RED + ", please report this error.");
			return;
		}

		//npc.talk(player);

		for (final Quest quest : Quest.QUESTS) {
			for (final QuestStep step : quest.getQuestSteps()){
				if (step.getNpc().equals(npc)) { // TODO NPC may need custom equals method
					if (step.talk(player)) {
						// if talking was successful, return to prevent
						// other messages from sending
						return;
					}
				}
			}
		}
	}

}
