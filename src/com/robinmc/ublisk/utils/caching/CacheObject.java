package com.robinmc.ublisk.utils.caching;

public class CacheObject {
	
	public String identifier;
	public Object object;
	public long timeout;
	public long timeCreated;
	
	protected CacheObject(String identifier, Object object, long timeout){
		this.identifier = identifier;
		this.object = object;
		this.timeout = timeout * 1000;
		this.timeCreated = System.currentTimeMillis();
	}

}
