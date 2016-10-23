package com.robinmc.ublisk.chat;

import com.robinmc.ublisk.Var;

public enum Trigger {

	BRB("brb", "I'll be right back"),
	APPLY("apply", "I'm sorry, currently there's no way to apply for staff.");
	
	private String trigger;
	private String message;
	
	Trigger(String trigger, String message){
		this.trigger = trigger;
		this.message = message;
	}
	
	public String getTrigger(){
		return Var.TRIGGER_CHARACTER + trigger;
	}
	
	public String getMessage(){
		return message;
	}
	
}
