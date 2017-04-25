package com.robinmc.ublisk.ext.com.xxmicloxx.noteblockapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PositionSongPlayer extends SongPlayer {

    private Location targetLocation;
    private int distance = 16;

    public PositionSongPlayer(Song song) {
        super(song);
    }

    public Location getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    @Override
    public void playTick(Player p, int tick) {
        if (!p.getWorld().getName().equals(targetLocation.getWorld().getName())) {
            // not in same world
            return;
        }
        byte playerVolume = NoteBlockPlayerMain.getPlayerVolume(p);

        for (Layer l : song.getLayerHashMap().values()) {
            Note note = l.getNote(tick);
            if (note == null) {
                continue;
            }
            p.playSound(targetLocation,
                    Instrument.getInstrument(note.getInstrument()),
                    ((l.getVolume() * (int) volume * (int) playerVolume) / 1000000f) * ((1f/16f) * distance),
                    NotePitch.getPitch(note.getKey() - 33));
            if (isPlayerInRange(p)){
            	if (!this.playerList.get(p.getName())){
            		playerList.put(p.getName(), true);
            		PlayerRangeStateChangeEvent event = new PlayerRangeStateChangeEvent(this, p, true);
            		Bukkit.getPluginManager().callEvent(event);
            	}
            } else {
            	if (this.playerList.get(p.getName())){
            		playerList.put(p.getName(), false);
            		PlayerRangeStateChangeEvent event = new PlayerRangeStateChangeEvent(this, p, false);
            		Bukkit.getPluginManager().callEvent(event);
            	}
            }
        }
    }
    
    /**
     * Sets distance in blocks where would be player able to hear sound. 
     * @param distance (Default 16 blocks)
     */
    public void setDistance(int distance){
    	this.distance = distance;
    }
    
    public int getDistance(){
    	return distance;
    }
    
    public boolean isPlayerInRange(Player p){
    	if (p.getLocation().distance(targetLocation) > distance){
    		return false;
    	} else {
    		return true;
    	}
    }
}
