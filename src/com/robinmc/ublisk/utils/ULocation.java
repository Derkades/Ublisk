package com.robinmc.ublisk.utils;

import org.bukkit.Location;

import com.robinmc.ublisk.utils.variable.Var;

public class ULocation {
	
	private double x;
	private int y;
	private double z;
	private int pitch;
	private int yaw;
	
	public ULocation(double x, int y, double z, int pitch, int yaw){
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	public double getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public double getZ(){
		return z;
	}
	
	public int getPitch(){
		return pitch;
	}
	
	public int getYaw(){
		return yaw;
	}
	
	public Location get(){
		return new Location(
				Var.WORLD, 
				getX(), 
				getY(), 
				getZ(), 
				getPitch(), 
				getYaw());
	}
	
	

}
