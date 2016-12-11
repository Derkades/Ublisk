package com.robinmc.ublisk.abilities;

import org.bukkit.Material;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.abilities.AbilityTrigger.TriggerType;
import com.robinmc.ublisk.abilities.swordsman.Meteorite;
import com.robinmc.ublisk.abilities.swordsman.TestAbility;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.NotEnoughManaException;

public enum Ability {
	
	TEST(4, new TestAbility(), new AbilityTrigger(Material.WOOD_SWORD, TriggerType.RIGHT_CLICK)),
	TEST2(10, new Meteorite(), new AbilityTrigger(Material.GOLD_SWORD, TriggerType.RIGHT_CLICK));
	
	private AbilityExecutor exec;
	private AbilityTrigger trigger;
	private int mana;
	
	Ability(int mana, AbilityExecutor exec, AbilityTrigger trigger){
		this.exec = exec;
		this.trigger = trigger;
		this.mana = mana;
	}
	
	public AbilityTrigger getTrigger(){
		return trigger;
	}
	
	public void doAbility(UPlayer player){
		try {
			player.removeMana(mana);
		} catch (NotEnoughManaException e) {
			player.sendMessage(Message.ABILITY_NOT_ENOUGH_MANA);
			return;
		}
		
		exec.doAbility(player);
	}

}
