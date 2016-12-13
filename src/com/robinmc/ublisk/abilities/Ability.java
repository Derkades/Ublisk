package com.robinmc.ublisk.abilities;

import org.bukkit.Material;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.abilities.AbilityTrigger.TriggerType;
import com.robinmc.ublisk.abilities.swordsman.Meteorite;
import com.robinmc.ublisk.abilities.swordsman.TestAbility;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.NotEnoughManaException;

public enum Ability {
	
	TEST(4, 0, new TestAbility(), new AbilityTrigger(Material.WOOD_SWORD, TriggerType.RIGHT_CLICK)),
	TEST2(10, 0, new Meteorite(), new AbilityTrigger(Material.GOLD_SWORD, TriggerType.RIGHT_CLICK));
	
	private int mana;
	private int minLevel;
	private AbilityExecutor exec;
	private AbilityTrigger trigger;
	
	Ability(int mana, int minLevel, AbilityExecutor exec, AbilityTrigger trigger){
		this.mana = mana;
		this.minLevel = minLevel;
		this.exec = exec;
		this.trigger = trigger;
	}
	
	public AbilityTrigger getTrigger(){
		return trigger;
	}
	
	public void doAbility(UPlayer player){
		if (minLevel > player.getLevel()){
			player.sendMessage(Message.ABILITY_NOT_ENOUGH_LEVEL);
			return;
		}
		
		try {
			player.removeMana(mana);
		} catch (NotEnoughManaException e) {
			player.sendMessage(Message.ABILITY_NOT_ENOUGH_MANA);
			return;
		}
		
		exec.doAbility(player);
	}

}
