package com.cts.gto.techngage.smartride.backend.main;

import com.cts.gto.techngage.smartride.backend.adapter.sub.BookingGroupSubscriber;
import com.cts.gto.techngage.smartride.backend.dao.key.UniqueKeyUtils;


public class LaunchBooking {
	
	public void backEndManager() {
		try {
			
			UniqueKeyUtils.getInstance();
			
			BookingGroupSubscriber bgSub = new BookingGroupSubscriber();
			bgSub.subscribeString();
			System.out.println("LaunchBooking: Subscribed to Booking group.. ");	

			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
		
	public static void main(String ags[]) {
		LaunchBooking testPubSub = new LaunchBooking();
		
		testPubSub.backEndManager();
		
	}

}
