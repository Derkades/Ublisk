package com.robinmc.ublisk.utils;

public class Area {
	
	private int lessX;
	private int moreX;
	private int lessZ;
	private int moreZ;
	
	public Area (int lessX, int moreX, int lessZ, int moreZ){
		this.lessX = lessX;
		this.moreX = moreX;
		this.lessZ = lessZ;
		this.moreZ = moreZ;
	}
	
	public int lessX(){
		return lessX;
	}
	
	public int moreX(){
		return moreX;
	}
	
	public int lessZ(){
		return lessZ;
	}
	
	public int moreZ(){
		return moreZ;
	}

}
