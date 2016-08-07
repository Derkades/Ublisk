package com.robinmc.ublisk.utils.quest;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.inventivetalent.npclib.annotation.NPC;
import org.inventivetalent.npclib.entity.living.human.EntityPlayer;
import org.inventivetalent.npclib.npc.living.human.NPCPlayer;

@NPC(id = 9001,
		type = EntityType.PLAYER,
		bukkit = Player.class,
		nms = "EntityPlayer",
		entity = CustomEntityInterface.class)
public class CustomClassNPC extends NPCPlayer {

	public CustomClassNPC(EntityPlayer npcEntity) {
		super(npcEntity);
	}

}
