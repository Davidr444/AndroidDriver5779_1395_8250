package com.jct.davidandyair.androiddriver5779_1395_8250.model.backend;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class CurrentLocation {
    // Acquire a reference to the system Location Manager
    LocationManager locationManager;
    // Define a listener that responds to location updates
    LocationListener locationListener;

    Context context;

    Boolean isGPSEnabled;
    Boolean isNetworkEnabled;

    public Location currentLocation;

    public CurrentLocation(Context _context)
    {
        context = _context;
        currentLocation = null;

        TurnOnLocation();

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                currentLocation = location;
            }
            public void onStatusChanged(String provider, int status, Bundle extras) { }
            public void onProviderEnabled(String provider) { }
            public void onProviderDisabled(String provider) { }
        };
    }

    /*Checks whether the location is turned on and has
     * permissions for everything you need, and if not,
     * turns on location and prompts for permissions
     */
    public void TurnOnLocation() {
        int locationOff = 0;
        try {
            //gets the status mode of the location's settings in the device
            locationOff = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        //if the GPS in the the device is off
        if (locationOff == 0) {
            //Moves to the device's location settings and asks to enable the GPS
            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            Toast.makeText(context, "Please turn on your GPS location", Toast.LENGTH_SHORT).show();
            context.startActivity(onGPS);
        } else {
            try {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                }else{
                    isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    if (!isGPSEnabled && !isNetworkEnabled) {
                        Toast.makeText(context, "Please Set Network/GPS ON", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isNetworkEnabled)
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    if (isGPSEnabled)
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            } catch(Exception e){
                Toast.makeText(context, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    static public
}
