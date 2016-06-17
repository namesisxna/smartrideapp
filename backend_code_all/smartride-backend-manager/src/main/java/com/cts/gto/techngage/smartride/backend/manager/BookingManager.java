package com.cts.gto.techngage.smartride.backend.manager;

import java.util.List;
import java.util.Map;

import com.cts.gto.techngage.smartride.backend.dao.repository.BookingRepository;
import com.cts.gto.techngage.smartride.backend.dao.repository.RouteRepository;
import com.cts.gto.techngage.smartride.backend.dataobj.StringData;



public class BookingManager {
	
	
	private RouteRepository routeRepository;
	private BookingRepository bookingRepository;
	
	public BookingManager() {
		//routeRepository = routeRepository == null ? new RouteRepository() : routeRepository;
		//bookingRepository = bookingRepository == null ? new BookingRepository() : bookingRepository;
	}

	
	
	public StringData retrieveRoute(StringData msg) {
		StringData respData = null;
		try {			
			System.out.println("******* BUSINESS METHOD INVOKED :delegateStringMessage *************** ");
			System.out.println("retrieveRoute() : Request received... msg :"+msg);
								
			routeRepository = new RouteRepository();
			respData = routeRepository.retrieveRoute(msg);
						
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return respData;
	}
	
	
	public StringData performBooking(StringData msg) {
		StringData respData = null;
		try {			
			System.out.println("******* BUSINESS METHOD INVOKED :performBooking *************** ");
			System.out.println("performBooking() : Request received... msg :"+msg);
						
			bookingRepository = new BookingRepository();
			respData = bookingRepository.performBooking(msg);
						
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return respData;
	}
	
	public StringData calculateCost(StringData msg) {
		StringData respData = null;
		try {			
			System.out.println("******* BUSINESS METHOD INVOKED :calculatecost *************** ");
			System.out.println("calculateCost() : Request received... msg :"+msg);
						
			bookingRepository = new BookingRepository();
			respData = bookingRepository.calculateCost(msg);
						
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return respData;
	}
	
	public StringData confirmBooking(StringData msg) {
		StringData respData = null;
		try {			
			System.out.println("******* BUSINESS METHOD INVOKED :confirmbooking *************** ");
			System.out.println("confirmBooking() : Request received... msg :"+msg);
				
			bookingRepository = new BookingRepository();
			respData = bookingRepository.confirmBooking(msg);
				
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return respData;
	}
	public StringData getExitingBookingData(StringData msg) {
		StringData respData = null;
		try {			
			System.out.println("******* BUSINESS METHOD INVOKED :confirmbooking *************** ");
			System.out.println("getExitingBookingData() : Request received... msg :"+msg);
				
			bookingRepository = new BookingRepository();
			respData = bookingRepository.getExistingBookingData(msg);
				
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return respData;
	}
	

	
}
