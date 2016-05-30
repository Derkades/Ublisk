package com.robinmc.ublisk;

public enum Mob {
	
	CHICKEN(1, "Chicken"),
	ZOMBIFIED_MERCHANT(5, "Zombified Merchant");
	
	private Integer xp;
	private String name;
	
	Mob(Integer xp, String name){
		this.xp = xp;
		this.name = name;
	}
	
	public int getExp(){
		return xp;
	}
	
	public String getName(){
		return name;
	}
}
