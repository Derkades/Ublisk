package com.robinmc.ublisk.utils.caching;

public class CacheObject {
	
	protected String identifier;
	protected Object object;
	protected long timeout;
	protected long timeCreated;
	
	protected CacheObject(String identifier, Object object, long timeout){
		this.identifier = identifier;
		this.object = object;
		this.timeout = timeout * 1000;
		this.timeCreated = System.currentTimeMillis();
	}

}
