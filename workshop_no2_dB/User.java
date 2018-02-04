package workshop_no2_dB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
	private int id;
	private String username;
	private String password;
	private String email;
	
	public String toString() {
		return "ID: '" + id + "'\n" + "username: '" + username + "'\n" + "password: '" + password + "'\n" + "email: '" + email + "'";
	}
	
	public User (String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.setPassword(password);
	}
	
	public User () {}
	
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public User setUsername(String username) {
		this.username = username;
		return this;
	}
	
	public String getEmail() {
		return email;
	}
	
	public User setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword (String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preStm;
			preStm = conn.prepareStatement(sql, generatedColumns);
			preStm.setString(1, this.username);
			preStm.setString(2, this.email);
			preStm.setString(3, this.password);
			preStm.executeUpdate();
			ResultSet rs = preStm.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		}
		else {
			String sql = "UPDATE users SET username=?, email=?, password=? where id=?";
			PreparedStatement preStm;
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, this.username);
			preStm.setString(2, this.email);
			preStm.setString(3, this.password);
			preStm.setInt(4, this.id);
			preStm.executeUpdate();
		}
	}
	
	static public User loadUserById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM users where id=?";
		PreparedStatement preStm;
		preStm = conn.prepareStatement(sql);
		preStm.setInt(1, id);
		ResultSet rs = preStm.executeQuery();
		if (rs.next()) {
			User loadedUser = new User();
			loadedUser.id = rs.getInt("id");
			loadedUser.username = rs.getString("username");
			loadedUser.password = rs.getString("password");
			loadedUser.email = rs.getString("email");
			return loadedUser;
		}
		return null;
		}
	
	static public User[] loadAllUsers(Connection conn) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM users";
		PreparedStatement preStm;
		preStm = conn.prepareStatement(sql);
		ResultSet rs = preStm.executeQuery();
		while (rs.next()) {
			User loadedUser = new User();
			loadedUser.id = rs.getInt("id");
			loadedUser.username = rs.getString("username");
			loadedUser.password = rs.getString("password");
			loadedUser.email = rs.getString("email");
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id !=0) {
			String sql = "DELETE FROM users WHERE id=?";
			PreparedStatement preStm = conn.prepareStatement(sql);
			preStm.setInt(1, this.id);
			preStm.executeUpdate();
			this.id=0;
		}
	}
			
		
}
	
	

