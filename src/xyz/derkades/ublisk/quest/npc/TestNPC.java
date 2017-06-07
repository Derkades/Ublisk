package xyz.derkades.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;

import xyz.derkades.ublisk.quest.NPC;
import xyz.derkades.ublisk.quest.Quest;
import xyz.derkades.ublisk.quest.QuestParticipant;
import xyz.derkades.ublisk.quest.npcmenu.ClickAction;
import xyz.derkades.ublisk.quest.npcmenu.ClickedOption;
import xyz.derkades.ublisk.quest.npcmenu.NPCMenu;
import xyz.derkades.ublisk.quest.npcmenu.Option;
import xyz.derkades.ublisk.utils.UPlayer;

public class TestNPC extends NPC {
	
	@Override
	public String getName() {
		return "TestNPC";
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public Profession getProfession() {
		return null;
	}

	@Override
	public boolean canWalk() {
		return false;
	}
	
	@Override
	public void talk(UPlayer player) {
		final QuestParticipant qp = player.getQuestParticipant(Quest.UNKNOWN, this);
		
		ClickAction action = new ClickAction(){

			@Override
			public void click(ClickedOption option) {
				qp.sendMessage("TEST!");
			}
			
		};
		
		Option option1 = new Option(10, Material.STONE, "Some random option", "Some lore!", "Line 2");
		Option option2 = new Option(11, Material.WOOD, "Option without lore");
		
		qp.openMenu(new NPCMenu("Test Menu", action, option1, option2));
	}

}
