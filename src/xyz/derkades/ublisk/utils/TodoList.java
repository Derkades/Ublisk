package xyz.derkades.ublisk.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
	
	private static String ip;
	private static int port;
	private static String database;
	private static String user;
	private static String password;
	
	public static void initialize(String ip, int port, String database, String user, String password){
		TodoList.ip = ip;
		TodoList.port = port;
		TodoList.database = database;
		TodoList.user = user;
		TodoList.password = password;
	}

	private static Connection getConnection() throws SQLException {
		Properties properties = new Properties();
		properties.setProperty("user", user);
		properties.setProperty("password", password);
		properties.setProperty("useSSL", "false");
		return DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database, properties);
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
			if (result != null) result.close();
			if (select != null) select.close();
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
			if (insert != null) insert.close();
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
			if (delete != null) delete.close();
		}
	}

}
