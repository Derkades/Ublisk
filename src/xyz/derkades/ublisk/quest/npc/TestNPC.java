package xyz.derkades.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;

import xyz.derkades.derkutils.bukkit.ItemBuilder;
import xyz.derkades.ublisk.quest.NPC;
import xyz.derkades.ublisk.quest.Quest;
import xyz.derkades.ublisk.quest.QuestParticipant;
import xyz.derkades.ublisk.quest.npcmenu.NPCMenu;
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
		
		/*ClickAction action = new ClickAction(){

			@Override
			public void click(ClickedOption option) {
				qp.sendMessage("TEST!");
			}
			
		};*/
		
		//Option option1 = new Option(10, Material.STONE, "Some random option", "Some lore!", "Line 2");
		//Option option2 = new Option(11, Material.WOOD, "Option without lore");
		
		//qp.openMenu(new NPCMenu("Test Menu", action, option1, option2));
		
		new NPCMenu("Test Menu", 3*9, qp){

			@Override
			public void open() {
				items.put(10, new ItemBuilder(Material.STONE).name("Some random option").lore("Some lore!", "Lines 2").create());
				items.put(11, new ItemBuilder(Material.WOOD).name("Option without lore").create());
				super.open();
			}

			@Override
			public boolean onOptionClick(OptionClickEvent event) {
				qp.sendMessage("TEST!");
				return false;
			}
			
		}.open();
	}

}
