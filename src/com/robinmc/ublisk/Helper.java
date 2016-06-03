package com.robinmc.ublisk;

public enum Helper {
	
	ROBINMC("RobinMC", "https://goo.gl/Aay9Ry", ""),
	CHASPYR("Chaspyr", "", "");
	
	private String ign;
	private String yt;
	private String custom;
	
	Helper(String ign, String yt, String custom){
		this.ign = ign;
		this.yt = yt;
		this.custom = custom;
	}
	
	public String getIgn(){
		if (ign == ""){
			return "N/A";
		} else {
			return ign;
		}
	}
	
	public String getYT(){
		if (yt == ""){
			return "N/A";
		} else {
			return yt;
		}		
	}
	
	public String getCustom(){
		if (custom == ""){
			return "N/A";
		} else {
			return custom;
		}
	}
	
	public static Helper fromString(String text) {
		if (text != null) {
			for (Helper helper: Helper.values()) {
				if (text.equalsIgnoreCase(helper.ign)) {
					return helper;
				}
			}
		}
		return null;
	}

}
