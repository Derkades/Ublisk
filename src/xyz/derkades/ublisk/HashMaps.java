package xyz.derkades.ublisk;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;

public class HashMaps {
	
	@Deprecated
	public static final Map<UUID, Boolean> ENTITY_RIGHT_CLICK_COOLDOWN = new HashMap<>();
	@Deprecated
	public static final Map<UUID, Boolean> COOLDOWN_CLASS = new HashMap<>();
	
	public static final Map<UUID, Boolean> IS_MUTED = new HashMap<>();
	public static final Map<UUID, Boolean> IS_SOFT_MUTED = new HashMap<>();

	
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
		
		PlayerInfo.resetHashMaps(player);
	}
	
}
