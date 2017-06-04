package com.robinmc.ublisk.task;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.DataFile.SaveFiles;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.database.AddTrackersInfoToQueue;
import com.robinmc.ublisk.database.ProcessQueue;

public enum Task {

	CHECK_TOWN(new CheckTown(), 0, 2*20, false),
	CLEAR_WEATHER(new ClearWeather(), 60*20, 5*60*20, false),
	FAST_DAY_NIGHT(new FastNight(), 0, 1, false),
	LIFE_CRYSTAL_INVENTORY(new LifeCrystalInventory(), 5*20, 5*20, false),
	REFRESH_LAST_SEEN_DATE(new RefreshLastSeenDate(), 0, 5*20, false),
	REGENERATE_HUNGER(new RegenerateHunger(), 0, 10, false),
	REMOVE_MOBS(new RemoveMobs(), 5*60*20, 15*60*20, false),
	RESPAWN_NPC(new RespawnNPC(), 5*20, 5*60*20, false),
	RESTART_ERROR_MESSAGE(new RestartErrorMessage(), 10*20, 60*20, false),
	SECONDS_AFK_STATISTIC(new SecondsAFKStatistic(), 1*20, 1*20, false),
	SPAWN_RANDOM_LOOT(new SpawnRandomLoot(), 5*60*20, 5*60*20, false),
	UPDATE_BACKPACK_NAME(new UpdateBackpackName(), 0, 5*20, false),
	UPDATE_INFO(new AddTrackersInfoToQueue(), 10*20, 30*20, false),
	
	PROCESS_QUEUE(new ProcessQueue(), 10*20, 2*20, false),
	
	SAVE_FILES(new SaveFiles(), 30*20, 60*50, false);
	
	private static final List<BukkitRunnable> RUNNING_TASKS = new ArrayList<BukkitRunnable>();
	
	private BukkitRunnable runnable;
	private long delay;
	private long period;
	private boolean async;
	
	Task(BukkitRunnable runnable, long delay, long period, boolean async){
		this.runnable = runnable;
		this.delay = delay;
		this.period = period;
		this.async = async;
	}
	
	public void start(){
		if (async){
			runnable.runTaskTimerAsynchronously(Main.getInstance(), delay, period);
		} else {
			runnable.runTaskTimer(Main.getInstance(), delay, period);
		}
		RUNNING_TASKS.add(runnable);
	}
	
	public static void stopAll(){
		for (BukkitRunnable runnable : RUNNING_TASKS){
			runnable.cancel();
		}
		RUNNING_TASKS.clear();
	}

}
