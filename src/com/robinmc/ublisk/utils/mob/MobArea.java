package com.robinmc.ublisk.utils.mob;

public class MobArea {

	private Radius[] radius;
	
	public MobArea(Radius... radius) {
		this.radius = radius;
	}
	
	public Radius[] getRadius(){
		return radius;
	}

}
