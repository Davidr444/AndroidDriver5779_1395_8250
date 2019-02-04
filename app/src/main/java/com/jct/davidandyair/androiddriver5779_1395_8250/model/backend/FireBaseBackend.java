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

    //Constructor - Important
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

    private float calculateDistance(Address a, Location b){
        Location locationA = new Location("A");
        Location locationB = new Location("B");

        locationA.setLongitude(a.getLongitude());
        locationA.setLatitude(a.getLatitude());
        locationB.setLongitude(b.getLongitude());
        locationB.setLatitude(b.getLatitude());

        return locationA.distanceTo(locationB);
    }

    private float calculateDistance(Address a, Address b){
        Location locationA = new Location("A");
        Location locationB = new Location("B");

        locationA.setLongitude(a.getLongitude());
        locationA.setLatitude(a.getLatitude());
        locationB.setLongitude(b.getLongitude());
        locationB.setLatitude(b.getLatitude());

        return locationA.distanceTo(locationB);
    }

    @Override
    public void addDriver(final Driver driver) {
      String key =  driverRef.push().getKey();
      driver.setKey(key);

      driverRef.child(key).setValue(driver);
    }

    @Override
    public List<Drive> getUnhandledDrives(Action<Long> action){
      List<Drive> unhandledDrives = new ArrayList<>();
        for (Drive drive:drives) {
            if(drive.getStatus() == Drive.DriveStatus.AVAILABLE)
                unhandledDrives.add(drive);
        }

      return unhandledDrives;
    }

    @Override
    public List<Drive> getFinishedDrives(Action<Long> action) {
        List<Drive> finishedDrives = new ArrayList<>();
        for (Drive drive:drives) {
            if(drive.getStatus() == Drive.DriveStatus.FINISHED)
                finishedDrives.add(drive);
        }

        return finishedDrives;
    }

    @Override
    public List<Drive> getDriversDrives(final Driver driver, Action<Long> action) {

        List<Drive> driverDrives = new ArrayList<>();
        for (Drive drive:drives) {
            if(driver.getId() == drive.getDriverId())
                driverDrives.add(drive);
        }
        return driverDrives;
    }

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

    @Override
    public void updateDrive(final Drive toUpdate){
        final String key = toUpdate.getKey();
        drivesRef.child(key).setValue(toUpdate);
    }

    @Override
    public float getPrice(Address source, Address destination) {
        return calculateDistance(source, destination)/1000 * 5;
    }


}
