package com.robinmc.ublisk;

import java.io.File;

import org.bukkit.Location;

import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

public enum Town {
	
	//(x < ..., x > ..., z < ..., z > ...
	//x < 100 x > 22 z < -10 z > 90
	INTRODUCTION("Introduction", "ComptineDunAutreEte.nbs", 100, 22, -10, -90, 69, 67, 5),
	GLAENOR("Glaenor", "Glaenor.nbs", 190, 100, 17, -120, 116, 68, -86),
	RHOCUS("Rhocus", "Rhocus.nbs", 200, 100, 400, 240, 174, 82, 313),
	NO_NAME("NoName", null, 645, 516, 60, -70, 604, 74, -41); //TODO Name for sand city
	
	private String name;
	private String musicFile;
	
	private int rangex;
	private int rangex2;
	private int rangez;
	private int rangez2;
	
	private int x;
	private int y;
	private int z;
	
	Town(String name, String musicFile, int rangex, int rangex2, int rangez, int rangez2, int x, int y, int z){
		this.name = name;
		this.musicFile = musicFile;
		
		this.rangex = rangex;
		this.rangex2 = rangex2;
		this.rangez = rangez;
		this.rangez2 = rangez2;
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String getName(){
		return name;
	}
	
	public String getSongFileName(){
		return musicFile;
	}
	
	public int lessX(){
		return rangex;
	}
	
	public int moreX(){
		return rangex2;
	}
	
	public int lessZ(){
		return rangez;
	}
	
	public int moreZ(){
		return rangez2;
	}
	
	public Location getSpawnLocation(){
		return new Location(Var.WORLD, x, y, z);
	}
	
	public static Town fromString(String text) throws IllegalArgumentException {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		
		for (Town town: Town.values()) {
			if (text.equalsIgnoreCase(town.name)) {
				return town;
			}
		}
		
		throw new IllegalArgumentException();
		
	}
	
	public void playThemeToPlayer(UPlayer player){
		Song song = NBSDecoder.parse(new File(Main.getInstance().getDataFolder() + "\\music", this.getSongFileName()));
		
		Logger.log(LogLevel.INFO, "Music", "Playing " + this.getSongFileName() + " for town with name " + this.getName() + " to " + player.getName());
		
		SongPlayer songPlayer = new RadioSongPlayer(song);
		songPlayer.setAutoDestroy(true);
		songPlayer.addPlayer(player.getPlayer());
		songPlayer.setPlaying(true);
	}
	
}
