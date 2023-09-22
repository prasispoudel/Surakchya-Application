package com.example.surakchya;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class userLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private static final String TAG = "userLocation";
    private final long MIN_TIME = 1000; // 1 second
    private final long MIN_DIST = 5; // distance in meter (5 meters)
    public LatLng latLng; // object of Latlng class for storing lattitude and longitude
    private Marker currentLocationmMarker;
    private GoogleApiClient client;
    // private LocationRequest locationRequest;
    private Location lastlocation;
    private int locationReviced;
    int PROXIMITY_RADIUS = 10000;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);
       // Button button = (Button) findViewById(R.id.button);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // directly requesting permission for accessing  users location
        // checkpermission is nesesary here in the later versions of the application
        // requesting  permission for Access_fine_Location
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
       // requesting permission for Access_coarse_location
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

    }
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
     */

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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
           //  bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        // Add a marker in Sydney and move the camera
      //  LatLng sydney = new LatLng(-34, 151);
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       /*
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
               latLng = new LatLng(location.getLatitude(), location.getLongitude());
                // latLng = new CLocation(LocationManager.GPS_PROVIDER).getLatLng();
                mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        };
        */
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                latLng = new LatLng(location.getLatitude(),location.getLongitude());
                // Log.d()
                lastlocation = location;
                Log.d(TAG,"Location:- "+ latLng.latitude+","+latLng.longitude);
               // mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
              //  mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                MarkerOptions markerOptions = new MarkerOptions();
               markerOptions.position(latLng);
                markerOptions.title("My Location");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                currentLocationmMarker = mMap.addMarker(markerOptions);
               // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
              //  mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
                mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
                locationReviced ++;
                if(locationReviced == 1){
                    Object dataTransfer[] = new Object[2];
                    GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
           
                    // String police = "police station";
                    String url = getUrl(latitude,longitude,"police station");
                    dataTransfer[0] = mMap;
                    dataTransfer[1]= url;

                    getNearbyPlacesData.execute(dataTransfer);
                    Toast.makeText(userLocation.this,"Showing nearby police Stations",Toast.LENGTH_LONG).show();
                }

            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
         // check permission is required  here, but for now try catch is used for catching the security exception
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }

        /*
            List<Address> addressList;
            if(!lastlocation.equals("")){
                Geocoder geocoder = new Geocoder(this);
                try{
                    addressList = geocoder.getFromLocationName(lastlocation.toString(),5);
                    if(addressList != null){
                        for(int i=0;i<addressList.size();i++){
                            LatLng latLng = new LatLng(addressList.get(i).getLatitude(),addressList.get(i).getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);
                            markerOptions.title(lastlocation.toString());
                            mMap.addMarker(markerOptions);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            */


        // mMap.clear();



    }
    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+"policestation");
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+getString(R.string.google_maps_key));
       //  googlePlaceUrl.append("&key="+"AIzaSyCIy-ZIyrVjHlE3Bbfz9n89gx5xCICHpE4");
        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    /*
    private synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();
    }
     */
}