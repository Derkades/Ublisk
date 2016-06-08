package com.robinmc.ublisk;

public enum Classes {
	
	SWORDSMAN("Swordsman"),
	ARCHER("Archer");
	
	private String name;
	
	Classes(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public static Classes fromString(String text) {
		for (Classes c: Classes.values()) {
			if (text.equalsIgnoreCase(c.name)) {
				return c;
			}
		}
		return null;
	}

}
