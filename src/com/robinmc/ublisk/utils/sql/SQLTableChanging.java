package com.robinmc.ublisk.utils.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.robinmc.ublisk.utils.exception.ConnectionClosedException;

@Deprecated
public class SQLTableChanging {
	
	public static PreparedStatement prepareStatement(String sql) throws ConnectionClosedException, SQLException {
		return MySQL.connection.prepareStatement(sql);
	}

}
