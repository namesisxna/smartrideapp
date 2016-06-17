package com.cts.gto.techngage.smartride.backend.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cts.gto.techngage.smartride.backend.db.utils.core.DbConnectionUtils;
import com.cts.gto.techngage.smartride.backend.dao.vo.SmartStop;


public class SmartStopRepository {
	static Connection con = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) {
		retrieveSmartStopByStopNm("hwh");

	}

	
	public static List<SmartStop> retrieveSmartStopByStopNm(String c) {
		ArrayList<SmartStop> smartStoplist = new ArrayList<SmartStop>();

		try {
			con = DbConnectionUtils.getConnection();
			String sql = ("select * from smart_stop where stop_nm= ?");
			PreparedStatement pStatement = con.prepareStatement(sql);
            pStatement.setString(1, c);
			ResultSet result = pStatement.executeQuery();
			
			int i = 0;
			while (result.next()) {
				SmartStop smartStop = new SmartStop();
				smartStop.setStop_id(result.getInt("stop_id"));
				smartStop.setStop_nm(result.getString("stop_nm"));
				smartStop.setStop_lat(result.getString("stop_lat"));
				smartStop.setStop_long(result.getString("stop_long"));
				
				smartStoplist.add(i, smartStop);
				System.out.println("stop_id :" + smartStoplist.get(0).getStop_id());
				System.out.println("stop_nm :" + smartStoplist.get(0).getStop_nm());
				System.out.println("stop_lat :" + smartStoplist.get(0).getStop_lat());
				System.out.println("stop_long :" + smartStoplist.get(0).getStop_long());
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		return smartStoplist;
	}
	
}
