package com.robinmc.ublisk.utils.guilds;

import com.robinmc.ublisk.utils.UPlayer;

public class GuildInvitation {
	
	private UPlayer player;
	private boolean invited;
	
	GuildInvitation(UPlayer player, boolean isInvited){
		this.player = player;
		invited = isInvited;
	}
	
	public UPlayer getPlayer(){
		return player;
	}
	
	public boolean getInvited(){
		return invited;
	}
	
	public void setInvited(boolean invited){
		this.invited = invited;
	}

}
