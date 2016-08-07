package com.robinmc.ublisk.utils.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.robinmc.ublisk.utils.exception.ConnectionClosedException;

public class SQLTableChanging {
	
	public static PreparedStatement prepareStatement(String sql) throws ConnectionClosedException, SQLException {
		Connection con = MySQL.connection;
		
		if (con == null || con.isClosed()){
			throw new ConnectionClosedException();
		}
		
		return MySQL.connection.prepareStatement(sql);
		
	}

}
