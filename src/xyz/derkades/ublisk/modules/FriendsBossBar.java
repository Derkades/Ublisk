package xyz.derkades.ublisk.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.URunnable;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.settings.Setting;

public class FriendsBossBar extends UModule {
	
	private static final long UPDATE_TIME = 5;
	
	private static final Map<UUID, List<FriendsBar>> FRIENDS_BARS = new HashMap<>();
	
	@Override
	public void onEnable(){
		//new CleanupHashMap().runTimer(60*20, 60*20);
		new UpdateProgress().runTimer(UPDATE_TIME);
		new AddBars().runTimer(5*20, 5*20);
	}
	
	private static class UpdateProgress extends URunnable {
		
		@Override
		public void run(){
			for (UPlayer player : Ublisk.getOnlinePlayers()){
				
				if (!player.getSetting(Setting.FRIENDS_SHOW_HEALTH)){
					//Remove bar if this player has turned off health
					if (FRIENDS_BARS.containsKey(player.getUniqueId())){
						FRIENDS_BARS.remove(player.getUniqueId());
					}
					continue;
				}
				
				List<FriendsBar> barsList = FRIENDS_BARS.get(player.getUniqueId());
				
				if (barsList == null){
					continue; //Bars haven't been added for this player (yet)
				}
				
				List<FriendsBar> barsToRemove = new ArrayList<>();
				
				for (FriendsBar bar : barsList){
					OfflinePlayer offlineFriend = Bukkit.getOfflinePlayer(bar.friendUUID);
					
					if (!offlineFriend.isOnline()){
						barsToRemove.add(bar); //Friend is not online -> remove bar
						continue;
					}
					
					UPlayer friend = new UPlayer(offlineFriend);
					
					boolean isStillFriend = false;
					for (OfflinePlayer friendFromFriendsList : player.getFriends()){
						if (friend.getUniqueId().equals(friendFromFriendsList.getUniqueId())){
							isStillFriend = true;
						}
					}
					
					if (!isStillFriend){
						barsToRemove.add(bar);
						continue;
					}
					
					bar.bar.setProgress((float) friend.getHealth() / friend.getMaxHealth());
				}
				
				for (FriendsBar bar : barsToRemove){
					bar.bar.removeAll();
					barsList.remove(bar);
				}
				
				FRIENDS_BARS.put(player.getUniqueId(), barsList);
			}
		}
		
	}
	
	private static class AddBars extends URunnable {
		
		@Override
		public void run(){
			for (UPlayer player : Ublisk.getOnlinePlayers()){
				if (!player.getSetting(Setting.FRIENDS_SHOW_HEALTH)){
					continue;
				}
				
				if (FRIENDS_BARS.containsKey(player.getUniqueId())){
					continue;
				}
				
				List<FriendsBar> barList = new ArrayList<>();
				for (UPlayer friend : player.getOnlineFriends()){
					BossBar bar = Ublisk.createBossBar(friend.getName() + "'s health", BarColor.RED, BarStyle.SEGMENTED_20);
					bar.removeAll();
					bar.addPlayer(player.getPlayer());
					bar.setProgress(friend.getHealth() / (float) friend.getMaxHealth());
					barList.add(new FriendsBar(friend.getUniqueId(), bar));
				}
				FRIENDS_BARS.put(player.getUniqueId(), barList);
			}
		}

	}
	
	public static void resetBars(UPlayer player){
		FRIENDS_BARS.remove(player.getUniqueId());
	}
	
	/*private static class CleanupHashMap extends URunnable {
		
		@Override
		public void run(){
			for (UUID uuid : FRIENDS_BARS.keySet()){
				OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
				if (!player.isOnline()){
					FRIENDS_BARS.remove(uuid);
				}
			}
		}
		
	}*/
	
	private static class FriendsBar {
		
		UUID friendUUID;
		BossBar bar;
		
		FriendsBar(UUID friendUUID, BossBar bar){
			this.friendUUID = friendUUID;
			this.bar = bar;
		}
		
	}

}
