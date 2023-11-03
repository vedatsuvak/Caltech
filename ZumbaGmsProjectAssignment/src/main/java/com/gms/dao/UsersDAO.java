package com.gms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gms.dbUtil.DbUtil;
import com.gms.pojo.User;

public class UsersDAO {

	// Insert a new user
	public int insertUser(String username, String password, String email) throws ClassNotFoundException, SQLException {
	    Connection con = null;
	    PreparedStatement ps = null;
	    int rowsAffected = 0;
	    try {
	        con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	        
	        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, username);
	        ps.setString(2, password);
	        ps.setString(3, email);
	        rowsAffected = ps.executeUpdate();
	        System.out.println("User registered");
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println(e);
	    } finally {
	    	// Close the resources
            DbUtil.closeConnection(con, ps);
	    }
	    return rowsAffected ;
	}

	// Retrieve users as admin
	public List<User> displayUsers() throws ClassNotFoundException, SQLException {
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<User> userList = new ArrayList<>();	    
	    try {
	        con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	        
	        String sql = "SELECT * FROM users ORDER BY username";
	        ps = con.prepareStatement(sql);
	        rs = ps.executeQuery();
	        System.out.println("Users listed");	        
	        while (rs.next()) {
	            User user = new User();
	            user.setUsername(rs.getString("username"));
	            user.setPassword(rs.getString("password"));
	            user.setEmail(rs.getString("email"));
	            userList.add(user);
	        }
	    } catch (ClassNotFoundException |SQLException e) {
	        System.out.println(e);
	    } finally {	       
	    	// Close the resources
            DbUtil.closeConnection(con, ps, rs);
	    }  
	    return userList;
	}

	// Retrieve as user
	public User displayUser(String username) throws ClassNotFoundException, SQLException {
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;    
	    User user = null;
	    try {
	        con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	        
	        String sql = "SELECT * FROM users WHERE username = ?";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, username);
	        rs = ps.executeQuery();	        
	        if (rs.next()) {
	            user = new User();
	            user.setUsername(rs.getString("username"));
	            user.setPassword(rs.getString("password"));
	            user.setEmail(rs.getString("email"));
	            System.out.println("User listed");
	            return user;
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println(e);
	   	} finally {
	    // Close the resources
        DbUtil.closeConnection(con, ps, rs);
	    }    
	    return user;
	}

	//update 
	public int editUser(User user, String oldUsername) throws ClassNotFoundException, SQLException {
	    Connection con = null;
	    PreparedStatement ps = null;
	    int rowsAffected = 0;
	    String newUsername = user.getUsername();
	    String newEmail = user.getEmail();
	    ParticipantsDAO dao = new ParticipantsDAO();
	    try {
	        con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }       
	        String sql = "UPDATE users SET username = ?, password = ?, email = ? WHERE username = ?";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, newUsername);
	        ps.setString(2, user.getPassword());
	        ps.setString(3, newEmail);
	        ps.setString(4, oldUsername);
	        rowsAffected = ps.executeUpdate();
	        dao.updateParticipant(oldUsername, newUsername, newEmail);
	        System.out.println("User edited");
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println(e);
	    } finally {
	   	// Close the resources
        DbUtil.closeConnection(con, ps);
	    }
	    return rowsAffected;
	}
	
	//delete
	public int deleteUser(String selectedUsername) throws ClassNotFoundException, SQLException {
	    Connection con = null;
	    PreparedStatement ps = null;
	    int rowsAffected = 0;
	    ParticipantsDAO dao = new ParticipantsDAO();
	    try {
	        con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }
	        String sql = "DELETE FROM users WHERE username = ?";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, selectedUsername);
	        rowsAffected = ps.executeUpdate();
	        System.out.println("User deleted");
	        dao.deleteParticipant(selectedUsername);
	        return rowsAffected;
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println(e);
	    } finally {
	    	// Close the resources
            DbUtil.closeConnection(con, ps);
	    }
	    return rowsAffected;
	}
	
	// Login
	public boolean login(String username, String password) throws ClassNotFoundException, SQLException {
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;	    
	    try {
	        con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }        
	        // Prepare the SQL statement
	        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, username);
	        ps.setString(2, password);
	        rs = ps.executeQuery();
	        System.out.println("User checked");
	        return rs.next();
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println(e);
	    } finally {
	    	// Close the resources
            DbUtil.closeConnection(con, ps, rs);
	    }
	    return false;
	}
}