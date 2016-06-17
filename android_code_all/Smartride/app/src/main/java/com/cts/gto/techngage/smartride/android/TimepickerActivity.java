package com.cts.gto.techngage.smartride.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cts.gto.techngage.smartride.dataobj.StringData;
import com.pubnub.api.Callback;

import java.util.List;

import com.cts.gto.techngage.smartride.pubnub.android.PubnubHelper;

import com.cts.gto.techngage.smartride.android.TableActivity;

public class TimepickerActivity extends AppCompatActivity {


    private DatePicker datepicker; // object for datepicker
    private TimePicker timepicker; // object for activity_timepicker
    private com.cts.gto.techngage.smartride.pubnub.android.PubnubHelper pubnubHelper = null;

    private  TextView txtView =null;
    private static Object lastPageData;
    private static Object lastPagedateData;

    private TimepickerActivity internalThis;
    private static String phoneChannel;
    private TableActivity tableActivity;

    private String responseString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        phoneChannel = "ph-ch_"+RegistrationSmartphoneActivity.getPhoneId();

        setContentView(R.layout.activity_timepicker); // Display datepicker activity when app starts

        timepicker = (TimePicker) findViewById(R.id.timePicker); //Obtain activity_timepicker attributes from layout
        timepicker.setIs24HourView(true);    //setting activity_timepicker to 24 hr clock view

        Button button1 = (Button)findViewById(R.id.getTime);

        // Register the onClick listener with the implementation above
        if (button1 != null) {
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String time = "Selected Time "+timepicker.getCurrentHour()+"h "+timepicker.getCurrentMinute()+"m";
                    Toast.makeText(getBaseContext(), time, Toast.LENGTH_LONG).show();
                }
            });
        }

       // ; // Obtain datepicker attributes from layout

        Button button = (Button) findViewById(R.id.getRoutes); // button to display datepicker value

        if (button != null) {

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    inActivatePage();

                    retrieveRouteData();
                }
            });
        }


        pubnubHelper = new com.cts.gto.techngage.smartride.pubnub.android.PubnubHelper();
    }


    private void retrieveRouteData() {
        List<String> latlngStr = (List<String>)lastPageData;
        datepicker =(DatePicker) lastPagedateData;
        String pickupStr = "pick="+latlngStr.get(0);
        String destStr = "dest="+latlngStr.get(1);
        String date = "date="+datepicker.getDayOfMonth()+"-"+(datepicker.getMonth())+"-"+datepicker.getYear();
        String pickuptime = "pickuptime="+timepicker.getCurrentHour()+"h"+timepicker.getCurrentMinute()+"m";
        String finalString = pickupStr+'|'+destStr+"|"+date+"|"+pickuptime;
        Log.i("mystring",finalString);

        Toast.makeText(getBaseContext(), finalString, Toast.LENGTH_LONG).show();


        pubnubHelper.subscribeString(phoneChannel + "_resp", subscribeCallback);

        pubnubHelper.publishString(phoneChannel, finalString);

        Log.i("Timepicker.retrRoutDt()", " : data sent to backend..");
    }

    public void inActivatePage(){

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.timepickerlayout);

//
        ProgressBar progressBar = new ProgressBar(TimepickerActivity.this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar, params);
    }




    //----------- Pubnub Section ----------------------------------------------------------------
    private Callback subscribeCallback = new Callback() {
        @Override
        public void successCallback(String channel, Object message) {
            responseString = message.toString();

            Log.i("Timepicker.retrRoutDt()", " : Response received from backend :"+responseString);

            StringData strData = null;
            try {
                strData = new StringData(responseString);

                TableActivity.setLastPageData(strData);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(getApplicationContext(), TableActivity.class);
            startActivity(intent);

            pubnubHelper.unSubscribe(channel);
        }
    };

    //-----------------------------------------------------------------------------------

    public static Object getLastPageData() {
        return lastPageData;
    }

    public static void setLastPageData(Object lastPageData) {
        TimepickerActivity.lastPageData = lastPageData;
    }
    public static Object getLastPagedateData() {
        return lastPagedateData;
    }

    public static void setLastPagedateData(Object lastPagedateData) {
        com.cts.gto.techngage.smartride.android.TimepickerActivity.lastPagedateData = lastPagedateData;
    }

}
