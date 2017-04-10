package com.robinmc.ublisk.utils.shapes;

import org.bukkit.Location;

import com.robinmc.ublisk.Var;

@Deprecated
public class Point {
	
	private double one;
	private double two;
	
	public Point(double one, double two){
		this.one = one;
		this.two = two;
	}
	
	public double getOne(){
		return one;
	}
	
	public double getTwo(){
		return two;
	}
	
	public Location getLocation(Direction direction, Location origin) {
		if (direction == Direction.Z) {
//			double x = originX + this.getOne();
//			double y = originY + this.getTwo();
//			double z = originZ;
			//Location location = new Location(Var.WORLD, x, y, z);
			origin.setX(origin.getX() + this.getOne());
			origin.setY(origin.getY() + this.getTwo());
		} else if (direction == Direction.X) {
//			double x = originX;
//			double y = originY + this.getTwo();
//			double z = originZ + this.getOne();
//			Location location = new Location(Var.WORLD, x, y, z);
//			locationList.add(location);
			origin.setZ(origin.getZ() + this.getOne());
			origin.setY(origin.getY() + this.getTwo());
		} else if (direction == Direction.HORIZONTAL) {
//			double x = originX + this.getOne();
//			double y = originY;
//			double z = originZ + this.getTwo();
//			Location location = new Location(Var.WORLD, x, y, z);
//			locationList.add(location);
			origin.setX(origin.getX() + this.getOne());
			origin.setZ(origin.getX() + this.getTwo());
		} else {
			throw new UnsupportedOperationException("This direction is not (yet) supported: " + direction);
		}
		
		return origin;
	}
	
	public Location getLocation(Direction direction, double originX, double originY, double originZ){
		return getLocation(direction, new Location(Var.WORLD, originX, originY, originZ));
	}

}
