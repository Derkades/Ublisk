package xyz.derkades.ublisk.mob;

import java.util.Arrays;
import java.util.List;

import xyz.derkades.ublisk.money.MoneyItem;

public enum GoldDrop {
	
	LEVEL1(new MobDrop(MoneyItem.DUST.getItem(), 50)
			),
	LEVEL2(new MobDrop(MoneyItem.DUST.getItem(), 0, 3)
			),
	LEVEL3(new MobDrop(MoneyItem.DUST.getItem(), 0, 5),
			new MobDrop(MoneyItem.DUST.getItem(), 0, 3)
			);
	
	private MobDrop[] drops;
	
	GoldDrop(MobDrop... drops){
		this.drops = drops;
	}
	
	public List<MobDrop> getMobDrops(){
		return Arrays.asList(drops);
	}

}
