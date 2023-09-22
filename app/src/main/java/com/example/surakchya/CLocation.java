package com.example.surakchya;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.LOCATION_SERVICE;

public class CLocation extends Location {
   private boolean bUseMetricUnits = false;
   // variable not needed
   // private LatLng latLng;
   // private LocationManager locationManager;
   private final long MIN_TIME = 1000; // 1 second
    private final long MIN_DIST = 5; // distance in meter (5 meters)
    public LatLng latLng;
    Location location;
   public CLocation(Location location){
       this(location, true);
   }
   public CLocation(Location location,boolean bUseMetricUnits){
       super(location);
       this.bUseMetricUnits = bUseMetricUnits;
   }

   // public CLocation(String provider) {
     //   super(provider);
    //}

    public boolean getUseMetricUnits(){
       return this.bUseMetricUnits;
   }

   public void setUseMetricUnits(boolean bUseMetricUnits){
       this.bUseMetricUnits = bUseMetricUnits;
   }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public double getLatitude() {
        return super.getLatitude();
    }

    @Override
    public double getLongitude() {
        return super.getLongitude();
    }

    public LatLng getLatLng() {
       // requestLocationUpdates(LocationManager.GPS_PROVIDER,);
        System.out.println(super.getLatitude());
        System.out.println(super.getLongitude());
       return this.latLng;
       // return  new LatLng(super.getLatitude(), super.getLongitude());
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public float distanceTo(Location dest) {
       float nDistance = super.distanceTo(dest);
       if(!this.getUseMetricUnits()){
           // Convert meter to feet
          nDistance = nDistance * 3.28083989501312f;
       }
       return nDistance;
       // return super.distanceTo(dest);
    }

    @Override
    public double getAltitude() {
        double nAltitude = super.getAltitude();
        if(!this.getUseMetricUnits()){
            // cenvert meters to feet
            nAltitude = nAltitude * 3.28083989501312d;
        }
        return nAltitude;

       // return super.getAltitude();
    }

    @Override
    public float getSpeed() {
        float nSpeed = super.getSpeed() * 3.6f;
        if(!this.getUseMetricUnits()){
            // Convert meters/second to miles/hr
            nSpeed = super.getSpeed() * 2.23693629f;
        }
        return nSpeed;
       // return super.getSpeed();
    }

    @Override
    public float getAccuracy() {
        float nAccuracy = super.getAccuracy();
        if(!this.getUseMetricUnits()){
            // Convert meters to feet
            nAccuracy = nAccuracy * 3.28083989501312f;
        }
        return nAccuracy;
       // return super.getAccuracy();
    }
     /*
    locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {

        }
    };
    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    // check permission is required  here, but for now try catch is used for catching the security exception
        try{
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
    }catch (SecurityException e){
        e.printStackTrace();
    }
      */
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            setLatLng(latLng);
            setLocation(location);
        }
    };
   // LocationManager locationManager = (LocationManager) setProvider();
    LocationManager locationManager;
    /*
    public LocationManager getLocationManager() {
        return locationManager;
    }
     */
    //   LocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);

    // LocationManager locationManager = (LocationManager) getLatLng(LOCATION_SERVICE);
   // LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    // check permission is required  here, but for now try catch is used for catching the security exception

     //   locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
}
