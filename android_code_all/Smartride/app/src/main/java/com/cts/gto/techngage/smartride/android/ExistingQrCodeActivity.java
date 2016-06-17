package com.cts.gto.techngage.smartride.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cts.gto.techngage.smartride.dataobj.StringData;
import com.cts.gto.techngage.smartride.pubnub.android.PubnubHelper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.pubnub.api.Callback;

/**
 * Created by administrator on 6/14/2016.
 */
public class ExistingQrCodeActivity extends AppCompatActivity {


    private PubnubHelper pubnubHelper;
    private String phoneChannel;
    private static String datafromExQRactivity;
    private String bookingNm;
    private  String routeId;
    private String bookingType;
    private String onboardStop;
    private String offboardStop;

    private StringData data;
    private ImageView img1;




    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existingqrcode);
        img1 = (ImageView)findViewById(R.id.existingqrImgView);
        String phoneId = RegistrationSmartphoneActivity.getPhoneId();
        phoneChannel = "ph-ch_" + phoneId;
        pubnubHelper = new PubnubHelper();
        try {
            data = new StringData(getDatafromExQRactivity());
            offboardStop = data.getValue(0, "OFFBOARD_STOP");
            onboardStop = data.getValue(0, "ONBOARD_STOP");
            bookingType = data.getValue(0, "BOOK_TYPE");
            bookingNm = data.getValue(0, "BOOK_NUM");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String messagedata = "Booking Number: "+bookingNm;

        if(bookingType.charAt(0)=='T'){

            messagedata = "OFFBOARD_STOP="+offboardStop+"|"+"ONBOARD_STOP="+onboardStop+"|"+"BOOK_NUM="+bookingNm+"|"+"BOOK_TYPE="+"T"+"|"+"PHONE_ID="+RegistrationSmartphoneActivity.getPhoneId();
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.existingQrCodeLayout);
            RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            param1.addRule(RelativeLayout.BELOW, img1.getId());

            Button button = new Button(this);
            button.setId(0+1);
            button.setText("GET CONFIRMED");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pubnubHelper.subscribeString(phoneChannel + "_resp", subscribeCallback);



                }
            });
            button.setLayoutParams(param1);
            relativeLayout.addView(button);


        }
        else if(bookingType.charAt(0)=='C') {
            messagedata = "OFFBOARD_STOP="+offboardStop+"|"+"ONBOARD_STOP="+onboardStop+"|"+"BOOK_NUM="+bookingNm +"|"+ "BOOK_TYPE=" + "C" + "|" + "PHONE_ID=" + RegistrationSmartphoneActivity.getPhoneId();
            RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.existingQrCodeLayout);
            RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            param2.addRule(RelativeLayout.BELOW, img1.getId());;
            Button button1 = new Button(this);
            button1.setId(0+1);
            button1.setText("TRACK ROUTE");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            button1.setLayoutParams(param2);
            relativeLayout1.addView(button1);
        }
        Log.i("QrCodeActivity:QR", " : Data set to QR :" + messagedata);
        img1.setImageBitmap(genQRCode(messagedata));

    }

    private Bitmap genQRCode(String data) {
        Bitmap bmp = null;
        BitMatrix byteMatrix = null;
        String qrFileName = "smartride_qrcode.png";
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }




            //((ImageView) findViewById(R.id.img_result_qr)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public static String getDatafromExQRactivity() {
        return datafromExQRactivity;
    }

    public static void setDatafromExQRactivity(String datafromExQRactivity) {
        ExistingQrCodeActivity.datafromExQRactivity = datafromExQRactivity;
    }
    //----------- Pubnub Section ----------------------------------------------------------------
    private Callback subscribeCallback = new Callback() {
        @Override
        public void successCallback(String channel, Object message) {
            String responseString = message.toString();
            Log.i("QrCodeActivity:", " : Response received from backend :" + responseString);
            try {

                StringData responseData = new StringData(responseString);
                String bookingType = responseData.getValue(0, "BOOK_TYPE");

                if(bookingType.charAt(0) == 'C'){
                    String  messagetoconQRdata = "OFFBOARD_STOP="+offboardStop+"|"+"ONBOARD_STOP="+onboardStop+"|"+"BOOK_NUM=" + bookingNm + "|" + "BOOK_TYPE=" + "C" + "|" + "PHONE_ID=" + RegistrationSmartphoneActivity.getPhoneId();

                    ConfirmedQrCodeActivity.setDatafromQRactivity(messagetoconQRdata);
                    Intent intent = new Intent(getApplicationContext(), ConfirmedQrCodeActivity.class);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            pubnubHelper.unSubscribe(channel);
        }
    };

    //-----------------------------------------------------------------------------------

}