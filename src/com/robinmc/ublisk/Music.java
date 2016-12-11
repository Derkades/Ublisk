package com.robinmc.ublisk;

import java.io.File;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

public enum Music {
	
	COMPTINE_DUN_AUTRE_ETE("Introduction", "ComptineDunAutreEte.nbs"),
	GLAENOR("Glaenor", "Glaenor.nbs"),
	RHOCUS("Rhocus", "Rhocus.nbs");
	
	private String town;
	private String filename;
	
	Music(String town, String filename){
		this.filename = filename;
		this.town = town;
	}
	
	public File getFile(){
		return new File(Main.getInstance().getDataFolder() + "\\music", filename);
	}
	
	public String getTown(){
		return town;
	}
	
	public static void playSong(Player player, String town){
		Music song = fromString(town);
		Song s = NBSDecoder.parse(song.getFile());
		
		Logger.log(LogLevel.INFO, "Music", "Playing " + song + " for town with name " + song.getTown() + " with filename " + song.getFile() + " to " + player.getName());
		
		SongPlayer sp = new RadioSongPlayer(s);
		sp.setAutoDestroy(true);
		sp.addPlayer(player);
		sp.setPlaying(true);
	}
	
	public static Music fromString(String text) {
		for (Music music: Music.values()) {
			if (text.equalsIgnoreCase(music.town)) {
				return music;
			}
		}
		return null;
	}

}
