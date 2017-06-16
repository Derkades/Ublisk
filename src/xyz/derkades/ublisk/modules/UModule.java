package xyz.derkades.ublisk.modules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;

public abstract class UModule implements Listener {
	
	public static final UModule[] ALL_MODULES = {
			new Advancements(),
			new AFK(),
			new AutoRestart(),
			new CustomHealth(),
			new CustomMOTD(),
			new CustomXP(),
			new DiscordBot(),
			new FriendsBossBar(),
			new GitHubModule(),
			new FormatChat(),
			new PlayerFreeze(),
			new PlayerLoginRoom(),
			new PlayerLogins(),
			new RandomTip(),
			new ResetDoors(),
			new ResourcePack(),
			new Scoreboard(),
			new TPS(),
			new VoteRestart(),
			new Voting(),
			//new WorldEditCUI(),
			};
	
	private static final List<UModule> RUNNING_MODULES = new ArrayList<UModule>();
	
	public boolean isRunning(){
		return RUNNING_MODULES.contains(this);
	}
	
	public void initialize(){
		if (isRunning()){
			throw new UnsupportedOperationException("Module is already initialized and running.");
		}
		
		RUNNING_MODULES.add(this);
		
		onEnable();
		
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
		
		Logger.log(LogLevel.DEBUG, "Initialized module " + this.getClass().getSimpleName());
	}
	
	public void terminate(){
		if (!isRunning()){
			throw new UnsupportedOperationException("Cannot terminate a module that is not running.");
		}
		
		RUNNING_MODULES.remove(this);
		
		onDisable();
	}
	
	void log(UModule module, LogLevel level, String message) {
		Logger.log(level, module.getClass().getSimpleName(), message);
	}
	
	void registerCommand(String command, CommandExecutor executor){
		Main.getInstance().getCommand(command).setExecutor(executor);
	}
	
	void onEnable(){}
	
	void onDisable(){}

}
