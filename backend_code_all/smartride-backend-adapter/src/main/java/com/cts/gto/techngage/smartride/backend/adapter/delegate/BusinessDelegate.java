package com.cts.gto.techngage.smartride.backend.adapter.delegate;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;


import com.cts.gto.techngage.smartride.backend.adapter.pub.BookingBackendPublisher;
import com.cts.gto.techngage.smartride.backend.manager.BookingManager;
import com.cts.gto.techngage.smartride.backend.manager.RegistrationManager;
import com.cts.gto.techngage.smartride.backend.dao.repository.RouteRepository;

import com.cts.gto.techngage.smartride.backend.dataobj.StringData;


public class BusinessDelegate {
	
	
	private RouteRepository routeRepository;
	
	public BusinessDelegate() {
		routeRepository = routeRepository == null ? new RouteRepository() : routeRepository;
	}

	public StringData delegateSmartphoneRegistration(StringData msg) {
		StringData respData = null;
		try {	
			
			System.out.println("******* BUSINESS METHOD INVOKED :delegateStringMessage *************** ");
			RegistrationManager regMangr = new RegistrationManager();							
						
			respData = regMangr.registerSmartphone(msg);
						
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return respData;
	}
	
	public StringData delegateToBackend(StringData msg) {
		StringData respData = null;
		try {	
			
			System.out.println("******* BUSINESS METHOD INVOKED :delegateToBackend *************** ");
			BookingManager bookMangr = new BookingManager();
			
			String operationId = msg.getValue(0, "OPRN");
			System.out.println("******* delegateToBackend() operationId :"+operationId);
					
			if(operationId == null) {
				respData = bookMangr.retrieveRoute(msg);
			}
			else if("NEW_BOOKING".equals(operationId)) {
				respData = bookMangr.performBooking(msg);
			}
			else if("busstopBackEndOPRN".equals(operationId)) {
				respData = bookMangr.confirmBooking(msg);
			}
			else if("calculateCost".equals(operationId)) {
				respData = bookMangr.calculateCost(msg);
			}
			else if("getExitingBookingData".equals(operationId)) {
				respData = bookMangr.getExitingBookingData(msg);
			}
						
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return respData;
	}
	
		
}
