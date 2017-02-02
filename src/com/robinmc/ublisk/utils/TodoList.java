package com.robinmc.ublisk.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoList {
	
	public static class TodoItem {

		private int id;
		private String subject;
		private String text;

		public TodoItem(int id, String subject, String text) {
			this.id = id;
			this.subject = subject;
			this.text = text;
		}

		public int getId() {
			return id;
		}

		public String getSubject() {
			return subject;
		}

		public String getText() {
			return text;
		}

	}
	
	private static String user;
	private static String password;
	
	public static void initialize(String user, String password){
		TodoList.user = user;
		TodoList.password = password;
	}

	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://192.168.0.121:3306/todo", user, password);
	}

	public static List<TodoItem> getTodoItemsList() throws SQLException {
		List<TodoItem> list = new ArrayList<TodoItem>();

		Connection connection = null;
		PreparedStatement select = null;
		ResultSet result = null;

		try {
			connection = getConnection();
			select = connection.prepareStatement("SELECT * FROM `todo`");
			result = select.executeQuery();

			while (result.next()) {
				int id = result.getInt("id");
				String subject = result.getString("subject");
				String text = result.getString("text");

				TodoItem todoItem = new TodoItem(id, subject, text);

				list.add(todoItem);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			result.close();
			select.close();

			connection.close();
		}

		return list;
	}

	public static void addTodoItem(TodoItem todoItem) throws SQLException {
		Connection connection = null;
		PreparedStatement insert = null;

		try {
			connection = getConnection();
			insert = connection.prepareStatement("INSERT INTO `todo` (subject, text) VALUES (?, ?)");
			insert.setString(1, todoItem.getSubject());
			insert.setString(2, todoItem.getText());
			insert.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			insert.close();
			connection.close();
		}
	}
	
	public static void removetodoItem(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement delete = null;
		
		try {
			connection = getConnection();
			delete =connection.prepareStatement("DELETE FROM `todo` WHERE id=?;");
			delete.setInt(1, id);
			delete.execute();
		} catch (SQLException e){
			throw e;
		} finally {
			delete.close();
			connection.close();
		}
	}

}
