package com.robinmc.ublisk;

public enum Perms {
	
	COMMAND_LOG("ublisk.commandlog"),
	DEBUG_COMMAND("ublisk.commands.debug");
	
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
