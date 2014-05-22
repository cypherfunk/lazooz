package com.iride.main;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iride.data.Config;
import com.iride.data.FBUserInfo;
import com.iride.data.UserInfo;
import com.iride.io.Response;
import com.iride.io.ServerResponseListener;
import com.iride.io.Serverconnector;
import com.iride.main.util.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iride.main.R;

import org.json.JSONObject;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ZoozActivity extends Activity implements LocationListener, ServerResponseListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    LocationManager locationManager;
    private String provider;
    Marker startPerc;
    Double prevLat=0.0, prevLon=0.0;
    UserInfo userInfo = UserInfo.getInstance();
    Integer User_ZOOZ = 0;
    private  Location lastLocation;
    private  double totalDistance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zooz);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });


        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });
        User_ZOOZ = GetParamInt("ZOOZ_AMOUNT");
        ShowUserZooz();
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.zooz_button).setOnTouchListener(mDelayHideTouchListener);
        try {
            // Loading map
//            initilizeMap();
            //user location lookup
            lookUpLocation();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(locationManager!=null)
            locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager!=null)
            locationManager.removeUpdates((LocationListener) this);
    }
    //user location lookup
    private void lookUpLocation() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabledGPS = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean enabledWiFi = service
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        // Check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabledGPS) {
            Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default

        Criteria criteria = new Criteria();
        provider = service.getBestProvider(criteria, false);
      //  locationManager.requestLocationUpdates(provider, 1000, 100, (LocationListener) this);
        Location location = service.getLastKnownLocation(provider);
        lastLocation =location;

        // Initialize the location fields
        if (location != null) {
//            Toast.makeText(this, "Selected Provider " + provider,
//                    Toast.LENGTH_SHORT).show();
            onLocationChanged(location);
        } else {

            //do something
        }

    }
    public void onLocationChanged(Location location) {
        double lat =  location.getLatitude();
        double lng = location.getLongitude();
        if(startPerc!=null)
            startPerc.remove();
//        Toast.makeText(this, "Location " + lat+","+lng,
//                Toast.LENGTH_LONG).show();
        LatLng coordinate = new LatLng(lat, lng);
//        Toast.makeText(this, "Location " + coordinate.latitude+","+coordinate.longitude,
//                Toast.LENGTH_LONG).show();
        /*
        startPerc = googleMap.addMarker(new MarkerOptions()
                .position(coordinate)
                .title("Its Me")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.facebook)));
                */
        if(prevLat != 0.0 && prevLon != 0.0)
        {
            int Ra = 6371000;
            double dlat = Math.toRadians((lat - prevLat));
            double dlon = Math.toRadians((lng - prevLon));
            lat = Math.toRadians(lat);
            lng = Math.toRadians(lng);
            double a = Math.sin(dlat/2)*Math.sin(dlat/2)+Math.sin(dlon/2) * Math.sin(dlon/2) * Math.cos(lat) * Math.cos(prevLat);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double d = Ra * c;
            //Toast.makeText(this, "Distance: " + d+" sessionkey: "+ userInfo.getSessionkey(),
            //        Toast.LENGTH_LONG).show();
            String strSessionkey = "";
            /*
            if(!FBUserInfo.getInstance().getId().equalsIgnoreCase(""))
                strSessionkey = FBUserInfo.getInstance().getId();
            else
            */
                strSessionkey = userInfo.getSessionkey();

            double distanceMeters = lastLocation.distanceTo(location);
            double distanceKm = distanceMeters / 1000f;
            // when the location change keep updating the total distance
            totalDistance += distanceKm;
          // lastLocation = location; // assign the current location to lastLocation.

            System.out.println("Distance:"+d);

            //if(d>200)

            TextView tv = (TextView) findViewById(R.id.zoozdistance_msg);
            tv.setText("D2="+distanceMeters);

            if(distanceMeters>100)
            {
                //user location update server call
                String url = Config.IRIDE_SERVER_URL + "gpsinfo?command=";
                String param = "{"+ "\'sessionkey\':"+"\'"+strSessionkey+"\',"+ "\'latitude\':" + "\'"+coordinate.latitude+"\',"
                        +"\'longitude\':"+"\'"+coordinate.longitude+"\'}";
                lastLocation = location; // assign the current location to lastLocation.
                url+=param;
                final Serverconnector connector = new Serverconnector(ZoozActivity.this, ZoozActivity.this, url, null,
                        Serverconnector.HTTP_GET_METHOD, Config.REQ_GPS, Serverconnector.HTTP_STRING);
                connector.start();
            }
        }
        prevLat = lat;
        prevLon = lng;
        // Showing the current location in Google Map
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));

        // Zoom in the Google Map
        //googleMap.animateCamera(CameraUpdateFactory.newLatLng(coordinate));
    }
    @Override
    public void serverResponse(Response response) {
        if(response.getStatus())
        {
            String strResponse = response.getData().toString();

            System.out.println(strResponse);

            try {
                JSONObject jobj = new JSONObject(strResponse);
                JSONObject detailObj = jobj.getJSONObject("details");
                System.out.println(detailObj.toString());
                User_ZOOZ = detailObj.getInt("ZOOZ AMOUNT");
                System.out.println("Received Zooz:"+User_ZOOZ);
                StoreParamInt("ZOOZ_AMOUNT", User_ZOOZ);

                ShowUserZooz();

            } catch (Exception ex) {
                //showDialog("Invalid mobile number.");
                ex.printStackTrace();
            }

        }
    }

    private boolean StoreParamInt(String key,Integer param)
    {

        SharedPreferences sharedPref = getSharedPreferences("ZOOZ_PARAM", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, param);

        // Commit the edits!
        editor.commit();
        return true;
    }
    private Integer GetParamInt(String key)
    {
        SharedPreferences settings = getSharedPreferences("ZOOZ_PARAM", 0);
        return settings.getInt(key, 0);
    }

    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    //user data read for registration
    private boolean  ShowUserZooz() {

        TextView tv = (TextView) findViewById(R.id.zoozcounter);
        tv.setText("You got :" +User_ZOOZ +" ZOOZi");

        tv = (TextView) findViewById(R.id.zoozcounter_msg);
        tv.setText("Keep moving!");

        return true;
    }
}
