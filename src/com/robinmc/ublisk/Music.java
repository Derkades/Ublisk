package com.robinmc.ublisk;

import java.io.File;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Console;
import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

public class Music {
	
	public static void play(Player player, Songs song){
		if (song == Songs.BOUNCY_BALLS){
			Song s = NBSDecoder.parse(new File(Main.getInstance().getDataFolder(), "BouncyBalls.nbs"));
			startSong(player, s);
		} else if (song == Songs.COMPTINE_DUN_AUTRE_ETE){
			Song s = NBSDecoder.parse(new File(Main.getInstance().getDataFolder(), "ComptineDunAutreEte.nbs"));
			startSong(player, s);
		} else if (song == Songs.KIND_OF_MAGIC){
			Song s = NBSDecoder.parse(new File(Main.getInstance().getDataFolder(), "KindOfMagic.nbs"));
			startSong(player, s);
		}	
	}

	private static void startSong(Player player, Song s){
		Console.sendMessage("[Music] Playing song to " + player.getName());
		SongPlayer sp = new RadioSongPlayer(s);
		sp.setAutoDestroy(true);
		sp.addPlayer(player);
		sp.setPlaying(true);
	}
}
