package com.robinmc.ublisk;

import org.bukkit.Location;

public enum Town {
	
	//(x < ..., x > ..., z < ..., z > ...
	//x < 100 x > 22 z < -10 z > 90
	INTRODUCTION("Introduction", 100, 22, -10, -90, 69, 67, 5),
	GLAENOR("Glaenor", 190, 100, 17, -120, 116, 68, -86),
	RHOCUS("Rhocus", 200, 100, 400, 240, 122, 82, 288),
	NO_NAME("NoName", 645, 516, 60, -70, 604, 74, -41); //TODO Name for sand city
	
	private String name;
	
	private int rangex;
	private int rangex2;
	private int rangez;
	private int rangez2;
	
	private int x;
	private int y;
	private int z;
	
	Town(String name, int rangex, int rangex2, int rangez, int rangez2, int x, int y, int z){
		this.name = name;
		this.rangex = rangex;
		this.rangex2 = rangex2;
		this.rangez = rangez;
		this.rangez2 = rangez2;
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String getName(){
		return name;
	}
	
	public int lessX(){
		return rangex;
	}
	
	public int moreX(){
		return rangex2;
	}
	
	public int lessZ(){
		return rangez;
	}
	
	public int moreZ(){
		return rangez2;
	}
	
	public Location getSpawnLocation(){
		return new Location(Var.WORLD, x, y, z);
	}
	
	public static Town fromString(String text) throws IllegalArgumentException {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		
		for (Town town: Town.values()) {
			if (text.equalsIgnoreCase(town.name)) {
				return town;
			}
		}
		
		throw new IllegalArgumentException();
		
	}
	
}
