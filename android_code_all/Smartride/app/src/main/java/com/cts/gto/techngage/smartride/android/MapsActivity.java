package com.cts.gto.techngage.smartride.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//import com.cts.gto.pubnub.utils.common.CallbackPublish;
//import com.cts.gto.pubnub.utils.common.CallbackSubscribe;
//import com.cts.gto.pubnub.utils.core.PubnubUtils;
//import com.cts.gto.pubnub.utils.data.BothwayMessage;
//import com.cts.gto.techngage.adapter.MyListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

//import com.pubnub.api.PubnubException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.FileOutputStream;
import java.util.Map;


import com.cts.gto.techngage.smartride.android.DatepickerActivity;
import com.cts.gto.techngage.smartride.android.TimepickerActivity;



public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,OnMarkerClickListener {

    private GoogleMap mMap;
    private int i = 0;

    private long publishTimeStamp;
    private com.cts.gto.techngage.smartride.android.MapsActivity internalThis;

    private Map<LatLng,Integer> busStopIndex = new HashMap<LatLng, Integer>();

    private String destinationString;
    private String pickupString;

    private List<LatLng> latLngList = new ArrayList<LatLng>();
    private List<String> pickuDestLatLng = new ArrayList<String>(2);
    private boolean pickupflag;
    private boolean destinationflag;
    Marker myMarker;

    //private LatLng destLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        latLngList.add(0, null);
        latLngList.add(1, new LatLng(22.5691064, 88.43072));
        latLngList.add(2, new LatLng(22.4720561, 88.3894515));
        latLngList.add(3, new LatLng(22.5746246,88.4336007));
        latLngList.add(4, new LatLng(22.5802664,88.4382033));
        latLngList.add(5, null);
        latLngList.add(6, new LatLng(22.4672526, 88.4037101));
        latLngList.add(7, null);
        latLngList.add(8, new LatLng(22.5819592,88.4535483));
        latLngList.add(9, null);
        latLngList.add(10, new LatLng(22.513615, 88.4015));
        latLngList.add(11, new LatLng(22.5425823,88.3982223));

        i=0;

        for(LatLng latlang : latLngList) {
            busStopIndex.put(latlang, i);
            i++;
        }

       Button button1 = (Button)findViewById(R.id.next_button);
        Button button2 = (Button)findViewById(R.id.reset_button);
//
//        // Register the onClick listener with the implementation above
        if (button1 != null) {
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(pickuDestLatLng.size() == 2) {
                        TimepickerActivity.setLastPageData(pickuDestLatLng);
                    }
                    Intent intent = new Intent(internalThis, DatepickerActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pickuDestLatLng.clear();
                    myMarker.remove();
                    onMapReady(mMap);
//                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
//                    startActivity(intent);
                }
            });
        }
//        button2.setOnClickListener(myButtonListener);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        internalThis = this;


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(1);


        // Add a marker in Sydney and move the camera
       // LatLng latLng = new LatLng(-34, 151);
       //
        googleMap.setOnMarkerClickListener(this);

        for(LatLng latlon : latLngList){

          if(latlon != null){
              mMap.addMarker(new MarkerOptions()
                      .position(latlon)
                      .title("Bus stop")
                      .snippet("This is my spot!")
                      .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

              mMap.moveCamera(CameraUpdateFactory.newLatLng(latlon));
          }
        }

       // setUpMarker(mMap);
    }




    private void selectMarker(GoogleMap googleMap, LatLng latLng1)
    {
if(pickuDestLatLng.size() == 0){
    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.position(latLng1)
            .title("Selected Bus stop").visible(true)
            .snippet("pickup point!")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));


    myMarker = googleMap.addMarker(markerOptions);

    myMarker.showInfoWindow();
}
       else if(pickuDestLatLng.size() == 1){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng1)
                    .title("Selected Bus stop").visible(true)
                    .snippet("destination point!")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


            myMarker = googleMap.addMarker(markerOptions);

            myMarker.showInfoWindow();

        }
//        //GoogleMap googleMap = new GoogleMap();
//
//    googleMap.setOnMarkerClickListener(this);


}

    //--test-------
    String[] latlngarray = new String[2];


    @Override
    public boolean onMarkerClick(final Marker marker) {
        StringBuilder sb = new StringBuilder();

//       String lat = String.valueOf( marker.getPosition().latitude);
//        String lon = String.valueOf( marker.getPosition().longitude);

        Log.i("Marker ALFA :", "" + marker.getAlpha());

        if(pickuDestLatLng.size() < 2 ) {

            selectMarker(mMap, marker.getPosition());
            pickuDestLatLng.add("" + busStopIndex.get(marker.getPosition()));

            //Toast.makeText(this,"Pickup Point selected "+String.valueOf(pickupIndex), Toast.LENGTH_LONG).show();
        }

        return true;
    }

    private String fileLocation = null;

    private void saveFile(String fileNm, String data) {
        FileOutputStream outputStream = null;

        try {
            outputStream = openFileOutput(fileNm, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
            fileLocation = getFilesDir().getAbsolutePath();
            Log.i("saveFile(): ", fileLocation+"| File saved success!!");
        } catch (Exception e) {
            Log.e("Exception saveFile(): ", e.getMessage());
            e.printStackTrace();
        }
    }

    private void readFile() {

//Get the text file
        File file = new File(fileLocation, "TechNgageSmartRide.txt");

//Read text from file
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
    }

//---------------------------

}
