package com.robinmc.ublisk.utils;

import com.robinmc.ublisk.DataFile;

public class UUIDUtils {

	public static void save(UPlayer player) {
		DataFile.UUID.getConfig().set("uuid." + player.getName(), player.getUniqueId().toString());
		DataFile.UUID.getConfig().set("name." + player.getUniqueId(), player.getName());

		//syncWithDatabase(player);
	}

	/*private static void syncWithDatabase(UPlayer player) {
		try {
			if (containsPlayer(player)) {
				updateInDatabase(player);
			} else {
				insertIntoDatabase(player);
			}
		} catch (SQLException e) {
			Logger.log(LogLevel.SEVERE, "An error occured while syncing player UUID/NAME with database.");
			e.printStackTrace();
		}
	}

	private static boolean containsPlayer(UPlayer player) throws SQLException {
		boolean containsPlayer = false;

		Connection connection = null;
		PreparedStatement check = null;
		ResultSet result = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("UUID/NAME (contains)");
			check = connection.prepareStatement("SELECT * FROM `uuid` WHERE name=?;");
			check.setString(1, player.getName());
			result = check.executeQuery();
			containsPlayer = result.next();
		} catch (SQLException e) {
			throw e;
		} finally {
			check.close();
			result.close();

			connection.close();
		}

		return containsPlayer;
	}

	private static void updateInDatabase(UPlayer player) throws SQLException {
		Connection connection = null;
		PreparedStatement update = null;

		try {
			connection = Ublisk.getNewDatabaseConnection("UUID/NAME update");

			update = connection.prepareStatement("UPDATE `uuid` SET uuid=? WHERE name=?;");

			update.setString(2, player.getName());
			update.setString(1, player.getUniqueId().toString());

			update.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			update.close();
			connection.close();
		}
	}

	private static void insertIntoDatabase(UPlayer player) throws SQLException {
		Connection connection = null;
		PreparedStatement insert = null;

		try {
			connection = Ublisk.getNewDatabaseConnection("UUID/NAME insert");

			insert = connection.prepareStatement("INSERT INTO `uuid` values(?,?);");

			insert.setString(2, player.getUniqueId().toString());
			insert.setString(1, player.getName());

			insert.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			insert.close();
			connection.close();
		}

	}*/

}
