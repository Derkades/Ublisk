package com.robinmc.ublisk.abilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Material;

import com.robinmc.ublisk.abilities.AbilityTrigger.TriggerType;
import com.robinmc.ublisk.abilities.swordsman.TestAbility;
import com.robinmc.ublisk.utils.UPlayer;

public enum Ability {
	
	TEST(new TestAbility(), new AbilityTrigger(Material.WOOD_SWORD, TriggerType.RIGHT_CLICK));
	
	private AbilityExecutor exec;
	private AbilityTrigger trigger;
	
	Ability(AbilityExecutor exec, AbilityTrigger trigger){
		this.exec = exec;
		this.trigger = trigger;
	}
	
	private AbilityExecutor getExecutor(){
		return exec;
	}
	
	public AbilityTrigger getTrigger(){
		return trigger;
	}
	
	public void doAbility(UPlayer player){
		Class<?> clazz = getExecutor().getClass();
		
		try {
			
			Method method = clazz.getMethod("doAbility", UPlayer.class);
			method.invoke(clazz.newInstance(), player);
			
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException |
				IllegalArgumentException | 
				InvocationTargetException | 
				InstantiationException e) {
			e.printStackTrace();
		}
		
	}

}
