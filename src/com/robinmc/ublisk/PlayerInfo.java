package com.robinmc.ublisk;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.NotInGuildException;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.sql.MySQL;

public enum PlayerInfo {

	XP("exp", new UpdateCode(){ /* TEXT uuid, INT xp count, TEXT name */

		@Override
		public void executeUpdate(UPlayer player, String uuid, String name, boolean containsPlayer, String table)
				throws SQLException {
			
			if (containsPlayer){
        		int xp = player.getXP();
        		PreparedStatement update = MySQL.prepareStatement("UPDATE `" + table + "` SET count=?,name=? WHERE uuid=?;");
        		
        		update.setInt(1, xp + 1);
        		update.setString(2, name);
        		update.setString(3, uuid);
        		
        		update.executeUpdate();

        		update.close();
        	} else {
        		PreparedStatement newplayer = MySQL.prepareStatement("INSERT INTO `" + table + "` values(?, 0, ?);");
        		newplayer.setString(1, uuid);
        		newplayer.setString(2, name);
        		newplayer.execute();
        		newplayer.close();
        	}
		}
		
	}),
	
	GUILD("playerguild", new UpdateCode(){ /* TEXT uuid, TEXT guild name, TEXT name */

		@Override
		public void executeUpdate(UPlayer player, String uuid, String name, boolean containsPlayer, String table)
				throws SQLException {
			
			String guildName;
			try {
				guildName = player.getGuild().getName();
			} catch (NotInGuildException e) {
				guildName = "Not in a guild";
			}
			
        	if (containsPlayer){
        		PreparedStatement update = MySQL.prepareStatement("UPDATE `" + table + "` SET guild=?,name=? WHERE uuid=?;");
        		
        		update.setString(1, guildName);
        		update.setString(2, player.getName());
        		update.setString(3, player.getUniqueId().toString());
        		
        		update.executeUpdate();
        		update.close();
        	} else {
        		PreparedStatement newplayer = MySQL.prepareStatement("INSERT INTO `" + table + "` values(?, ?, ?);");
        		newplayer.setString(1, player.getUniqueId().toString());
        		newplayer.setString(2, guildName);
        		newplayer.setString(3, player.getName());
        		newplayer.execute();
        		newplayer.close();
        	}
		}
		
	}),
	
	RANK("rank", new UpdateCode(){ /* TEXT uuid, TEXT rank name, TEXT name */

		@Override
		public void executeUpdate(UPlayer player, String uuid, String name, boolean containsPlayer, String table)
				throws SQLException {
			
			String rank = player.getGroup().getName();
			
        	if (containsPlayer){
        		PreparedStatement update = MySQL.prepareStatement("UPDATE `" + table + "` SET rank=?,name=? WHERE uuid=?;");
        		
        		update.setString(1, rank);
        		update.setString(2, player.getName());
        		update.setString(3, player.getUniqueId().toString());
        		
        		update.executeUpdate();
        		update.close();
        	} else {
        		PreparedStatement newplayer = MySQL.prepareStatement("INSERT INTO `" + table + "` values(?, ?, ?);");
        		newplayer.setString(1, player.getUniqueId().toString());
        		newplayer.setString(2, rank);
        		newplayer.setString(3, player.getName());
        		newplayer.execute();
        		newplayer.close();
        	}
		}
		
	}),
	
	LAST_SEEN("last_seen", new UpdateCode(){ /* TEXT uuid, INT level, TEXT name */

		@Override
		public void executeUpdate(UPlayer player, String uuid, String name, boolean containsPlayer, String table)
				throws SQLException {
			
			String date = player.getLastSeenDate();
			
        	if (containsPlayer){
        		PreparedStatement update = MySQL.prepareStatement("UPDATE `" + table + "` SET date=?,name=? WHERE uuid=?;");
        		
        		update.setString(1, date);
        		update.setString(2, player.getName());
        		update.setString(3, player.getUniqueId().toString());
        		
        		update.executeUpdate();
        		update.close();
        	} else {
        		PreparedStatement insert = MySQL.prepareStatement("INSERT INTO `" + table + "` values(?, ?, ?);");
        		insert.setString(1, player.getUniqueId().toString());
        		insert.setString(2, date);
        		insert.setString(3, player.getName());
        		insert.execute();
        		insert.close();
        	}
		}
		
	}),
	
	LEVEL("level", new UpdateCode(){ /* TEXT uuid, INT level, TEXT name */

		@Override
		public void executeUpdate(UPlayer player, String uuid, String name, boolean containsPlayer, String table)
				throws SQLException {
			
			int level = player.getLevel();
			
        	if (containsPlayer){
        		PreparedStatement update = MySQL.prepareStatement("UPDATE `" + table + "` SET level=?,name=? WHERE uuid=?;");
        		
        		update.setInt(1, level);
        		update.setString(2, player.getName());
        		update.setString(3, player.getUniqueId().toString());
        		
        		update.executeUpdate();
        		update.close();
        	} else {
        		PreparedStatement insert = MySQL.prepareStatement("INSERT INTO `" + table + "` values(?, ?, ?);");
        		insert.setString(1, player.getUniqueId().toString());
        		insert.setInt(2, level);
        		insert.setString(3, player.getName());
        		insert.execute();
        		insert.close();
        	}
		}
		
	}),
	
	LAST_TOWN("town", new UpdateCode(){ /* TEXT uuid, TEXT town, TEXT name */

		@Override
		public void executeUpdate(UPlayer player, String uuid, String name, boolean containsPlayer, String table)
				throws SQLException {
			
			Town town = player.getLastTown();
			
        	if (containsPlayer){
        		PreparedStatement update = MySQL.prepareStatement("UPDATE `" + table + "` SET town=?,name=? WHERE uuid=?;");
        		
        		update.setString(1, town.getName());
        		update.setString(2, player.getName());
        		update.setString(3, player.getUniqueId().toString());
        		
        		update.executeUpdate();
        		update.close();
        	} else {
        		PreparedStatement insert = MySQL.prepareStatement("INSERT INTO `" + table + "` values(?, ?, ?);");
        		insert.setString(1, player.getUniqueId().toString());
        		insert.setString(2, town.getName());
        		insert.setString(3, player.getName());
        		insert.execute();
        		insert.close();
        	}
		}
		
	});
	
	private String table;
	private UpdateCode code;
	
	PlayerInfo(String table, UpdateCode code) {
		this.table = table;
		this.code = code;
	}
	
	public void syncWithDatabase(UPlayer player){
		String uuid = player.getUniqueId().toString();
		String name = player.getName();
		
		try {
			MySQL.openConnection();
			
			Logger.log(LogLevel.INFO, "PlayerInfo", "Updating " + table + " in database for player " + player.getName());
    		
    		PreparedStatement sql = MySQL.prepareStatement("SELECT * FROM `" + table + "` WHERE uuid=?;");
			sql.setString(1, uuid);
			ResultSet resultSet = sql.executeQuery();
			boolean containsPlayer = resultSet.next();
			
			sql.close();
			resultSet.close();
			
			code.executeUpdate(player, uuid, name, containsPlayer, table);
        	
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			try {
				MySQL.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private interface UpdateCode {
		
		public void executeUpdate(UPlayer player, String uuid, String name, boolean containsPlayer, String table) throws SQLException;
		
	}

}
