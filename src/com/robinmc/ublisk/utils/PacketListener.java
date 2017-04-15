package com.robinmc.ublisk.utils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class PacketListener {
	
	//public static boolean RUNNING;

//	public static void listenForPacket(final int port, final int packetLength, final PacketRecievedListener runnable) {
//				try (final DatagramSocket serverSocket = new DatagramSocket(port)) {
//					Logger.log(LogLevel.DEBUG, "PacketListener", "Creating listenener on port " + port + "...");
//					
//					byte[] receiveData = new byte[packetLength];					
//					final DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//					
//					new BukkitRunnable(){
//						public void run(){
//							try {
//								Logger.log(LogLevel.INFO, "PacketListener", "Started listening on " + port);
//								while (RUNNING) {
//									serverSocket.receive(receivePacket);
//									final String string = new String(receivePacket.getData(), 0, receivePacket.getLength());
//									runnable.onPacketRecieved(string);
//								}
//							} catch (IOException e){
//								e.printStackTrace();
//							} finally {
//								Logger.log(LogLevel.INFO, "PacketListener", "Shutting down listener on port " + port);
//								serverSocket.close();
//								this.cancel();
//							}
//						}
//					}.runTaskLaterAsynchronously(Main.getInstance(), 2*20);
//				} catch (BindException e) {
//					if (e.getMessage().contains("in use")) {
//						Logger.log(LogLevel.WARNING,
//								"Could not start listener on port " + port + ", because this port is already in use.");
//						Ublisk.RESTART_ERROR = true;
//					} else {
//						e.printStackTrace();
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//	}
	
	private static Channel serverChannel;
	private static NioEventLoopGroup group;
	
	public static void listenForPacket(final int port, final PacketRecievedListener packetRecievedListener){
		if (group == null){
			group = new NioEventLoopGroup(1);
		}
		
		new ServerBootstrap()
        .channel(NioServerSocketChannel.class)
        .group(group)
        .childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel channel) throws Exception {
                
            }
        })
        .bind("0.0.0.0", port)
        .addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    serverChannel = future.channel();
                } else {
                    SocketAddress socketAddress = future.channel().localAddress();
                    if (socketAddress == null) {
                        socketAddress = new InetSocketAddress("0.0.0.0", port);
                    }
                }
            }
        });
	}
	
	public static void close(){
		serverChannel.close();
	}

	public static interface PacketRecievedListener {

		public void onPacketRecieved(String message);

	}

}
