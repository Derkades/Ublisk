package xyz.derkades.ublisk.utils;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.utils.Logger.LogLevel;

public class PacketListener {
	
	public static boolean RUNNING;

	public static void listenForPacket(final int port, final int packetLength, final PacketRecievedListener runnable) {
		if (Var.DEBUG){
			Logger.log(LogLevel.WARNING, "PacketListener", "Packet listener on port " + port + " has not been started, because debug mode is enabled.");
			return;
		}
		
		new BukkitRunnable() {

			public void run() {
				try (final DatagramSocket serverSocket = new DatagramSocket(port)) {
					byte[] receiveData = new byte[packetLength];

					Logger.log(LogLevel.INFO, "PacketListener", "Started listening on " + port);
					final DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

					while (RUNNING) {
						serverSocket.receive(receivePacket);
						final String string = new String(receivePacket.getData(), 0, receivePacket.getLength());
						runnable.onPacketRecieved(string);
					}
					
					Logger.log(LogLevel.INFO, "PacketListener", "Shutting down listener on port " + port);
					serverSocket.close();
					this.cancel();
					return;
				} catch (BindException e) {
					if (e.getMessage().contains("in use")) {
						Logger.log(LogLevel.WARNING,
								"Could not start listener on port " + port + ", because this port is already in use.");
						Ublisk.RESTART_ERROR = true;
					} else {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(Main.getInstance());
	}

	public static interface PacketRecievedListener {

		public void onPacketRecieved(String message);

	}

}
