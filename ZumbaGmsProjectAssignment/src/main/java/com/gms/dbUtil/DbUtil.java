package com.gms.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtil {
			
	public static Connection getDbConn() throws ClassNotFoundException, SQLException {
		
		//register the vendor driver		
		Class.forName(DbUtilHelperConstant.DRIVER_CLASS);
		
		//establish communication with the db		
		Connection con = DriverManager
				.getConnection(DbUtilHelperConstant.DB_URL,DbUtilHelperConstant.USERNAME,DbUtilHelperConstant.PASSWORD);
				
		return con;
	}

    public static void closeConnection(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }

    // Overloaded method to close the database connection without a result set
    public static void closeConnection(Connection con, PreparedStatement ps) {
        closeConnection(con, ps, null);
    }
}