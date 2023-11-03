
package com.gms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gms.dbUtil.DbUtil;
import com.gms.pojo.Batch;
import com.gms.pojo.Participants;

public class BatchDAO {

    // Insert
    public int insertBatch(Batch batch) throws ClassNotFoundException, SQLException {
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
            String sql = "INSERT INTO batch (bname, instructor, startTime, startDate) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            // Batch ID auto increment
            ps.setString(1, batch.getBname());
            ps.setString(2, batch.getInstructor());
            ps.setString(3, batch.getStartTime());
            ps.setString(4, batch.getStartDate());
            result = ps.executeUpdate();
            System.out.println("Batch added");       
        } finally {
            DbUtil.closeConnection(con, ps);
        }
        return result;
    }

    // Retrieve all batches
    public List<Batch> displayBatches() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Batch> list = new ArrayList<>();
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "SELECT * FROM batch ORDER BY bid";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println("Batches listed");  
            while (rs.next()) {
                Batch batch = new Batch();
                batch.setBid(rs.getInt("bid"));
                batch.setBname(rs.getString("bname"));
                batch.setInstructor(rs.getString("instructor"));
                batch.setStartTime(rs.getString("startTime"));
                batch.setStartDate(rs.getString("startDate"));
                list.add(batch);
            }
        } finally {
            DbUtil.closeConnection(con, ps, rs);
        }
        return list;
    }

    // Retrieve a specific batch
    public Batch displayBatch(int bid) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Batch batch = null;
        try {
        	con = DbUtil.getDbConn();
	        if (con != null) {
	            System.out.println("Connection with DB established");
	        } else {
	            throw new SQLException("Connection failed");
	        }	
            String sql = "SELECT * FROM batch WHERE bid = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, bid);
            rs = ps.executeQuery();
            if (rs.next()) {
                batch = new Batch();
                batch.setBid(rs.getInt("bid"));
                batch.setBname(rs.getString("bname"));
                batch.setInstructor(rs.getString("instructor"));
                batch.setStartTime(rs.getString("startTime"));
                batch.setStartDate(rs.getString("startDate"));
                System.out.println("Batch: " + bid);  
            }
        } finally {
            DbUtil.closeConnection(con, ps, rs);
        }
        return batch;
    }

    // Update
    public int editBatch(Batch batch) throws ClassNotFoundException, SQLException {
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
            String sql = "UPDATE batch SET bname = ?, instructor = ?, startTime = ?, startDate = ? WHERE bid = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, batch.getBname());
            ps.setString(2, batch.getInstructor());
            ps.setString(3, batch.getStartTime());
            ps.setString(4, batch.getStartDate());
            ps.setInt(5, batch.getBid());
            result = ps.executeUpdate();
            System.out.println("Batch edited");  
        } finally {
            DbUtil.closeConnection(con, ps);
        }
        return result;
    }

    // Delete
    public int deleteBatch(int bid) throws ClassNotFoundException, SQLException {
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
            String sql = "DELETE FROM batch WHERE bid = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, bid);
            result = ps.executeUpdate();
            System.out.println("Batch deleted");  
        } finally {
            DbUtil.closeConnection(con, ps);
        }
        return result;
    }
    
    // Start Batch
    public int startBatch(int bid) throws ClassNotFoundException, SQLException {
        ParticipantsDAO dao = new ParticipantsDAO();
        List<Participants> list = dao.BatchParticipants(bid);
        int result = 0;
        for (Participants b : list) {
        		String name = b.getPname().toUpperCase();
        				dao.sendSMS(name);
        				dao.sendEmail(name);
        				result++;
        }
        return result;
    }

}