package com.cts.gto.techngage.smartride.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cts.gto.techngage.smartride.dataobj.StringData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by administrator on 6/14/2016.
 */
public class ExistingBookingTableActivity extends AppCompatActivity {
    private static StringData lastpagedata;
    private RelativeLayout layout;
    private   TableLayout ll;
    private String bookingNm;
    private  String routeId;
    private String bookingType;
    private String onboardStop;
    private String offboardStop;
    private  String pickupDtTm;
    private  String onboardstopnm;
    private  String offboardstopnm;
    private String messagedata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existingbookingtable);
        layout = (RelativeLayout)findViewById(R.id.exitingbookingLayout);
        makeTable(ExistingBookingTableActivity.getLastpagedata());
    }

    public void makeTable(StringData data){



        ll = new TableLayout(this);


        ll.setShrinkAllColumns(true);
        ll.setStretchAllColumns(true);
        ll.setBackgroundColor(0xffffff);
        int i=0;
        TableRow row= new TableRow(this);

        row.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT));
    /* Create a Button to be the row-content. */

//            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//            row.setLayoutParams(lp);
        try{
            TextView tv = new TextView(this);

            TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
            params.gravity = Gravity.CENTER;
            params.span =1;
            tv.setLayoutParams(params);
            tv.setBackgroundColor(0xdcdcdc);
            tv.setTextColor(0x000000);


            row.addView(tv);

            Button button = null;
            ll.addView(row,0);
            final Map<Integer,String> qrmessage = new HashMap<Integer,String>();
            for (final Map<String, String> recMap : data.getDataList()) {
                //for (int i=0; i<contentString.size(); i++) {
                //String s = contentString.get(0);l1.a
                TableRow row1= new TableRow(this);

                row1.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT));
    /* Create a Button to be the row-content. */

//            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//            row.setLayoutParams(lp);

                 bookingNm = recMap.get("booking_num");
                routeId = recMap.get("route_id");
                bookingType = recMap.get("booking_type");
                onboardStop = recMap.get("onboard_stop");
                 offboardStop = recMap.get("offboard_stop");
                 pickupDtTm = recMap.get("pickup_dt_tm");
                onboardstopnm = recMap.get("onboardstopnm");
                offboardstopnm =recMap.get("offboardstopnm");

                messagedata = "OFFBOARD_STOP="+offboardStop+"|"+"ONBOARD_STOP="+onboardStop+"|"+"BOOK_NUM="+bookingNm+"|"+"BOOK_TYPE="+bookingType+"|"+"PHONE_ID="+RegistrationSmartphoneActivity.getPhoneId();
                qrmessage.put(i,messagedata);
//
                button = new Button(this);
                String text = "<big><font color='#008000'>"+"Booking Number: "+bookingNm+"</font></big>"+"<br />"+"<small>"+"  From :" +"<b>" +onboardstopnm  +"</b>" +"  To :" +"<b>"+offboardstopnm + "</b>"+"</small>"+"</br>"+"<small>"+" ArivalTime:"+"<b>"+pickupDtTm+"</b>"+" BookingType:"+"<b>"+bookingType+"</b>"+"</small>";
                button.setText(Html.fromHtml(text));
                button.setId(0 + i);

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                           String messagedata1 = qrmessage.get(v.getId());
                        ExistingQrCodeActivity.setDatafromExQRactivity(messagedata1);
                        Intent intent = new Intent(getApplicationContext(), ExistingQrCodeActivity.class);
                        startActivity(intent);




                    }
                });

                row1.addView(button);

                ll.addView(row1, i);

                i++;
            }

            layout.addView(ll);

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public static StringData getLastpagedata() {
        return lastpagedata;
    }

    public static void setLastpagedata(StringData lastpagedata) {
        ExistingBookingTableActivity.lastpagedata = lastpagedata;
    }
}
