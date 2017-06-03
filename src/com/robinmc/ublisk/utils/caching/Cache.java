package com.robinmc.ublisk.utils.caching;

import java.util.ArrayList;
import java.util.List;

public class Cache {
	
	public static final List<CacheObject> CACHE_OBJECT_LIST = new ArrayList<>();
	
	/**
	 * @param identifier
	 * @param object
	 * @param timeout In seconds
	 */
	public static void addCachedObject(String identifier, Object object, long timeout){
		removeCachedObject(identifier); //Remove existing cached object
		
		if (timeout < 0){
			timeout = Long.MAX_VALUE;
		}
		
		CacheObject cachedObject = new CacheObject(identifier, object, timeout);
		CACHE_OBJECT_LIST.add(cachedObject);
	}
	
	public static Object getCachedObject(String identifier){
		for (CacheObject cacheObject : CACHE_OBJECT_LIST){
			//Remove any old cached objects
			if (System.currentTimeMillis() - cacheObject.timeCreated > cacheObject.timeout){
				CACHE_OBJECT_LIST.remove(cacheObject);
				continue;
			}
			
			if (cacheObject.identifier.equals(identifier)){
				return cacheObject.object;
			}
		}
		
		return null;
	}
	
	public static void removeCachedObject(String identifier){
		for (CacheObject cacheObject : CACHE_OBJECT_LIST){
			if (System.currentTimeMillis() - cacheObject.timeCreated > cacheObject.timeout ||
					cacheObject.identifier.equals(identifier)){
				CACHE_OBJECT_LIST.remove(cacheObject);
				continue;
			}
		}
	}

}
