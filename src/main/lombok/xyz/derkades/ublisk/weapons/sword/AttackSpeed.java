package xyz.derkades.ublisk.weapons.sword;

public enum AttackSpeed {
	
	/**
	 * 
	 */
	SLOW("Slow", 0.1),
	
	/**
	 * NBT value of 1.0
	 */
	NORMAL("Normal", 1.0),
	
	/**
	 * NBT value of 3.0;
	 */
	FAST("Fast", 3.5);
	
	private String name;
	private double speed;
	
	AttackSpeed(String name, double speed){
		this.speed = speed;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public double getValue(){
		return speed;
	}

}
