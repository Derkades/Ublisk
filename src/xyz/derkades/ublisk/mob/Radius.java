package xyz.derkades.ublisk.mob;

public class Radius {

	private int x;
	private int y;
	private int z;
	private int radius;
	
	public Radius(int x, int z, int diameter){
		this.x = x;
		this.y = 200;
		this.z = z;
		this.radius = diameter / 2;
	}
	
	public Radius(int x, int y, int z, int diameter){
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = diameter / 2;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
	
	public int getRadius(){
		return radius;
	}

}
