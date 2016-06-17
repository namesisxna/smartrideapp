package com.cts.gto.busscanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;



import com.cts.gto.techngage.smartride.dataobj.StringData;
import com.google.zxing.client.android.Intents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import pubnub.android.PubnubHelper;


public class BusScanner extends Activity {
	private pubnub.android.PubnubHelper pubnubHelper = null;
	private static String phoneChannel;
	private static String phoneId;
	Intents intents;


	/**
	 * Called when the activity is first created.
	 */


	//----------------------------------------------


	//-----------------------------------------

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void scanBar(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {
			showDialog(BusScanner.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
		}
	}

	public void scanQR(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {
			showDialog(BusScanner.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
		}
	}

	private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
		downloadDialog.setTitle(title);
		downloadDialog.setMessage(message);
		downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
				Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				try {
					act.startActivity(intent);
				} catch (ActivityNotFoundException anfe) {

				}
			}
		});
		downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
			}
		});
		return downloadDialog.show();
	}
	private String destinationStopId ="1";
	private String bookType;
	private String bookNum;
	private String offboardStop;
	private  String onboardStop;
	StringBuilder reqStringB = null;
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				try {
					StringData stringData = new StringData(contents);

					phoneId = stringData.getValue(0, "PHONE_ID");
					phoneChannel = "ph-ch_" + phoneId;
					bookType = stringData.getValue(0, "BOOK_TYPE");
					bookNum = stringData.getValue(0, "BOOK_NUM");
					offboardStop = stringData.getValue(0, "OFFBOARD_STOP");
					onboardStop = stringData.getValue(0,"ONBOARD_STOP");

				} catch (Exception e) {
					e.printStackTrace();
				}


				 reqStringB = new StringBuilder();

				reqStringB.append("OPRN=").append("busstopBackEndOPRN").append('|');

				reqStringB.append("BOOK_NUM=").append(bookNum).append('|');
				reqStringB.append("BOOKING_TYPE=").append(bookType).append('|');
				reqStringB.append("BOOKING_STATUS=").append('A').append('|');
				reqStringB.append("DEVICE_ID=").append(phoneId);
				if(bookType.charAt(0)=='T'){
					TextView textView = (TextView) findViewById(R.id.textView2);
					textView.setText("BookingNum :"+bookNum+" "+" Booking type tentative"+" Bus gate will not open");
				}
				if(bookType.charAt(0)=='C'){
					TextView textView = (TextView) findViewById(R.id.textView2);
					textView.setText("BookingNum :"+bookNum+" "+" Booking type Confirmed "+" Bus gate is opening");

				}





//				Toast toast = Toast.makeText(this, "phoneId:" + phoneId + " Format:" + format, Toast.LENGTH_LONG);
//				toast.show();
			}

		}
//		pubnubHelper = new PubnubHelper();
//		pubnubHelper.publishString(phoneChannel, reqStringB.toString());
//		Log.i("BusScanner.publish:", " data sent to backend..");



	}




}