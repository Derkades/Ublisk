package com.robinmc.ublisk;

import java.io.File;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Console;
import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

public enum Music {
	
	COMPTINE_DUN_AUTRE_ETE("Introduction", "ComptineDunAutreEte.nbs"),
	GLAENOR("Glaenor", "Glaenor.nbs"),
	RHOCUS("Rhocus", "Rhocus.nbs");
	
	private String town;
	private String path;
	
	Music(String town, String path){
		this.path = path;
		this.town = town;
	}
	
	public String getPath(){
		return path;
	}
	
	public String getTown(){
		return town;
	}
	
	public static void playSong(Player player, String town){
		Music song = fromString(town);
		Song s = NBSDecoder.parse(new File(Main.getInstance().getDataFolder(), song.getPath()));
		
		Console.sendMessage("[Music] Playing song to " + player.getName());
		
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
