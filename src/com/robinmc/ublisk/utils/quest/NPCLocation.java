package com.robinmc.ublisk.utils.quest;

import org.bukkit.Location;

import com.robinmc.ublisk.utils.variable.Var;

public class NPCLocation {
	
	private double x;
	private int y;
	private double z;
	private int pitch;
	private int yaw;
	
	public NPCLocation(double x, int y, double z, int pitch, int yaw){
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
	
	public Location getBukkitLocation(){
		return new Location(Var.WORLD, 
				getX(), 
				getY(), 
				getZ(), 
				getPitch(), 
				getYaw());
	}
	
	

}
