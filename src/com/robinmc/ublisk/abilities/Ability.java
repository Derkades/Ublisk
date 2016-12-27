package com.robinmc.ublisk.abilities;

import com.robinmc.ublisk.utils.UPlayer;

public abstract class Ability {
	
	private int mana;
	private int minLevel;
	
	public Ability(int mana, int minLevel){
		this.mana = mana;
		this.minLevel = minLevel;
	}
	
	public int getMana(){
		return mana;
	}
	
	public int getMinimumLevel(){
		return minLevel;
	}
	
	public abstract void run(UPlayer player);
	
	
	/*
	TEST(4, 0, new TestAbility(), new AbilityTrigger(Material.WOOD_SWORD, TriggerType.RIGHT_CLICK)),
	TEST2(10, 0, new Meteorite(), new AbilityTrigger(Material.GOLD_SWORD, TriggerType.RIGHT_CLICK));*/
	
	/*
	private int mana;
	private int minLevel;
	private AbilityExecutor exec;
	
	Ability(int mana, int minLevel, AbilityExecutor exec){
		this.mana = mana;
		this.minLevel = minLevel;
		this.exec = exec;
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
	*/

}
