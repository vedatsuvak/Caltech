
package com.gms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gms.dbUtil.DbUtil;
import com.gms.pojo.Participants;

public class ParticipantsDAO {

    // Insert
    public int insertParticipant(Participants participant) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "INSERT INTO participants (pname, age, gender, phone, email, bid) VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, participant.getPname().toUpperCase());
            ps.setInt(2, participant.getAge());
            ps.setString(3, participant.getGender());
            ps.setString(4, participant.getPhone());
            ps.setString(5, participant.getEmail().toLowerCase());
            ps.setInt(6, participant.getBid());           
            result = ps.executeUpdate();
            System.out.println("Participant added to Batch" + participant.getBid());       
        } finally {
            DbUtil.closeConnection(con, ps);
        }
        return result;
    }

    // Retrieve all participants
    public List<Participants> displayParticipants() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Participants> list = new ArrayList<>();
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "SELECT * FROM participants ORDER BY bid";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println("Participants listed forr all Batches");
            while (rs.next()) {
                Participants participant = new Participants();
                participant.setPid(rs.getInt("pid"));
                participant.setPname(rs.getString("pname"));
                participant.setAge(rs.getInt("age"));
                participant.setGender(rs.getString("gender"));
                participant.setPhone(rs.getString("phone"));
                participant.setEmail(rs.getString("email"));
                participant.setBid(rs.getInt("bid"));
                list.add(participant);      
            }
        } finally {
            DbUtil.closeConnection(con, ps, rs);
        }
        return list;
    }

    // Retrieve Specific Batch's participants
    public List<Participants> BatchParticipants(int bid) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Participants> list = new ArrayList<>();
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "SELECT * FROM participants WHERE bid = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, bid);
            rs = ps.executeQuery();
            System.out.println("Participat from Batch: "+ bid +" listed");
            while (rs.next()) {
                Participants participant = new Participants();
                participant.setPid(rs.getInt("pid"));
                participant.setPname(rs.getString("pname"));
                participant.setAge(rs.getInt("age"));
                participant.setGender(rs.getString("gender"));
                participant.setPhone(rs.getString("phone"));
                participant.setEmail(rs.getString("email"));
                participant.setBid(rs.getInt("bid"));
                list.add(participant); 
            }
        } finally {
            DbUtil.closeConnection(con, ps, rs);
        }
        return list;
    }
    
    //Override Retrieve Specific Participant's Batches
    public List<Participants> BatchParticipants(String username) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Participants> list = new ArrayList<>();
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "SELECT * FROM participants WHERE pname = ? ORDER BY bid";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            System.out.println("Batch for "+ username +" listed");
            while (rs.next()) {
                Participants participant = new Participants();
                participant.setPid(rs.getInt("pid"));
                participant.setPname(rs.getString("pname"));
                participant.setAge(rs.getInt("age"));
                participant.setGender(rs.getString("gender"));
                participant.setPhone(rs.getString("phone"));
                participant.setEmail(rs.getString("email"));
                participant.setBid(rs.getInt("bid"));
                list.add(participant); 
            }
        } finally {
            DbUtil.closeConnection(con, ps, rs);
        }
        return list;
    }
    
    // Retrieve a specific participant
    public Participants getParticipant(int pid) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Participants participant = null;
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "SELECT * FROM participants WHERE pid = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, pid);
            rs = ps.executeQuery();
            if (rs.next()) {
                participant = new Participants();
                participant.setPid(rs.getInt("pid"));
                participant.setPname(rs.getString("pname"));
                participant.setAge(rs.getInt("age"));
                participant.setGender(rs.getString("gender"));
                participant.setPhone(rs.getString("phone"));
                participant.setEmail(rs.getString("email"));
                participant.setBid(rs.getInt("bid"));
                System.out.println("Participat "+ pid +" listed");
            }
        } finally {
            DbUtil.closeConnection(con, ps, rs);
        }
        return participant;
    }
    
    //Override Retrieve a specific participant to check if registered
    public Participants getParticipant(String pname, int bid) throws ClassNotFoundException, SQLException {
    	Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Participants participant = null;
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "SELECT * FROM participants WHERE pname = ? AND bid = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, pname);
            ps.setInt(2, bid);
            rs = ps.executeQuery();
            if (rs.next()) {
                participant = new Participants();
                participant.setPid(rs.getInt("pid"));
                participant.setPname(rs.getString("pname"));
                participant.setAge(rs.getInt("age"));
                participant.setGender(rs.getString("gender"));
                participant.setPhone(rs.getString("phone"));
                participant.setEmail(rs.getString("email"));
                participant.setBid(rs.getInt("bid"));
                System.out.println("Participat found !");
            }
        } finally {
        	DbUtil.closeConnection(con, ps, rs);
        }
        return participant;
    }

    // Update
    public int updateParticipant(Participants participant) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "UPDATE participants SET pname = ?, age = ?, gender = ?, phone = ?, email = ?, bid = ? WHERE pid = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, participant.getPname().toUpperCase());
            ps.setInt(2, participant.getAge());
            ps.setString(3, participant.getGender());
            ps.setString(4, participant.getPhone());
            ps.setString(5, participant.getEmail().toLowerCase());
            ps.setInt(6, participant.getBid());
            ps.setInt(7, participant.getPid());
            result = ps.executeUpdate();
            System.out.println("Participat updated");
        } finally {
            DbUtil.closeConnection(con, ps);
        }
        return result;
    }
    
    // Override Update participant when username and email updated
    public int updateParticipant(String username, String newusername, String newemail) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "UPDATE participants SET pname = ?, email = ? WHERE pname = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, newusername.toUpperCase());
            ps.setString(2, newemail.toLowerCase());
            ps.setString(3, username.toUpperCase());
            result = ps.executeUpdate();
            System.out.println("Participat updated: " + newusername.toUpperCase());
        } finally {
            DbUtil.closeConnection(con, ps);
        }
        return result;
    }
    
    // unregister from a Batch
    public int unregisterParticipant(String username, int bid) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "DELETE FROM participants WHERE pname = ? AND bid = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, bid);
            result = ps.executeUpdate();
            System.out.println("Participat unregistered");
        } finally {
            DbUtil.closeConnection(con, ps);
        }
        return result;
    }

    // Delete
    public int deleteParticipant(int pid) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "DELETE FROM participants WHERE pid = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, pid);
            result = ps.executeUpdate();
            System.out.println("Participat "+ pid +" deleted");
        } finally {
            DbUtil.closeConnection(con, ps);
        }
        return result;
    }
    
    // override Delete participant when user deleted
    public int deleteParticipant(String username) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "DELETE FROM participants WHERE pname = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            result = ps.executeUpdate();
            System.out.println("Participat "+ username.toUpperCase() +" deleted from all Batches");
        } finally {
            DbUtil.closeConnection(con, ps);
        }
        return result;
    }
    
    //Send SMS
    public String sendSMS(String pname){
    	System.out.println("SMS sent to: " + pname);
		return pname;
    }
    
    //Send Email
    public String sendEmail(String pname){
    	System.out.println("Email sent to: " + pname);
		return pname;
    }
}
