package com.robinmc.ublisk;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.commands.Command;
import com.robinmc.ublisk.database.SyncQueue;
import com.robinmc.ublisk.listeners.Listeners;
import com.robinmc.ublisk.mob.Mob;
import com.robinmc.ublisk.modules.UModule;
import com.robinmc.ublisk.task.Task;
import com.robinmc.ublisk.utils.DoubleXP;
import com.robinmc.ublisk.utils.Guild;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.PacketListener;
import com.robinmc.ublisk.utils.TodoList;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class Main extends JavaPlugin {

	public static Main instance;

	@Override
	public void onEnable() {
		instance = this;
		
		Ublisk.RESTART_ERROR = false;
		
		PacketListener.RUNNING = true;

		HashMaps.resetAllPlayers();

		Listeners.register();

		Command.registerAll();

		Mob.startMobSpawning();

		for (Task task : Task.values())
			task.start();

		DoubleXP.startDoubleXPPacketListener();
		Logger.startSiteLogger();
		
		TodoList.initialize(DataFile.MYSQL.getConfig().getString("todo.user"), DataFile.MYSQL.getConfig().getString("todo.password"));
		
		Logger.log(LogLevel.INFO, "Guilds", "Deleting empty guilds...");
		for (Guild guild : Guild.getGuildsList()){
			if (guild.getMembers().size() == 0){
				Logger.log(LogLevel.WARNING, "Guilds", "Automatically deleted " + guild.getName() + ", because it does not have any members.");
				guild.remove();
			}
		}
		
		for (UModule module : UModule.ALL_MODULES){
			module.initialize();
		}
		
		new BukkitRunnable(){
			public void run(){
				for (UPlayer player : Ublisk.getOnlinePlayers()){
					if (player.getInventory().contains(Material.FIREWORK)){
						player.sendMessage("Ga even serieus doen :)");
					}
				}
			}
		}.runTaskTimer(this, 1*20, 10*20);

	}
	
	@Override
	public void onDisable() {
	    try {
	    	// Do some fancy trickery to execute code while the plugin is still enabled
	        Field field = this.getClass().getField("isEnabled");
	        field.setAccessible(true);
	        field.set(this, true);

	        // Call cleanup code
	        cleanup();

	        field.set(this, false);
	    } catch (Exception ex) { }
	}
	
	private void cleanup(){
		Task.stopAll();
		
		// Save data files
		for (DataFile dataFile : DataFile.values()){
			dataFile.save();
		}
		
		// Stop all running modules
		for (UModule module : UModule.ALL_MODULES){
			if (!module.isRunning()){
				Logger.log(LogLevel.WARNING, "Modules", module.getClass().getSimpleName() + " is already terminated.");
			}
			
			module.terminate();
		}
		
		// Close all open sockets
		PacketListener.RUNNING = false;
		
		// Clear remaining tasks in sync queue
		SyncQueue.clear();
		
		instance = null;
	}

	public static Main getInstance() {
		return instance;
	}

}
