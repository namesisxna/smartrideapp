package com.cts.gto.techngage.smartride.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;


import com.cts.gto.techngage.smartride.android.TimepickerActivity;


/**
 * Created by SUMIT on 17-05-2016.
 */
public class DatepickerActivity extends AppCompatActivity {

    private DatePicker datepicker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_datepicker); // Display datepicker activity when app starts
        Button button = (Button)findViewById(R.id.getDate);

        // Register the onClick listener with the implementation above
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    datepicker = (DatePicker) findViewById(R.id.datePicker);


                        com.cts.gto.techngage.smartride.android.TimepickerActivity.setLastPagedateData(datepicker);
                    String date = "Selected date "+datepicker.getDayOfMonth()+"-"+(datepicker.getMonth()+1)+"-"+datepicker.getYear();
                    Toast.makeText(getBaseContext(), date, Toast.LENGTH_LONG).show();


                    Intent intent = new Intent(getApplicationContext(), com.cts.gto.techngage.smartride.android.TimepickerActivity.class);
                    startActivity(intent);
                }
            });
        }


    }
}
