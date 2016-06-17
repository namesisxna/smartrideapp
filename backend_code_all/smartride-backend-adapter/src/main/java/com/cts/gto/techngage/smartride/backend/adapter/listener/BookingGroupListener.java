package com.cts.gto.techngage.smartride.backend.adapter.listener;



import com.cts.gto.techngage.smartride.backend.adapter.delegate.BusinessDelegate;
import com.cts.gto.techngage.smartride.backend.adapter.pub.BookingBackendPublisher;
import com.cts.gto.techngage.smartride.backend.dataobj.StringData;
import com.cts.gto.techngage.smartride.backend.pubnub.utils.common.DefaultCallbackListener;


public class BookingGroupListener extends DefaultCallbackListener {
	
	public BookingGroupListener() {}
	public BookingGroupListener(String deviceUuid) {
		super(deviceUuid);
	}
	
	public void onSuccessReceive(String ch, String msg) {
		System.out.println("MyListener.onSuccessReceive().. calling business method..");
		System.out.println("Channel : "+ch+"| Raw response "+ msg.toString());
		//super.onSuccessReceive(ch, msg);
		
		//String inputStr = super.getReceivedData();
		StringData strData = null, respData=null;		
		try {
			strData = new StringData(msg);
		} catch (Exception e) {			
			e.printStackTrace();
		}
				
		//-- Delegating request to business logic method ---------
		BusinessDelegate bizLogic = new BusinessDelegate();
		StringData str1 = null;
	try {
		 str1 = new StringData("");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		respData = bizLogic.delegateToBackend(strData);
		
			respData = respData == null ?  str1 : respData;
		
		
		System.out.println("BookingGroupListener.onSuccessReceive() Response data ="+respData.toString());
		
		String respChannel = ch + "_resp";
		
		BookingBackendPublisher bookingBackendPublisher = new BookingBackendPublisher();
		bookingBackendPublisher.publishStringMessage1(respChannel, respData.toString());
		
		System.out.println("BookingGroupListener.onSuccessReceive() Response Published at channel :"+respChannel);
		//--------------		
	}
	

}
