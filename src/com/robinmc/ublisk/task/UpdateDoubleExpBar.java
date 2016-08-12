package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Task;
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
							//double n = ((left * (10 / 6)) / 100); //Converts a range of 0-60 to 0-1
							//Console.sendMessage(left + "");
							//Console.sendMessage((left * (10 / 6)) / 100 + "");
							//BigDecimal bd = new BigDecimal(Double.toString((left * (10 / 6)) / 100));
					        //bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
							//float progress = bd.floatValue();
							//float progress = (float) n;
							//Console.sendMessage(progress + "");
							
							//As you can see from the mess above converting a 0-60 double to 0.0-1.0 float was too hard for me. I left this here in case I want to do this properly later. Doin' it the messy way, sorry fellow coders :(
							float p = 1.0f;
							if (left > 54){
								p = 1.0f;
							} else if (left > 48){
								p = 0.9f;
							} else if (left > 42){
								p = 0.8f;
							} else if (left > 36){
								p = 0.7f;
							} else if (left > 30){
								p = 0.6f;
							} else if (left > 24){
								p = 0.5f;
							} else if (left > 18){
								p = 0.4f;
							} else if (left > 12){
								p = 0.3f;
							} else if (left > 6){
								p = 0.2f;
							} else if (left > 1){
								p = 0.1f;
							} else {
								p = 0.0f;
							}
							final BossBar bossBar = BossBarAPI.addBar(player, new TextComponent("Double XP for 1 minute"), BossBarAPI.Color.GREEN, BossBarAPI.Style.PROGRESS, p, 20, 40);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
								public void run(){
									bossBar.removePlayer(player);
								}
							}, 20);
						}
						int left2 = left - 1;
						if (left2 == 0){
							left2 = Var.doubleExpTime;
							HashMaps.doublexp.put(HashMaps.placeHolder(), false);
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
