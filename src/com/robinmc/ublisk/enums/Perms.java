package com.robinmc.ublisk.enums;

@Deprecated
public enum Perms {
	
	COMMAND_LOG("ublisk.commandlog"),
	COMMAND_DEBUG("ublisk.commands.debug"),
	COMMAND_MUTE("ublisk.commands.mute");
	
	private String perm;
	
	Perms(String perm){
		this.perm = perm;
	}
	
	public String getPerm(){
		return perm;
	}
	
	public void setPerm(String perm){
		this.perm = perm;
	}

}
