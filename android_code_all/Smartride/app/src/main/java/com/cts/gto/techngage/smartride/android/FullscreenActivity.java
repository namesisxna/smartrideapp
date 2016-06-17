package com.cts.gto.techngage.smartride.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;



/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private View mControlsView;
    private Button butnNewBooking, butnRegistration;
    private Button butnExstBooking, butnBookingHist;
    private String phoneChannel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        butnExstBooking = (Button)findViewById(R.id.ExistingBooking_button);
        butnBookingHist = (Button)findViewById(R.id.BookingHistory_button);

        butnNewBooking = (Button)findViewById(R.id.NewBooking_button);
//
//        // Register the onClick listener with the implementation above
        if (butnNewBooking != null) {
            butnNewBooking.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (butnExstBooking != null) {
            butnExstBooking.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {



                    Intent intent = new Intent(getApplicationContext(), ExistingBookingActivity.class);
                    startActivity(intent);
                }
            });
        }
        butnRegistration = (Button)findViewById(R.id.Registration_button);
//
//        // Register the onClick listener with the implementation above
        if (butnRegistration != null) {
            butnRegistration.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), RegistrationSmartphoneActivity.class);
                    startActivity(intent);
                }
            });
        }

        /**
         * @TODO uncomment following line when development is over. Commented for testing...
         */
        setEnableButtons();

    }//onCreate() ends----


    private void setEnableButtons() {
        String phoneRegNo = RegistrationSmartphoneActivity.checkRegistration(getFilesDir());

        boolean flag = !(phoneRegNo == null || phoneRegNo.isEmpty());

        butnNewBooking.setEnabled(flag);
        butnExstBooking.setEnabled(flag);
        butnBookingHist.setEnabled(flag);
    }

}//class ends----
