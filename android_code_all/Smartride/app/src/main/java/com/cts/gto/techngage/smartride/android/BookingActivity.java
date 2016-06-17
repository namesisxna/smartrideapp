package com.cts.gto.techngage.smartride.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.cts.gto.techngage.smartride.dataobj.StringData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.pubnub.api.Callback;

import java.util.List;
import java.util.Map;


/**
 * Created by administrator on 5/23/2016.
 */
public class BookingActivity extends AppCompatActivity {
    private static Map<String, String> lastPageData;
    private static String phoneChannel;
    private static String phoneId;
    private com.cts.gto.techngage.smartride.pubnub.android.PubnubHelper pubnubHelper = null;
    private QrCodeActivity qrCodeActivity;
    private static StringData datafromTableActivity;


    int i = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //createbookingtable();
        phoneId = RegistrationSmartphoneActivity.getPhoneId();
        phoneChannel = "ph-ch_"+phoneId;
        String freezedAmount = null;
        String travelCost = null;
        setContentView(R.layout.activity_booking);

     //final  RelativeLayout relativeLayout = new RelativeLayout(this);
       //RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        try {
             freezedAmount = datafromTableActivity.getValue(0,"frezed_amount");
             travelCost = datafromTableActivity.getValue(0,"travel_cost");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button button = (Button)findViewById(R.id.tentativeBooking_button);
        button.setText("BOOK TENTATIVE");

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                System.out.println("v.getid is:- " + v.getId());
                inActivatePage();
                bookTentative();
                //relativeLayout.addView(img1);

            }
        });


        Button button1 = (Button)findViewById(R.id.confirmedBooking_button);

        button1.setText("BOOK CONFIRM");
       // button1.setId();
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                System.out.println("v.getid is:- " + v.getId());
                inActivatePage();
                bookConfirmed();
                //relativeLayout.addView(img1);
            }
        });


        TextView tv = (TextView)findViewById(R.id.showcostView);
        tv.setText("Mandatory Instructions:");

        TextView tv1 = (TextView)findViewById(R.id.showcostView1);
        tv1.setText("Please read the instructions carefully. After reading these, you should proceed:" +
                "The total amount for this selected route will freeze here at the time of confirm booking due to our clause. But, don’t worry! At destination bus stop, when you scan the QR_code generated on your mobile, your appropriate fare will be deducted from the total freezed amount and remaining amount will be credited in your account.");
        TextView tv3 = (TextView)findViewById(R.id.showcostView4);
        tv3.setText("Total FreezedAmount: " + freezedAmount);
        TextView tv4 = (TextView)findViewById(R.id.showcostView5);
        tv4.setText("Total TravelCost: "+travelCost);
        TextView tv2 = (TextView)findViewById(R.id.showcostView2);
        tv2.setText("Wish you a safe and happy journey:");




        // relativeLayout.addView(tv);

       // relativeLayout.addView(button1);



       // setContentView(relativeLayout);
        pubnubHelper = new com.cts.gto.techngage.smartride.pubnub.android.PubnubHelper();
    }

    public void inActivatePage(){

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.bookingLayout);
        //v.setEnabled(false);

        for(int i = 0; i < layout.getChildCount(); i++)
        {
            LinearLayout lnearlayout = (LinearLayout) layout.getChildAt(i);
            //This will iterate through the table row.
            for(int j = 0; j < lnearlayout.getChildCount(); j++)
            {
                View view = lnearlayout.getChildAt(j);
                view.setEnabled(false);
            }
        }

//        for ( int i = 0; i < ll.getChildCount(); i++ ){
//            View view = ll.getChildAt(i);
//            view.setEnabled(false); // Or whatever you want to do with the view.
//        }
        ProgressBar progressBar = new ProgressBar(BookingActivity.this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar, params);
    }



    private void bookTentative() {



//        String routeId =  lastPageData.get("route_id");
//        String pickupDate =  lastPageData.get("pickup_date");
//        StringData input = new StringData();
        performBooking("T");
        //genQRCode("Tentative Booking");
    }

    private void bookConfirmed() {

        performBooking("C");
        //genQRCode("Confirmed Booking");
    }


    public static Object getLastPageData() {
        return lastPageData;
    }

    private void performBooking(String bookingType) {

        String routeId =  lastPageData.get("route_id");
        String pickupDate =  lastPageData.get("pickup_date");
        String onboardStop =  lastPageData.get("pick");
        String offboardStop =  lastPageData.get("dest");

        StringBuilder reqStringB = new StringBuilder();

        reqStringB.append("OPRN=").append("NEW_BOOKING").append('|');

        reqStringB.append("ROUTE_ID=").append(routeId).append('|');
        reqStringB.append("BOOKING_TYPE=").append(bookingType).append('|');
        reqStringB.append("ONBOARD_STOP=").append(onboardStop).append('|');
        reqStringB.append("OFFBOARD_STOP=").append(offboardStop).append('|');
        reqStringB.append("PICKUP_DT_TM=").append(pickupDate).append('|');
        reqStringB.append("BOOKING_STATUS=").append('A').append('|');
        reqStringB.append("DEVICE_ID=").append(phoneId);

        pubnubHelper.subscribeString(phoneChannel + "_resp", subscribeCallback);
        pubnubHelper.publishString(phoneChannel, reqStringB.toString());
        Log.i("BookingActivty.publish:", " data sent to backend..");
    }



    //----------- Pubnub Section ----------------------------------------------------------------
    private Callback subscribeCallback = new Callback() {
        @Override
        public void successCallback(String channel, Object message) {
            String responseString = message.toString();
            Log.i("BookingActivity:", " : Response received from backend :"+responseString);

            StringData strData = null;
            try {
                strData = new StringData(responseString);

               // genQRCode(messagedata);
                qrCodeActivity.setLastPageData(strData);
                Intent intent = new Intent(getApplicationContext(), com.cts.gto.techngage.smartride.android.QrCodeActivity.class);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
            pubnubHelper.unSubscribe(channel);
        }
    };

    //-----------------------------------------------------------------------------------



    public static void setLastPageData(Map<String, String> lastPageData) {
        BookingActivity.lastPageData = lastPageData;
    }

    public static StringData getDatafromTableActivity() {
        return datafromTableActivity;
    }

    public static void setDatafromTableActivity(StringData datafromTableActivity) {
        BookingActivity.datafromTableActivity = datafromTableActivity;
    }

}
