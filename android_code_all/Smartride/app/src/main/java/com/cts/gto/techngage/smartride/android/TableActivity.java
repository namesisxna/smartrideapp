package com.cts.gto.techngage.smartride.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


/**
 * Created by administrator on 5/16/2016.
 */
public class TableActivity extends AppCompatActivity {
    List<String> contentString = new ArrayList<String>();

    private static StringData lastPageData;
    private com.cts.gto.techngage.smartride.pubnub.android.PubnubHelper pubnubHelper = null;
    private String phoneChannel = null;
    private BookingActivity bookingActivity;
    private   TableLayout ll;
    private RelativeLayout layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String phoneId = RegistrationSmartphoneActivity.getPhoneId();
        phoneChannel = "ph-ch_" + phoneId;
        setContentView(R.layout.activity_table);
        layout = (RelativeLayout)findViewById(R.id.layout3_contents);
        makeTable();


        // TableLayout tableLayout = (TableLayout) findViewById(R.id.table_contents);
    }

    public void makeTable(){



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
            TextView  tv = new TextView(this);

            TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
            params.gravity = Gravity.CENTER;
            params.span =1;
            tv.setLayoutParams(params);
            tv.setBackgroundColor(0xdcdcdc);
            tv.setTextColor(0x000000);

            row.addView(tv);
            Button button = null;
            ll.addView(row,0);
            for (final Map<String, String> recMap : lastPageData.getDataList()) {

                TableRow row1= new TableRow(this);

                row1.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT));
                String deptTmdata = recMap.get("estm_arv_tm");
                String deptTm ="";
                String deptTmininitial ="";
                String deptThrinitial ="";
                String depmminfin ="";
                if(!deptTmdata.isEmpty() && deptTmdata != null){
                    StringTokenizer st  = new StringTokenizer(deptTmdata,".");
                    while (st.hasMoreTokens()){
                        deptThrinitial = st.nextToken();
                        deptTmininitial = st.nextToken();
                    }
                   String strmin = "0."+deptTmininitial;
                    Double doublemin =Double.parseDouble(strmin);
                    int intmin = (int)(doublemin * 60);
                    if(intmin == 0)
                    {depmminfin =String.valueOf(intmin)+"0";}
                    else {
                     depmminfin = String.valueOf(intmin);}


                }
                deptTm = deptThrinitial+":"+depmminfin;
                String tentativecount = recMap.get("tent_count");
                String confirmcount = recMap.get("confirm_count");
                final String routeId = recMap.get("route_id");
               final  String routeName = recMap.get("route_nm");
                int confirmcountN = 0, tentativecountN=0;

                try {
                    confirmcountN = Integer.parseInt(confirmcount);
                } catch (NumberFormatException e) {}
                try {
                    tentativecountN = Integer.parseInt(tentativecount);
                } catch (NumberFormatException e) {}

//
                button = new Button(this);
                String text = "<big><font color='#008000'>"+"Route: "+routeName+"</font></big>"+"<br />"+"<small>"+" Onboard time :" +"<b>" +deptTm  +"</b>" +"  Tentative :" +"<b>"+tentativecountN + "</b>"+"   " + "  Confirmed :" +"<b>"+confirmcountN+"</b>"+"</small>";
                button.setText(Html.fromHtml(text));
                button.setId(Integer.parseInt(routeId));

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                       // submit1(v.getId());

                        calculateCost(String.valueOf(v.getId()));
                        BookingActivity.setLastPageData(recMap);
                        inActivatePage();




                    }
                });

                row1.addView(button);
//
//            row.addView(button);
                ll.addView(row1,i);

                i++;
            }//for loop ends

            // RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.layout3_contents);
            //  relativeLayout.addView(ll);
            // setContentView(relativeLayout);

            layout.addView(ll);

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    public void calculateCost(String routeId){
        try {
            StringBuilder reqString = new StringBuilder();
            String routId = routeId;
            String onBoardStop = lastPageData.getValue(0,"pick");
            String offboardSop = lastPageData.getValue(0,"dest");


            reqString.append("OPRN=").append("calculateCost").append('|');

            reqString.append("ROUTE_ID=").append(routId).append('|');
            reqString.append("ONBOARD_STOP=").append(onBoardStop).append('|');
            reqString.append("OFFBOARD_STOP=").append(offboardSop);
            pubnubHelper = new PubnubHelper();
            pubnubHelper.subscribeString(phoneChannel + "_resp", subscribeCallback);
            pubnubHelper.publishString(phoneChannel, reqString.toString());
            Log.i("TableActivity.publish:", " data sent to backend..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void inActivatePage(){


        //v.setEnabled(false);

        for(int i = 0; i < ll.getChildCount(); i++)
        {
            TableRow row = (TableRow) ll.getChildAt(i);
            //This will iterate through the table row.
            for(int j = 0; j < row.getChildCount(); j++)
            {
                View view = row.getChildAt(j);
           view.setEnabled(false);
            }
        }

//        for ( int i = 0; i < ll.getChildCount(); i++ ){
//            View view = ll.getChildAt(i);
//            view.setEnabled(false); // Or whatever you want to do with the view.
//        }
        ProgressBar  progressBar = new ProgressBar(TableActivity.this, null, android.R.attr.progressBarStyleLarge);
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
            String responseString = message.toString();
            Log.i("TableActivity:", " : Response received from backend :"+responseString);

            StringData strData = null;
            try {
                strData = new StringData(responseString);

                // genQRCode(messagedata);
                bookingActivity.setDatafromTableActivity(strData);
                Intent intent = new Intent(getApplicationContext(), com.cts.gto.techngage.smartride.android.BookingActivity.class);
                startActivity(intent);


            } catch (Exception e) {
                e.printStackTrace();
            }
            pubnubHelper.unSubscribe(channel);
        }
    };

    //-----------------------------------------------------------------------------------



    public static StringData getLastPageData() {
        return lastPageData;
    }

    public static void setLastPageData(StringData lastPageData) {
        com.cts.gto.techngage.smartride.android.TableActivity.lastPageData = lastPageData;
    }

}
