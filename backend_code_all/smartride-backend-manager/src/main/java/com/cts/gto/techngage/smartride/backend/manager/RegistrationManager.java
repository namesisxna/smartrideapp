package com.cts.gto.techngage.smartride.backend.manager;

import java.util.List;
import java.util.Map;

import com.cts.gto.techngage.smartride.backend.dao.repository.BookingRepository;
import com.cts.gto.techngage.smartride.backend.dao.repository.RegistrationRepository;
import com.cts.gto.techngage.smartride.backend.dao.repository.RouteRepository;
import com.cts.gto.techngage.smartride.backend.dataobj.StringData;

public class RegistrationManager {
	
	
	private RegistrationRepository registrationRepository;
	
	public RegistrationManager() {
		registrationRepository = registrationRepository == null ? new RegistrationRepository() : registrationRepository;
	}

	
	
	public StringData registerSmartphone(StringData msg) {
		StringData respData = null;
		try {	
			
			System.out.println("******* BUSINESS METHOD INVOKED :delegateStringMessage *************** ");
												
			System.out.println("DAO registerSmartphone() : Request received... msg :"+msg);		
						
			respData = registrationRepository.registerSmartphone(msg);
			
			System.out.println("DAO registerSmartphone() : generated phone-id:"+respData.getValue(0, "phone-id"));
			
						
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return respData;
	}
	

	
}
