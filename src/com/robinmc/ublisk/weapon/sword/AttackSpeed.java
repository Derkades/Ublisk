package com.robinmc.ublisk.weapon.sword;

public enum AttackSpeed {
	
	VANILLA("Slow", -1),
	FASTER("Medium", 1.0),	
	FASTEST("Fast", 3);
	
	private String name;
	private double speed;
	
	AttackSpeed(String name, double speed){
		this.speed = speed;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public double getValue(){
		return speed;
	}

}
