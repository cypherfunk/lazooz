package com.iride.main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.zxing.WriterException;
import com.iride.data.Config;
import com.iride.data.UserInfo;
import com.iride.io.Response;
import com.iride.io.ServerResponseListener;
import com.iride.io.Serverconnector;
import com.iride.main.util.QrUtils;
import com.iride.main.util.SystemUiHider;

import org.json.JSONObject;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see com.iride.main.util.SystemUiHider
 */
public class WalletActivity extends Activity  {




    String usrPubKey = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wallet);





        StoreParamString("PUB_KEY", "1L4nd28BM64EtqhaWfR9P6V5SqPuq468om");
        usrPubKey = GetParamString("PUB_KEY");
        showUserPubKey();
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.zooz_button).setOnTouchListener(mDelayHideTouchListener);

    }

    public void scanQrOnClick(View e){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }






    private boolean StoreParamString(String key,String param)
    {

        SharedPreferences sharedPref = getSharedPreferences("ZOOZ_PARAM", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, param);

        // Commit the edits!
        editor.commit();
        return true;
    }
    private String GetParamString(String key)
    {
        SharedPreferences settings = getSharedPreferences("ZOOZ_PARAM", 0);
        return settings.getString(key, "");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    //user data read for registration
    private boolean  showUserPubKey() {

        TextView tv = (TextView) findViewById(R.id.pubKeyTextBox);
        tv.setText(usrPubKey);
        ImageView iv = (ImageView) findViewById(R.id.pubQrImage);
        try {
            QrUtils.createQrFromString(usrPubKey,iv,250,250);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        return true;
    }
}
