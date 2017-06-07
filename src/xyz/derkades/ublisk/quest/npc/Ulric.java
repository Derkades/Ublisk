package xyz.derkades.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.quest.NPC;
import xyz.derkades.ublisk.quest.Quest;
import xyz.derkades.ublisk.quest.QuestParticipant;
import xyz.derkades.ublisk.utils.UPlayer;

public class Ulric extends NPC {
	
	@Override
	public String getName() {
		return "Ulric";
	}

	@Override
	public Location getLocation() {
		return new Location(Var.WORLD, 38.5, 67, -26.5);
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
		//UInventory inv = qp.getInventory();
		
		if (qp.inventoryContains(new ItemStack(Material.LOG, 10),
			new ItemStack(Material.STRING, 16),
			new ItemStack(Material.GOLD_NUGGET, 10))){
			
			qp.sendMessage("me do not work");
			/*if (player.getClazz() != Clazz.SWORDSMAN){
				player.sendMessage("As of right now, only swordsman is implemented. Please choose swordsman from the classes menu");
				return;
			}
			
			inv.addItem(new Item(new BasicWoodenSword().getItemStack()));
			inv.remove(Material.LOG, 10);
			inv.remove(Material.STRING, 16);
			inv.remove(Material.GOLD_NUGGET, 10);*/
		} else {
			qp.sendMessage("I can make a weapon for you if you bring me the required materials.");
		}
	}

}
