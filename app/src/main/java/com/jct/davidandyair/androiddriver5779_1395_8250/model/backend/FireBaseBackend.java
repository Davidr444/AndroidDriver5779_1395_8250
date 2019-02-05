package com.jct.davidandyair.androiddriver5779_1395_8250.model.backend;

import android.location.Address;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <h1>in this class we implement the functions of the backend using the firebase</h1></h1>
 * The FireBaseBackend class has implemented functions that
 * add drive to the firebase and get lists of drives and drivers.
 * This class can also manipulate the data and return constrainted lists if needed.
 * the output on the screen.
 * @author  Yair Ben David and David Rakovski
 * @version 1.0
 * @since   2019-02-04
 */

public class FireBaseBackend implements IBackend {

    //region Interfaces
    public interface Action<T>  {
        void onSuccess(T obj);
        void onFailure(Exception exception);
        void onProgress(String status, double percent);
    }

    //endregion

    //region NOTIFY DATA CHANGE
    public interface NotifyDataChange<T>  {
        void OnDataChanged(T obj);
        void onFailure(Exception exception);
    }

    private List<NotifyDataChange<List<Drive>>> listeners;

    public void addNotifyDataChangeListener(NotifyDataChange<List<Drive>> l)
    {
        listeners.add(l);
        l.OnDataChanged(drives);
    }
    public void removeNotifyDataChangeListener(NotifyDataChange<List<Drive>> l)
    {
        listeners.remove(l);
    }
    //endregion


    //region Lists
    private List<Driver> drivers = new ArrayList<Driver>();
    private List<Drive> drives = new ArrayList<Drive>();
    //endregion

    //region FireBase fields
    private static ChildEventListener drivesRefChildEventListener;
    private static ChildEventListener driversRefChildEventListener;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference driverRef = database.getReference("Drivers");
    private static DatabaseReference drivesRef = database.getReference("drives");
    //endregion

