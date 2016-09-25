package com.robinmc.ublisk.utils.mob;

import java.util.Arrays;
import java.util.List;

import com.robinmc.ublisk.money.MoneyItem;

public enum GoldDrop {
	
	LEVEL1(
			new MobDrop(MoneyItem.DUST.getItem(), 50)
			),
	LEVEL2(
			new MobDrop(MoneyItem.DUST.getItem(), 1, 3, 90)
			);
	
	private MobDrop[] drops;
	
	GoldDrop(MobDrop... drops){
		this.drops = drops;
	}
	
	public List<MobDrop> getMobDrops(){
		return Arrays.asList(drops);
	}

}
