package com.robinmc.ublisk.utils;

import org.bukkit.event.Listener;

@Deprecated
public class ActionBarAPI implements Listener {
	/* TODO Cleanup this mess
	public static Plugin plugin = Main.getInstance();
	//public static boolean works = true;
	//public static String nmsver;
	//private static boolean useOldMethods = false;
  
	public void onEnable(){  
	    //nmsver = Bukkit.getServer().getClass().getPackage().getName();
	    //nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
	    //if ((nmsver.equalsIgnoreCase("v1_8_R1")) || (nmsver.equalsIgnoreCase("v1_7_"))) {
	    //  useOldMethods = true;
	    //}
	}
  
	public static void sendActionBar(Player player, String message){
		/*
		try {
			  Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
			  Object p = c1.cast(player);
	    
			Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
			Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");
			Object ppoc;
			
			if (useOldMethods){
				Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
				Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
				Method m3 = c2.getDeclaredMethod("a", new Class[] { String.class });
				Object cbc = c3.cast(m3.invoke(c2, new Object[] { "{\"text\": \"" + message + "\"}" }));
				ppoc = c4.getConstructor(new Class[] { c3, Byte.TYPE }).newInstance(new Object[] { cbc, 2 });
			} else {
				Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
				Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
				Object o = c2.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
				ppoc = c4.getConstructor(new Class[] { c3, Byte.TYPE }).newInstance(new Object[] { o, 2 });
			}
			
			Method m1 = c1.getDeclaredMethod("getHandle", new Class[0]);
			Object h = m1.invoke(p, new Object[0]);
			Field f1 = h.getClass().getDeclaredField("playerConnection");
			Object pc = f1.get(h);
			Method m5 = pc.getClass().getDeclaredMethod("sendPacket", new Class[] { c5 });
			m5.invoke(pc, new Object[] { ppoc });
		
		} catch (Exception e){
		    e.printStackTrace();
		    works = false;
		}
		*//*
		CraftPlayer player2 = (CraftPlayer) player;
		player2.getHandle().playerConnection.sendPacket(new PacketPlayOutChat(new ChatComponentText(message), (byte) 2));
		
	}
  
	public static void sendActionBar(final Player player, final String message, int duration){
  		sendActionBar(player, message);
  		if (duration >= 0){
  			new BukkitRunnable(){
  				public void run(){
  					ActionBarAPI.sendActionBar(player, "");
  				}
  			}.runTaskLater(plugin, duration + 1);
  		}
  	}
	*/
}

