package com.robinmc.ublisk;

public enum Town {
	
	//(x < ..., x > ..., z < ..., z > ...
	//x < 100 x > 22 z < -10 z > 90
	INTRODUCTION("Introduction", 100, 22, -10, -90),
	GLEANOR("Glaenor", 190, 100, 17, -120),
	RHOCUS("Rhocus", 200, 100, 400, 240);
	
	private String name;
	private int x;
	private int x2;
	private int z;
	private int z2;
	
	Town(String name, int x, int x2, int z, int z2){
		this.name = name;
		this.x = x;
		this.x2 = x2;
		this.z = z;
		this.z2 = z2;
	}
	
	public String getName(){
		return name;
	}
	
	public int lessX(){
		return x;
	}
	
	public int moreX(){
		return x2;
	}
	
	public int lessZ(){
		return z;
	}
	
	public int moreZ(){
		return z2;
	}
	
}
