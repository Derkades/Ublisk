package com.robinmc.ublisk.utils.inventory.item.weapon;

public class WeaponInfo {
	
	private int damage;
	private double speed;
	private String attackSpeedName;
	private double attackSpeed;
	private double knockback;
	
	/**
	 * Provides info about a weapon.
	 * @param damage Damage dealt by attacks, in half-hearts.
	 * <br><br>
	 * Minimum: 0.0
	 * <br>
	 * Maximum: --
	 * <br><br>
	 * @param speed Speed of movement. <b>For vanilla use -1</b>
	 * <br><br>
	 * Minimum: 0.0
	 * <br>
	 * Maximum: --
	 * <br><br>
	 * @param attackSpeed Determines speed at which attack strength recharges.
	 * <br><br>
	 * AttackSpeed.VANILLA - "Slow"
	 * <br>
	 * AttackSpeed.FASTER - "Medium"
	 * <br>
	 * AttackSpeed.FASTEST - "Fast"
	 * <br><br>
	 * @param knockback The chance to resist knockback from attacks, explosions, and projectiles. 1.0 is 100% chance for resistance. <b>For vanilla use -1</b>
	 * <br><br>
	 * Minimum: 0.0
	 * <br>
	 * Maximum: 1.0
	 * 
	 */
	public WeaponInfo(int damage, double speed, AttackSpeed attack, double knockback){
		this.damage = damage;
		this.speed = speed;
		this.attackSpeedName = attack.getName();
		this.attackSpeed = attack.getSpeed();
		this.knockback = knockback;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public double getMovementSpeed(){
		return speed;
	}
	
	public String getAttackSpeedName(){
		return attackSpeedName;
	}
	
	public double getAttackSpeedValue(){
		return attackSpeed;
	}
	
	public double getKnockbackResistance(){
		return knockback;
	}

}
