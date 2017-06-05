package com.robinmc.ublisk.modules;

import java.util.ArrayList;
import java.util.List;

import com.robinmc.ublisk.utils.URunnable;
import com.robinmc.ublisk.utils.java.NumberUtils;

public class TPS extends UModule {
	
	private static final List<Double> TPS_AVERAGE_LIST = new ArrayList<>();
	
	@Override
	public void onEnable(){
		new TPSMeasureTask().runTimer(100, 1);
		new TPSAverageTask().runTimer(5*20, 10*20);
	}
	
	public static double getCurrentTPS(){
		return TPSMeasureTask.getTPS();
	}
	
	public static double getAverageTPS(){
		return NumberUtils.calculateAverage(TPS_AVERAGE_LIST);
	}
	
	private static class TPSAverageTask extends URunnable {

		@Override
		public void run() {
			TPS_AVERAGE_LIST.add(TPSMeasureTask.getTPS());
			if (TPS_AVERAGE_LIST.size() > 25){
				TPS_AVERAGE_LIST.remove(0);
			}
		}
		
	}
	
	private static class TPSMeasureTask extends URunnable {
		
		public static int tickCount = 0;
		public static long[] tick = new long[600];

		public static double getTPS() {
			return getTPS(100);
		}

		public static double getTPS(int ticks) {
			if (tickCount < ticks) {
				return 20.0D;
			}

			int target = (tickCount - 1 - ticks) % tick.length;
			long elapsed = System.currentTimeMillis() - tick[target];

			return ticks / (elapsed / 1000.0D);
		}

		@Override
		public void run() {
			tick[(tickCount % tick.length)] = System.currentTimeMillis();
			tickCount += 1;
		}
		
	}

}
