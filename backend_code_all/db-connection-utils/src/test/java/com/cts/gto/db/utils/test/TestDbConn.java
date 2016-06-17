package com.cts.gto.db.utils.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cts.gto.techngage.smartride.backend.db.utils.core.DbConnectionUtils;


public class TestDbConn {

	public static void main(String[] args) {
        
        System.out.println("**********");
        testDBCPDataSource();
    }
 
    private static void testDBCPDataSource() {
                 
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DbConnectionUtils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select VEHICLE_ID, REG_NUM, VEHICLE_NAME from VEHICLE_DTL");
            while(rs.next()){
                System.out.println("VEHICLE_ID ID="+rs.getInt("VEHICLE_ID")
                		+", REG_NUM="+rs.getString("REG_NUM")+", Name ="+rs.getString("VEHICLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
                try {
                    if(rs != null) rs.close();
                    if(stmt != null) stmt.close();
                    if(con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

}
