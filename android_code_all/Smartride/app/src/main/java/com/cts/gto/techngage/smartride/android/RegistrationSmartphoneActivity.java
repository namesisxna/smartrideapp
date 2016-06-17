package com.cts.gto.techngage.smartride.android;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cts.gto.techngage.smartride.pubnub.android.PubnubHelper;

import com.pubnub.api.Callback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class RegistrationSmartphoneActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String phoneIdFileName = "smartrideIdFile.txt";

    private TextView titleTextView;
    private PubnubHelper pubnubHelper;
    private String responseString;

    private RegistrationSmartphoneActivity internalThis;

    private String smartrideIdFileUri;
    private static final int REQUEST_READ = 0;

    private String internalFileLocation;

    private static short callerPageIndex = 1;
    private static String callerData;
    private Button registerButton;



    private static String phoneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        internalThis = this;

        titleTextView = (TextView) findViewById(R.id.titleTextView);

        if (callerPageIndex > 0) {
            lauchRegistration();
        } else if (callerPageIndex == 0) {
            registerButton = (Button) findViewById(R.id.divRegistration);
            registerButton.setEnabled(false);

            titleTextView.setText(callerData);
        }
        Button button = (Button) findViewById(R.id.Home_button);

        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    Intent intent = new Intent(getApplicationContext(), FullscreenActivity.class);
                    startActivity(intent);
                }
            });

        }//--onCreate---
    }


    protected static String checkRegistration(File getFilesDir) {
        try {
            //getFilesDir()
            phoneId = readFile(getFilesDir, phoneIdFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phoneId;
    }


    private void lauchRegistration() {
        registerButton = (Button)findViewById(R.id.divRegistration);

        phoneId = checkRegistration(getFilesDir());

        if (phoneId == null) {
            // Register the onClick listener with the implementation above
            if (registerButton != null) {
                registerButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        registerButton.setEnabled(false);
                        deviceRegistration();
                    }
                });
            }//if----

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // Camera permission has not been granted.
                requestPermission();
            } else {
                // Camera permissions is already available, show the camera preview.
                Log.i("Granted permission", "..Already Granted..");
            }
            pubnubHelper = new PubnubHelper();

        } else {
            registerButton.setEnabled(false);
            titleTextView.setText("Phone registered with Registration number :"+phoneId);
        }
    }


    private void deviceRegistration() {
        Log.i(" deviceRegistration() ", " Invoked...");

        TelephonyManager telephonyManager  = (TelephonyManager)getSystemService( Context.TELEPHONY_SERVICE );
        String deviceUUID = null;
       /*
        * getDeviceId() function Returns the unique device ID.
        * for example,the IMEI for GSM and the MEID or ESN for CDMA phones.
        */
        try {
            deviceUUID = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(" deviceUUID : ", deviceUUID);

        if(deviceUUID != null && !deviceUUID.isEmpty()) {
            pubnubHelper.subscribeString(deviceUUID+"_ch", subscribeCallback);
            pubnubHelper.publishString("GR_ch", deviceUUID);
        }
        titleTextView.setText("Registration of Device UUID " + deviceUUID + " in progress...");
    }


    //----------- Pubnub Section ----------------------------------------------------------------
    private Callback subscribeCallback = new Callback() {
        @Override
        public void successCallback(String channel, Object message) {
        responseString = message.toString();

        Log.i("Registratn.subscribe()", " : Response received from backend :" + responseString);
        try {
          //  internalFileLocation =
                    saveFile(phoneIdFileName, responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pubnubHelper.unSubscribe(channel);

        try {
            String fileContent = readFile(getFilesDir(), phoneIdFileName);
            RegistrationSmartphoneActivity.setCallerPageIndex((short)0);
            RegistrationSmartphoneActivity.setCallerData("Registration Completed.\nRegistration number : " +fileContent);

            Intent intent = new Intent(getApplicationContext(), RegistrationSmartphoneActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    };


    //===============================================================================================================
    //----------------------------------------------------------------------------------------------------------
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i("Request permission", "....");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_READ) {

            // Received permission result for camera permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Log.i("Granted permission", "..Granted");
            } else {
                Log.i("Rejected permission", "..Rejected...");
            }
        }
    }
    //----------------------------------------------------------------------------------------------------------
    //===============================================================================================================

    //-----------------------------------------------------------------------------------

    private String saveFile(String fileNm, String data) {
        FileOutputStream outputStream = null;
        String fileLocation = null;
        try {
            outputStream = openFileOutput(fileNm, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
            fileLocation = getFilesDir().getAbsolutePath();
            Log.i("saveFile(): ", fileLocation+"| File saved success!!");
        } catch (Exception e) {
            Log.e("Exception saveFile(): ", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileLocation;
    }

    private static String readFile(File internalFilesDir, String fileName) {

        String fileLocation = internalFilesDir.getAbsolutePath();
        File file = new File(fileLocation, fileName);
        StringBuilder text = new StringBuilder();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            Log.i("readFile(): ", text.toString());
        }
        catch (IOException e) {
            Log.e("Exception readFile(): ", e.getMessage());
        } catch (Exception e) {
            Log.e("Exception readFile(): ", e.getMessage());
        }finally{
            try {
                br.close();
            } catch (IOException e) { }
        }
        return text.substring(0, text.length()-1);
    }


    public static short getCallerPageIndex() {
        return callerPageIndex;
    }
    public static void setCallerPageIndex(short callerPageIndex) {
        RegistrationSmartphoneActivity.callerPageIndex = callerPageIndex;
    }

    public static String getCallerData() {
        return callerData;
    }
    public static void setCallerData(String callerData) {
        RegistrationSmartphoneActivity.callerData = callerData;
    }

    public static String getPhoneId() {
        return phoneId;
    }

}
