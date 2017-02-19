package com.robinmc.ublisk.utils.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListUtils {
	
	public static boolean stringListContainsString(List<String> list, String string){
		boolean contains = false;
		for (String entry : list){
			if (entry.contains(string)){
				contains = true;
			}
		}
		return contains;
	}
	
	public static List<String> getStringListFromResultSet(ResultSet resultSet, String column) throws SQLException {
		List<String> list = new ArrayList<String>();
		
		while (resultSet.next()){
			list.add(resultSet.getString(column));
		}
		
		return list;
	}
	
	public static <T> T getRandomValueFromList(List<T> list){
		int size = list.size();
		int index = Random.getRandomInteger(0, size - 1); //Size -1 because if the list has 1 entry (entry 0) the length is 1.
		return list.get(index);s
	}

}
