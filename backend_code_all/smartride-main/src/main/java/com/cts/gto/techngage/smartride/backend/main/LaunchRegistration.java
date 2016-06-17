package com.cts.gto.techngage.smartride.backend.main;

import com.cts.gto.techngage.smartride.backend.adapter.sub.SmartphoneRegistrationSubscriber;
import com.cts.gto.techngage.smartride.backend.dao.key.UniqueKeyUtils;



public class LaunchRegistration {
	
	public static void main(String ags[]) {
		UniqueKeyUtils.getInstance();
		
		SmartphoneRegistrationSubscriber regiSub = new SmartphoneRegistrationSubscriber();
		regiSub.subscribeString();
		System.out.println(" LaunchRegistration : Registration channel.. ");		
	}

}
