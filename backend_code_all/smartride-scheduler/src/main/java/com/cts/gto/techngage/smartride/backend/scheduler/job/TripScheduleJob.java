package com.cts.gto.techngage.smartride.backend.scheduler.job;

//import java.sql.Date;

import java.util.Calendar;
import com.cts.gto.techngage.smartride.backend.scheduler.job.IConstants;
import com.cts.gto.techngage.smartride.backend.dataobj.StringData;
import com.cts.gto.techngage.smartride.backend.manager.TripManager;

public class TripScheduleJob {

	public void scheduleUpcomingTrips(String offsetMinute) {

		try {
			int offsetMin = 0;

			try {
				offsetMin = Integer.parseInt(offsetMinute);
			} catch (NumberFormatException e) {
				offsetMin = 60;
			}

			Calendar calendar = Calendar.getInstance();

			int day = calendar.get(Calendar.DAY_OF_MONTH);
			String dd = day < 10 ? "0" + day : "" + day;

			int month = calendar.get(Calendar.MONTH);
			String mon = IConstants.SQL_MON[month];

			int year = calendar.get(Calendar.YEAR);

			String dateSql = dd + '-' + mon + '-' + year;

			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			String hh = hour < 10 ? "0" + hour : "" + hour;

			int minute = calendar.get(Calendar.MINUTE);
			String mm = minute < 10 ? "0" + minute : "" + minute;

			int sec = calendar.get(Calendar.SECOND);
			String ss = sec < 10 ? "0" + sec : "" + sec;

			String timeSql = hh + '-' + mm + '-' + ss;

			// ------------------------------------

			StringBuilder sb = new StringBuilder();
			sb.append("date=").append(dateSql).append('|')
					.append("pickuptime=").append(timeSql).append('|')
					.append("time-offset-hr=").append(offsetMin);

			StringData stringData;

			stringData = new StringData(sb.toString());

			TripManager tripManager = new TripManager();
			tripManager.scheduleUpcomingTrips(stringData);
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
