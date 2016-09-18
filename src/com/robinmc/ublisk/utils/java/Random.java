package com.robinmc.ublisk.utils.java;

public class Random {
	
	public static int getRandomInteger(int min, int max){
		return NumberUtils.randomInteger(min, max);
	}
	
	public static boolean getRandomBoolean(){
		int i = getRandomInteger(0, 1);
		if (i == 0){
			return false;
		} else {
			return true;
		}
	}
	

}
