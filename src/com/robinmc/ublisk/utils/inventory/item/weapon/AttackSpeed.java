package com.robinmc.ublisk.utils.inventory.item.weapon;

public enum AttackSpeed {
	
	VERY_SLOW(0.5),
	SLOW(1.0),
	MEDIUM(2.0),
	FAST(3.0),
	VERY_FAST(4.0);
	
	private double speed;
	
	AttackSpeed(double speed){
		this.speed = speed;
	}
	
	public double getSpeed(){
		return speed;
	}

}
