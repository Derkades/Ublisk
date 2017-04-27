package com.robinmc.ublisk.mob;

@Deprecated
public class MobArea {

	private Radius[] radius;
	
	public MobArea(Radius... radius) {
		this.radius = radius;
	}
	
	public Radius[] getRadius(){
		return radius;
	}

}
