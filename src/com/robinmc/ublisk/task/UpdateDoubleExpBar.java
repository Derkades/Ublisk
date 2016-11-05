package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.bossbar.BossBarAPI;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Exp;

import net.md_5.bungee.api.chat.TextComponent;

public class UpdateDoubleExpBar extends BukkitRunnable {

	@Override
	public void run(){
		try {
			if (Exp.DOUBLE_XP_ACTIVE){
				int left = Exp.DOUBLE_XP_TIME;
				for (final Player player : Bukkit.getOnlinePlayers()){
					if (!Exp.DOUBLE_XP_BAR_ACTIVE){
						Exp.DOUBLE_XP_BAR_ACTIVE = true;
						BossBarAPI.addBar(player, 
								new TextComponent("Double XP"), 
								BossBarAPI.Color.GREEN, 
								BossBarAPI.Style.PROGRESS, 
								1.0f, 
								60*20,
								1);
					}
				}
				
				int left2 = left - 1;
				if (left2 == 0){
					left2 = Var.DOUBLE_XP_TIME;
					Exp.DOUBLE_XP_ACTIVE = false;
					Exp.DOUBLE_XP_BAR_ACTIVE = false;
				}
				
				Exp.DOUBLE_XP_TIME = left2;
			}
		} catch (NullPointerException e){
			Exp.DOUBLE_XP_ACTIVE = false;
		}
	}

}
