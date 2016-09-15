package com.robinmc.ublisk.utils.inventory.item.weapon;

public class WeaponInfo {
	
	private double damage;
	private double speed;
	private double attackSpeed;
	private double knockback;
	
	/**
	 * Provides info about a weapon
	 * @param damage Damage dealt by attacks, in half-hearts.
	 * <br><br>
	 * Default: 2.0
	 * <br>
	 * Minimum: 0.0
	 * <br>
	 * Maximum: --
	 * <br><br>
	 * @param speed Speed of movement.
	 * <br><br>
	 * Default: 0.7
	 * <br>
	 * Minimum: 0.0
	 * <br>
	 * Maximum: --
	 * <br><br>
	 * @param attackSpeed Determines speed at which attack strength recharges. Value is the number of full-strength attacks per second.
	 * <br><br>
	 * Default: 4.0
	 * <br>
	 * Minimum: 0.0
	 * <br>
	 * Maximum: 1024.0
	 * <br><br>
	 * @param knockback The chance to resist knockback from attacks, explosions, and projectiles. 1.0 is 100% chance for resistance.
	 * <br><br>
	 * Default: 0.0
	 * <br>
	 * Minimum: 0.0
	 * <br>
	 * Maximum: 1.0
	 * 
	 */
	public WeaponInfo(double damage, double speed, double attackSpeed, double knockback){
		this.damage = damage;
		if (speed == 0){
			speed = 0.7;
		}
		this.speed = speed;
		this.attackSpeed = attackSpeed;
		this.knockback = knockback;
	}
	
	public double getDamage(){
		return damage;
	}
	
	public double getMovementSpeed(){
		return speed;
	}
	
	public double getAttackSpeed(){
		return attackSpeed;
	}
	
	public double getKnockbackResistance(){
		return knockback;
	}

}
