package com.jct.davidandyair.androiddriver5779_1395_8250.model.entities;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyAddress extends Address {

    public MyAddress()
    {
        super(null);
    }

    @Override
    public String toString () {
        return getPlace(AddressToLocation(this));
    }

    //region Help Functions
    public String getPlace(Location location) {

        Geocoder geocoder = new Geocoder(null, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses.size() > 0) {
                String cityName = addresses.get(0).getAddressLine(0);
                //  String stateName = addresses.get(0).getAddressLine(1);
                //  String countryName = addresses.get(0).getAddressLine(2);
                //  return stateName + "\n" + cityName + "\n" + countryName;
                return cityName;
            }

            return "no place: \n ("+location.getLongitude()+" , "+location.getLatitude()+")";
        }
        catch(
                IOException e)

        {
            e.printStackTrace();
        }
        return "IOException ...";
    }

    // this function converts address to location.
    private Location AddressToLocation(Address address){
        Location location = new Location("a");
        location.setLatitude(address.getLatitude());
        location.setLongitude(address.getLongitude());

        return location;
    }
    //endregion
}
