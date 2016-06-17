package com.cts.gto.techngage.smartride.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cts.gto.techngage.smartride.dataobj.StringData;
import com.cts.gto.techngage.smartride.pubnub.android.PubnubHelper;
import com.pubnub.api.Callback;

import java.util.Map;

/**
 * Created by SUMIT on 05-06-2016.
 */
public class ExistingBookingActivity extends AppCompatActivity{
    private static String phoneChannel;
    private RelativeLayout layout;
    private PubnubHelper pubnubHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pubnubHelper = new PubnubHelper();
        phoneChannel = "ph-ch_" + RegistrationSmartphoneActivity.getPhoneId();
        setContentView(R.layout.activity_existingbookingtable);
        layout = (RelativeLayout)findViewById(R.id.exitingbookingLayout);
        inActivatePage();

    }
    public void inActivatePage(){



        ProgressBar progressBar = new ProgressBar(ExistingBookingActivity.this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar, params);

        pubnubHelper.subscribeString(phoneChannel + "_resp", subscribeCallback);
        String phoneId = RegistrationSmartphoneActivity.getPhoneId();
        phoneChannel = "ph-ch_" + phoneId;

        StringBuilder reqString = new StringBuilder();
        reqString.append("OPRN=").append("getExitingBookingData").append('|');
        reqString.append("deviceId=").append(phoneId);
        pubnubHelper.publishString(phoneChannel, reqString.toString());


    }

    //----------- Pubnub Section ----------------------------------------------------------------
    private Callback subscribeCallback = new Callback() {
        @Override
        public void successCallback(String channel, Object message) {
            String responseString = message.toString();
            Log.i("ExBookingActivity:", " : Response received from backend :" + responseString);

            StringData strData = null;

            try {
               strData = new StringData(responseString);

//
//                // genQRCode(messagedata);
//                bookingActivity.setDatafromTableActivity(strData);
//                Intent intent = new Intent(getApplicationContext(), com.cts.gto.techngage.smartride.android.BookingActivity.class);
//                startActivity(intent);
                ExistingBookingTableActivity.setLastpagedata(strData);
                Intent intent = new Intent(getApplicationContext(), ExistingBookingTableActivity.class);
                startActivity(intent);


            } catch (Exception e) {
                e.printStackTrace();
            }
            pubnubHelper.unSubscribe(channel);
        }
    };

    //-----------------------------------------------------------------------------------




}
