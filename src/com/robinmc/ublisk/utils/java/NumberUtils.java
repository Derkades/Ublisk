package com.robinmc.ublisk.utils.java;

import java.util.concurrent.ThreadLocalRandom;

@Deprecated
public class NumberUtils {
	
	public static int randomInteger(int min, int max){
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

}
