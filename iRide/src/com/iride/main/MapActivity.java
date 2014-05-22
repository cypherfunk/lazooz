package com.iride.main;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
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

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class MapActivity extends Activity implements LocationListener, ServerResponseListener{

	// Google Map
    private GoogleMap googleMap;
    LocationManager locationManager;
    private String provider;
    Marker startPerc;
    UserInfo userInfo = UserInfo.getInstance();
    Double prevLat=0.0, prevLon=0.0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		try {
            // Loading map
            initilizeMap();
            //user location lookup
            lookUpLocation();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Location location = service.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
//            Toast.makeText(this, "Selected Provider " + provider,
//                    Toast.LENGTH_SHORT).show();
            onLocationChanged(location);
        } else {

            //do something
        }
		
	}

	/**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
        if(locationManager!=null)
        	locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager!=null)
        	locationManager.removeUpdates((LocationListener) this);
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
        startPerc = googleMap.addMarker(new MarkerOptions()
        .position(coordinate)
        .title("Its Me")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.facebook)));
        if(prevLat != 0.0 && prevLon != 0.0)
        {
        	int R = 6371000;
        	double dlat = Math.toRadians((lat - prevLat));
        	double dlon = Math.toRadians((lng - prevLon));
        	lat = Math.toRadians(lat);
        	lng = Math.toRadians(lng);
        	double a = Math.sin(dlat/2)*Math.sin(dlat/2)+Math.sin(dlon/2) * Math.sin(dlon/2) * Math.cos(lat) * Math.cos(prevLat);
        	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        	double d = R * c;
        	//Toast.makeText(this, "Distance: " + d+" sessionkey: "+ userInfo.getSessionkey(),
            //        Toast.LENGTH_LONG).show();
        	String strSessionkey = "";
        	if(!FBUserInfo.getInstance().getId().equalsIgnoreCase(""))
        		strSessionkey = FBUserInfo.getInstance().getId();
        	else
        		strSessionkey = userInfo.getSessionkey();

        	if(d>200)
        	{
        		//user location update server call
        		String url = Config.IRIDE_SERVER_URL + "gpsinfo?command=";
        		String param = "{"+ "\'sessionkey\':"+"\'"+strSessionkey+"\',"+ "\'latitude\':" + "\'"+coordinate.latitude+"\',"
        				+"\'longitude\':"+"\'"+coordinate.longitude+"\'}";
        		url+=param;
        		final Serverconnector connector = new Serverconnector(MapActivity.this, MapActivity.this, url, null, 
        				Serverconnector.HTTP_GET_METHOD, Config.REQ_GPS, Serverconnector.HTTP_STRING);
        		connector.start();
        	}
        }
        prevLat = lat;
        prevLon = lng;
        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));

        // Zoom in the Google Map
        //googleMap.animateCamera(CameraUpdateFactory.newLatLng(coordinate));
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

	@Override
	public void serverResponse(Response response) {
		if(response.getStatus())
		{
//			Toast.makeText(this, "Status: true" ,
//                    Toast.LENGTH_LONG).show();
		}
	}

}
