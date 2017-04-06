package com.robinmc.ublisk;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.database.PlayerInfo2;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class HashMaps {
	
	public static final Map<UUID, Boolean> ENTITY_RIGHT_CLICK_COOLDOWN = new HashMap<>();
	public static final Map<UUID, Boolean> COOLDOWN_CLASS = new HashMap<>();
	
	public static final Map<UUID, Boolean> IS_MUTED = new HashMap<>();
	public static final Map<UUID, Boolean> IS_SOFT_MUTED = new HashMap<>();
	
	public static final Map<Player, Player> LAST_MESSAGE_SENDER = new HashMap<>();
	
	public static final Map<UUID, Integer> PREVIOUS_LEVEL = new HashMap<>();
	
	static void resetAllPlayers(){
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			addPlayerToMaps(player);
		}
	}

	public static void addPlayerToMaps(UPlayer player){
		Logger.log(LogLevel.INFO, "HashMaps", player.getName() + "'s maps have been reset");
		UUID uuid = player.getUniqueId();
		
		ENTITY_RIGHT_CLICK_COOLDOWN.put(uuid, false);
		COOLDOWN_CLASS.put(uuid, false);
		IS_MUTED.put(uuid, false);
		IS_SOFT_MUTED.put(uuid, false);
		LAST_MESSAGE_SENDER.put(player.getPlayer(), null);
		PREVIOUS_LEVEL.put(uuid, player.getLevel());
		
		//Tracker.resetHashMaps(player);
		
		PlayerInfo2.resetHashMaps(player);
	}
	
	public static HashMap<Integer, Integer> build(int... data){
	    HashMap<Integer, Integer> result = new HashMap<>();

	    if(data.length % 2 != 0) 
	        throw new IllegalArgumentException("Odd number of arguments");      

	    int key = 0;
	    Integer step = -1;

	    for(int value : data){
	        step++;
	        switch(step % 2){
	        case 0: 
	            key = value;
	            continue;
	        case 1:             
	            result.put(key, value);
	            break;
	        }
	    }

	    return result;
	}
	
}
