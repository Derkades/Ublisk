package com.robinmc.ublisk.utils.chat;

public class JSON {
	
	private String string;
	
	public JSON(String string){
		this.string = string;
	}
	
	public String getString(){
		return string;
	}
	
	public static JSON fromString(String string){
		return new JSON(string);
	}

}
