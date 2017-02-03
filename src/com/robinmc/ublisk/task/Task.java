package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Scoreboard;
import com.robinmc.ublisk.database.AddTrackersInfoToQueue;
import com.robinmc.ublisk.utils.DataFile.SaveFiles;
import com.robinmc.ublisk.utils.Lag;
import com.robinmc.ublisk.utils.sql.ProcessQueue;

public enum Task {
	
	AFK_TIMER(new AfkTimer(), 30*20, 1*20, false),
	CHECK_SHIELD(new CheckShield(), 5*20, 5*20, false),
	CHECK_TOWN(new CheckTown(), 0, 2*20, false),
	CLEAR_WEATHER(new ClearWeather(), 60*20, 5*60*20, false),
	FAST_DAY_NIGHT(new FastNight(), 0, 1, false),
	LIFE_CRYSTAL_INVENTORY(new LifeCrystalInventory(), 5*20, 5*20, false),
	PLAYER_LEVEL_UP(new PlayerLevelUp(), 0, 5*20, false),
	RANDOM_TIP(new RandomTips(), 30*20, 5*60*20, false),
	REFRESH_LAST_SEEN_DATE(new RefreshLastSeenDate(), 0, 5*20, false),
	REGENERATE_HUNGER(new RegenerateHunger(), 0, 10, false),
	REMOVE_MOBS(new RemoveMobs(), 5*60*20, 15*60*20, false),
	RESPAWN_NPC(new RespawnNPC(), 5*20, 5*60*20, false),
	RESTART_ERROR_MESSAGE(new RestartErrorMessage(), 10*20, 10*20, false),
	SET_MAX_HEALTH(new SetMaxHealth(), 5*20, 5*20, false),
	SPAWN_RANDOM_LOOT(new SpawnRandomLoot(), 5*60*20, 5*60*20, false),
	UPDATE_BACKPACK_NAME(new UpdateBackpackName(), 0, 5*20, false),
	UPDATE_INFO(new AddTrackersInfoToQueue(), 10*20, 5*60*20, false),
	UPDATE_SIDEBAR(new Scoreboard(), 5*20, 2*20, false),
	TPS_UPDATE(new Lag(), 100, 1, false),
	PROCESS_QUEUE(new ProcessQueue(), 10*20, 4*20, false),
	
	SAVE_FILES(new SaveFiles(), 30*20, 60*50, false);
	
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
	}

}
