package xyz.derkades.ublisk.utils.caching;

public class CacheObject {
	
	public Object object;
	public long timeout;
	public long timeCreated;
	
	protected CacheObject(Object object, long timeout){
		this.object = object;
		this.timeout = timeout * 1000;
		this.timeCreated = System.currentTimeMillis();
	}

}
