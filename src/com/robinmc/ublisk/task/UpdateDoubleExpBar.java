package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.scheduler.Task;
import com.robinmc.ublisk.utils.variable.Var;

import net.md_5.bungee.api.chat.TextComponent;

public class UpdateDoubleExpBar implements Task {

	@Override
	public void task(final Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				try {
					if (HashMaps.doublexp.get(HashMaps.placeHolder())){
						int left = HashMaps.doubleExpTime.get(HashMaps.placeHolder());
						for (final Player player : Bukkit.getOnlinePlayers()){
							if (!HashMaps.doubleExpBarActive.get(HashMaps.placeHolder())){
								HashMaps.doubleExpBarActive.put(HashMaps.placeHolder(), true);
								@SuppressWarnings("unused")
								BossBar bossBar = BossBarAPI.addBar(player, new TextComponent("Double XP for 1 minute"), BossBarAPI.Color.GREEN, BossBarAPI.Style.PROGRESS, 1.0f, 60*20, 1);
							}
						}
						
						int left2 = left - 1;
						if (left2 == 0){
							left2 = Var.doubleExpTime;
							HashMaps.doublexp.put(HashMaps.placeHolder(), false);
							HashMaps.doubleExpBarActive.put(HashMaps.placeHolder(), false);
						}
						
						HashMaps.doubleExpTime.put(HashMaps.placeHolder(), left2);
					}
				} catch (NullPointerException e){
					HashMaps.doublexp.put("hi", false);
				}
			}
		}, 1*20, 1*20);
	}

}
