package xyz.derkades.ublisk.utils;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

	public static String getStringFromLocation(Location location) {
		StringBuilder builder = new StringBuilder();

		builder.append(location.getWorld().getName()).append('!');
		builder.append(round(location.getX())).append('!');
		builder.append(round(location.getY())).append('!');
		builder.append(round(location.getZ())).append('!');
		builder.append(round(location.getYaw())).append('!');
		builder.append(round(location.getPitch()));

		return builder.toString();
	}

	public static Location getLocationFromString(String string) {
		String[] split = string.split("!");

		World world = Bukkit.getWorld(split[0]);
		double x = Double.parseDouble(split[1]);
		double y = Double.parseDouble(split[2]);
		double z = Double.parseDouble(split[3]);
		float yaw = 0.0f;
		float pitch = 0.0f;
		if (split.length == 6) {
			yaw = Float.parseFloat(split[4]);
			pitch = Float.parseFloat(split[5]);
		}
		return new Location(world, x, y, z, yaw, pitch);
	}
	
	private static double round(double d){
		DecimalFormat decimalFormat = new DecimalFormat("##.00");
		return Double.parseDouble(decimalFormat.format(d));
	}

}
