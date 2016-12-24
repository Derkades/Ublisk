package com.robinmc.ublisk.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Logger.LogLevel;

public class PacketListener {
		
	private static List<DatagramSocket> sockets = new ArrayList<>();
	
    public static void listenForPacket(final int port, final int packetLength, final PacketRecievedListener runnable) {   
    	new BukkitRunnable(){
        	public void run(){
		    	try (final DatagramSocket serverSocket = new DatagramSocket(port)){
		    		sockets.add(serverSocket);
		            byte[] receiveData = new byte[packetLength];
		
		            System.out.printf("Listening on udp:%s:%d%n",
		                    InetAddress.getLocalHost().getHostAddress(), port);     
		            final DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		
		            while(true){
						serverSocket.receive(receivePacket);
		                final String string = new String(receivePacket.getData(), 0, receivePacket.getLength());
		                runnable.onPacketRecieved(string);
		    	    }
		        } catch (IOException e){
		            e.printStackTrace();
		        }
        	}
    	}.runTaskAsynchronously(Main.getInstance());
	}
    
    public static void closeAllOpenSockets(){
    	Logger.log(LogLevel.INFO, "PacketListener", "Closing all open sockets...");
    	for (DatagramSocket socket : sockets){
    		if (!socket.isClosed())
    			socket.close();
    	}
    }
    
    public static interface PacketRecievedListener {
    	
    	public void onPacketRecieved(String message);
    	
    }

}
