package com.cts.gto.techngage.smartride.android;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.cts.gto.techngage.smartride.dataobj.StringData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Created by administrator on 6/6/2016.
 */
public class ConfirmedQrCodeActivity extends AppCompatActivity{
   private static String datafromQRactivity;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmqrcode);
        ImageView img2 = (ImageView)findViewById(R.id.confirmQrImgView);
        img2.setImageBitmap(genQRCode(getDatafromQRactivity()));
    }
    private Bitmap genQRCode(String data) {
        Bitmap bmp = null;
        BitMatrix byteMatrix = null;
        String qrFileName = "smartride_qrcodefin.png";
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

    public static String getDatafromQRactivity() {
        return datafromQRactivity;
    }

    public static void setDatafromQRactivity(String datafromQRactivity) {
        ConfirmedQrCodeActivity.datafromQRactivity = datafromQRactivity;
    }
}