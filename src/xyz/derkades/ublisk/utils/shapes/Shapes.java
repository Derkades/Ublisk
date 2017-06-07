package xyz.derkades.ublisk.utils.shapes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import xyz.derkades.ublisk.Var;

public class Shapes {
	
	public static List<Location> generateCircle(Direction direction, Location origin, int points, double radius){
		double center1;
		double center2;
		
		if (direction == Direction.HORIZONTAL){
			center1 = origin.getX();
			center2 = origin.getZ();
		} else {
			throw new UnsupportedOperationException("Unknown direction");
		}
		
		List<Location> locationList = new ArrayList<Location>();
		
	    double slice = 2 * Math.PI / points;
		for (int i = 0; i < points; i++) {
			double angle = slice * i;
			
			double point1 = center1 + radius * Math.cos(angle);
			double point2 = center2 + radius * Math.sin(angle);
			
			double x, y, z;
			if (direction == Direction.HORIZONTAL){
				x = point1;
				y = origin.getY();
				z = point2;
			} else {
				throw new UnsupportedOperationException("Unknown direction");
			}
			
			Location location = new Location(Var.WORLD, x, y, z);
			locationList.add(location);
		}
		
		return locationList;
	}

}
