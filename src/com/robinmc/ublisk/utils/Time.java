package com.robinmc.ublisk.utils;

import com.robinmc.ublisk.utils.variable.Var;

	public class Time {
	
	public static boolean isDay() {
	    long time = Var.world().getTime();	 
	    if(time > 0 && time < 12300) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	public static void set(long time){
		Var.world().setTime(time);
	}
	
	public static void add(long time){
		long oldtime = get();
		long newtime = oldtime + time;
		Var.world().setTime(newtime);
	}
	
	public static long get(){
		return Var.world().getTime();
	}

}
