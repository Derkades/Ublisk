package com.robinmc.ublisk.utils;

import com.robinmc.ublisk.Var;

public class Time {
	
	public static boolean isDay() {
	    long time = Var.WORLD.getTime();	 
	    if(time > 0 && time < 12300) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	public static void set(long time){
		Var.WORLD.setTime(time);
	}
	
	public static void add(long time){
		long oldtime = get();
		long newtime = oldtime + time;
		Var.WORLD.setTime(newtime);
	}
	
	public static long get(){
		return Var.WORLD.getTime();
	}

}
