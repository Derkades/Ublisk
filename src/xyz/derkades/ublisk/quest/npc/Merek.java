package xyz.derkades.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;

import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.money.MoneyItem;
import xyz.derkades.ublisk.quest.NPC;
import xyz.derkades.ublisk.quest.Quest;
import xyz.derkades.ublisk.quest.QuestParticipant;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.inventory.Item;
import xyz.derkades.ublisk.utils.inventory.UInventory;	

public class Merek extends NPC {
	
	@Override
	public String getName() {
		return "Merek";
	}

	@Override
	public Location getLocation() {
		return new Location(Var.WORLD, 33, 67, -38);
	}

	@Override
	public Profession getProfession() {
		return Profession.FARMER;
	}

	@Override
	public boolean canWalk() {
		return false;
	}
	
	@Override
	public void talk(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.INTRODUCTION, this);
		UInventory inv = qp.getInventory();
		if (inv.containsItems(
				new Item(Material.LOG, 10), 
				new Item(Material.STRING, 16), 
				MoneyItem.DUST.getItem(10))){
			qp.sendMessage("Great! Now go to Ulric to craft a weapon.");
		} else if (inv.containsItems(
				new Item(Material.STRING, 16), 
				MoneyItem.DUST.getItem(10))){
			qp.sendMessage("Finally get 10 wood logs at the saw");
		} else if (inv.containsItems(
				new Item(Material.WOOL, 4), 
				MoneyItem.DUST.getItem(10))){
			qp.sendMessage("Great job, now please break down your wool into 16 string by using the windmill.");
		} else if (inv.contains(Material.GOLD_NUGGET, 10)){
			qp.sendMessage("Now bring me 4 wool by killing sheep.");
		} else {
			qp.sendMessage("What are you doing out here? You don’t even have a weapon yet! I\'ll tell you what you’ll need to make a weapon. First, get 10 gold dust by killing animals.");
		}
			
	}

}
