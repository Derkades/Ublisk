package com.robinmc.ublisk.utils.shapes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

@Deprecated
public class Circle implements Shape {
	
//	private List<Point> getPoints(double radius) {
//		List<Point> points = new ArrayList<Point>();
//		
//	    double x, y;
//
//	    for (x = -1.0; x <= 1.0; x += 0.2) {
//	        y = Math.sqrt(radius - Math.pow(x,2)) ;
//	        points.add(new Point(x, y));
//	    }
//	    
//	    return points;
//	}
	
//	private List<Point> getPoints(int points, double radius, double center1, double center2){
//		double points = 50;
//		double radius = 2;
//		double centerX = player.getLocation().getX();
//		double centerY = player.getLocation().getY() + radius;
//		
//	    double slice = 2 * Math.PI / points;
//		for (int i = 0; i < points; i++) {
//			double angle = slice * i;
//			double newX = centerX + radius * Math.cos(angle);
//			double newY = centerY + radius * Math.sin(angle);
//	}
	
	public Location[] getCoords(double originX, double originY, double originZ, double radius, Direction direction) {
		List<Location> locationList = new ArrayList<>();
		
//		List<Point> points = getPoints(radius);
//		if (direction == Direction.Z){
//			for (Point point : points){
//				double x = originX + point.getOne();
//				double y = originY + point.getTwo();
//				double z = originZ;
//				Location location = new Location(Var.WORLD, x, y, z);
//				locationList.add(location);
//			}
//		} else if (direction == Direction.X){
//			for (Point point : points){
//				double x = originX;
//				double y = originY + point.getTwo();
//				double z = originZ + point.getOne();
//				Location location = new Location(Var.WORLD, x, y, z);
//				locationList.add(location);
//			}
//		} else if (direction == Direction.HORIZONTAL){
//			for (Point point : points){
//				double x = originX + point.getOne();
//				double y = originY;
//				double z = originZ + point.getTwo();
//				Location location = new Location(Var.WORLD, x, y, z);
//				locationList.add(location);
//			}
//		} else {
//			throw new UnsupportedOperationException("This direction is not supported: " + direction);
//		}
		
	    double x, y;

	    for (x = -1.0; x <= 1.0; x += 0.2) {
	        y = Math.sqrt(radius - Math.pow(x, 2));
	        
	        Point point = new Point(x, y);
	        Location location = point.getLocation(direction, originX, originY, originZ);
	        locationList.add(location);
	    }
		
		return locationList.toArray(new Location[]{});
	}

}