    /**
     * This method is used to create a nes instance of this class. This is
     * no parameters and no return values.
     */
    public FireBaseBackend()
    {
        listeners = new ArrayList<NotifyDataChange<List<Drive>>>();
        notifyToDriverList(new NotifyDataChange<List<Driver>>() {
            @Override
            public void OnDataChanged(List<Driver> obj) {
                drivers = obj;
            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
        notifyToDrivesList(new NotifyDataChange<List<Drive>>() {
            @Override
            public void OnDataChanged(List<Drive> obj) {

                drives = obj;
                if(listeners != null) {
                    for (NotifyDataChange<List<Drive>> l : listeners) {
                        l.OnDataChanged(drives);
                    }
                }
            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }

    //region Notify Functions
    public void notifyToDrivesList(final NotifyDataChange<List<Drive>> notifyDataChange){
        if (notifyDataChange != null)
        {
            if (drivesRefChildEventListener != null)
            {
                notifyDataChange.onFailure(new Exception("first unNotify drives list"));
                return;
            }
            drives.clear();
            drivesRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s)
                {
                    Drive drive = dataSnapshot.getValue(Drive.class);
                    String id = dataSnapshot.getKey();
                    try{drives.add(drive);}
                    catch(Exception e){e.printStackTrace();}
                    notifyDataChange.OnDataChanged(drives);
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot){}
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s)
                { }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyDataChange.onFailure(databaseError.toException());
                }
            };
            drivesRef.addChildEventListener(drivesRefChildEventListener);
        }
    }
    public void notifyToDriverList(final NotifyDataChange<List<Driver>> notifyDataChange){
        if (notifyDataChange != null)
        {
            if (driversRefChildEventListener != null)
            {
                notifyDataChange.onFailure(new Exception("first unNotify drivers list"));
                return;
            }
            drivers.clear();
            driversRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s)
                {
                    Driver driver = dataSnapshot.getValue(Driver.class);
                    String id = dataSnapshot.getKey();
                    try{drivers.add(driver);}
                    catch(Exception e){e.printStackTrace();}
                    notifyDataChange.OnDataChanged(drivers);
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot){}
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s)
                { }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyDataChange.onFailure(databaseError.toException());
                }
            };
            driverRef.addChildEventListener(driversRefChildEventListener);
        }
    }
    public static void stopNotifyToDrivesList(){
        if(drivesRefChildEventListener != null){
            drivesRef.removeEventListener(drivesRefChildEventListener);
            drivesRefChildEventListener = null;
        }
    }
    public static void stopNotifyToDriversList(){
        if(driversRefChildEventListener != null){
            driverRef.removeEventListener(driversRefChildEventListener);
            driversRefChildEventListener = null;
        }
    }
    //endregion

    /**
     * This method is used to calculate the distance between address and location.
     * @param a This is the first paramter to this method that describes the first address.
     * @param b This is the second paramter to this method that describes the location.
     * @return float number that tells the caller function the distacne betweeen the parameter.
     */
    private float calculateDistance(Address a, Location b){
        Location locationA = new Location("A");
        Location locationB = new Location("B");

        locationA.setLongitude(a.getLongitude());
        locationA.setLatitude(a.getLatitude());
        locationB.setLongitude(b.getLongitude());
        locationB.setLatitude(b.getLatitude());

        return locationA.distanceTo(locationB);
    }
    /**
     * This method is used to calculate the distance between two addresses.
     * @param a This is the first paramter to this method that describes the first address.
     * @param b This is the second paramter to this method that describes the second address.
     * @return float number that tells the caller function the distacne betweeen the address.
     */
    private float calculateDistance(Address a, Address b){
        Location locationA = new Location("A");
        Location locationB = new Location("B");

        locationA.setLongitude(a.getLongitude());
        locationA.setLatitude(a.getLatitude());
        locationB.setLongitude(b.getLongitude());
        locationB.setLatitude(b.getLatitude());

        return locationA.distanceTo(locationB);
    }

    /**
     * This method is used to push a new driver to the firebase in a case of a new driver that sign in to
     * our app. in this function we push the information of the driver to our backend.
     * @param driver This is the first paramter to this method that describes the driver details.
     * @return void - no return values.
     */
    @Override
    public void addDriver(final Driver driver) {
      String key =  driverRef.push().getKey();
      driver.setKey(key);

      driverRef.child(key).setValue(driver);
    }

    /**
     * This method is used in order to return a list of all the drives that has not been handeled yet.
     * @param action This paramter is used to allow the caller send us an "action" if "listening" was succesful or fail.
     * @return the list of drives that we got from the firebase.
     */
    @Override
    public List<Drive> getUnhandledDrives(Action<Long> action){
      List<Drive> unhandledDrives = new ArrayList<>();
        for (Drive drive:drives) {
            if(drive.getStatus() == Drive.DriveStatus.AVAILABLE)
                unhandledDrives.add(drive);
        }

      return unhandledDrives;
    }

    /**
     * This method is used in order to return a list of all the drives that has already been handeled and finished.
     * @param action This paramter is used to allow the caller send us an "action" if "listening" was succesful or fail.
     * @return the list of drives that we got from the firebase.
     */
    @Override
    public List<Drive> getFinishedDrives(Action<Long> action) {
        List<Drive> finishedDrives = new ArrayList<>();
        for (Drive drive:drives) {
            if(drive.getStatus() == Drive.DriveStatus.FINISHED)
                finishedDrives.add(drive);
        }

        return finishedDrives;
    }

    /**
     * This method is used in order to return a list of all the drives that belong to a specific driver.
     * @param driver this parameter describes the details of the driver that we're looking for his drives.
     * @param action This paramter is used to allow the caller send us an "action" if "listening" was succesful or fail.
     * @return the list of drives that we got from the firevbase.
     */
    @Override
    public List<Drive> getDriversDrives(final Driver driver, Action<Long> action) {

        List<Drive> driverDrives = new ArrayList<>();
        for (Drive drive:drives) {
            if(driver.getId() == drive.getDriverId())
                driverDrives.add(drive);
        }
        return driverDrives;
    }

    /**
     * This method is used in order to return a list of all the drives
     * that their destination is in a specific city.
     * @param address this parameter describes the city that we're looking for.
     * @param action This paramter is used to allow the caller send us an "action" if "listening" was succesful or fail.
     * @return the list of drives that we got from the firevbase.
     */
    @Override
    public List<Drive> getDrivesByCity(Address address, Action<Long> action) {
      List<Drive> cityDrives = getUnhandledDrives(null);
      List<Drive> toRemove = new ArrayList<Drive>();

        for (Drive drive:cityDrives) {
            if(address.getAddressLine(0) != drive.getDestination().getAddressLine(0))
                toRemove.add(drive);
        }

        cityDrives.removeAll(toRemove);
        return cityDrives;
    }


    /**
     * This method is used in order to return a list of all the drives that have a
     * specific diatance from the current location of the driver.
     * @param driver describes the driver that is connected to our app right now.
     * @param distance the wanted distance.
     * @param currentLocation the driver's current location.
     * @param action This paramter is used to allow the caller send us an "action" if the "listening" was succesful or fail.
     * @return the list of drives that we got from the firevbase.
     */
    @Override
    public List<Drive> getDrivesByDistance(Driver driver, float distance, Address currentLocation, Action<Long> action) {
      List<Drive> driverDrives = getUnhandledDrives(null);
        for (Drive drive:driverDrives) {
            if(calculateDistance(drive.getSource(), currentLocation) >= distance)
                driverDrives.remove(drive);
        }

        return driverDrives;
    }

    @Override
    public List<Drive> getDrivesByDate(Date date, Action<Long> action) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM//YYYY");
        List<Drive> returnVal = getFinishedDrives(null);
        List<Drive> toRemove = new ArrayList<Drive>();

        for (Drive drive:returnVal) {
            if(simpleDateFormat.format(drive.getBeginning()) != simpleDateFormat.format(date))
                toRemove.add(drive);
        }

        returnVal.removeAll(toRemove);
        return returnVal;
    }

    @Override
    public List<Drive> getDrivesByPrice(float price, Action<Long> action) {
      List<Drive> returnVal = getUnhandledDrives(null);
        for (Drive drive:returnVal) {
            float drivePrice = getPrice(drive.getSource(), drive.getDestination());
            if(drivePrice != price)
                returnVal.remove(drive);
        }

        return returnVal;
    }

    @Override
    public List<Driver> getDrivers(final Action<Long> action){

        return drivers;
    }

    @Override
    public void changeStatus(Drive drive, Drive.DriveStatus status){
        drive.setStatus(status);
        updateDrive(drive);
    }


    @Override
    public List<String> getDriversNames(final Action<Long> action){
        final List<String> driversNames = new ArrayList<String>();

        for (Driver driver:drivers) {
            String fullName = driver.getFirstName() + " " + driver.getLastName();
            driversNames.add(fullName);
        }

        return driversNames;
    }

    /**
     * This method is used in order to update a drive in the firebase
     * @param toUpdate This paramter describes the new drive that we want to update.
     * @return void - no return value
     */
    @Override
    public void updateDrive(final Drive toUpdate){
        final String key = toUpdate.getKey();
        drivesRef.child(key).setValue(toUpdate);
    }

    /**
     * This method is used in order to calculate the price of specific ride.
     * the ride is described by its source and destination addresses.
     * @param source describes the source of this ride.
     * @param destination describes the destination of this ride.
     * @return float number that tells the caller the price of this ride.
     */
    @Override
    public float getPrice(Address source, Address destination) {
        return calculateDistance(source, destination)/1000 * 5;
    }


}
