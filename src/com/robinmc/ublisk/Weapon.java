package com.robinmc.ublisk;

public enum Weapon {
	
	OldWoodenSword("askidchnsa"),
	OldStoneSword("soaidcmsa"),
	OldIronSword("asdicnascd");
	
	private String cmd;
	
	Weapon(String cmd){
		this.cmd = cmd;
	}
	
	public String getCmd(){
		return cmd;
	}
	
	public void setCmd(String cmd){
		this.cmd = cmd;
	}

}
