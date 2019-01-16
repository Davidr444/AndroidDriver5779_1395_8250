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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FireBaseBackend implements IBackend {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static ChildEventListener drivesRefChildEventList;
    //The list of the drives the will be return
    static ArrayList<Drive> driveList;

  public interface Action<T>  {
      void onSuccess(T obj);
      void onFailure(Exception exception);
      void onProgress(String status, double percent);
  }
  public interface NotifyDataChange<T>  {
      void OnDataChanged(T obj);
      void onFailure(Exception exception);
  }

  public static void notifyToDrivesList(final NotifyDataChange<List<Drive>> notifyDataChange){
      DatabaseReference DrivesRef = database.getReference("drives");
      if (notifyDataChange != null)
      {
          if (drivesRefChildEventList != null)
          {
              notifyDataChange.onFailure(new Exception("first unNotify drives list"));
              return;
          }
          driveList.clear();
          drivesRefChildEventList = new ChildEventListener() {
              @Override
              public void onChildAdded(DataSnapshot dataSnapshot, String s)
              {
                  Drive drive = dataSnapshot.getValue(Drive.class);
                  String id = dataSnapshot.getKey();
                  //drive.setId(Long.parseLong(id));
                  driveList.add(drive);
                  notifyDataChange.OnDataChanged(driveList);
              }
              @Override
              public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                  Drive drive = dataSnapshot.getValue(Drive.class);
                  Long id = Long.parseLong(dataSnapshot.getKey());
               //   drive.setId(id);
                  for (int i = 0; i < driveList.size(); i++)
                  {
                      //if (driveList.get(i).getId().equals(id))
                      //{
                      //    driveList.set(i, student);
                      //    break;
                      //}
                  }
                  notifyDataChange.OnDataChanged(driveList);
              }
              @Override
              public void onChildRemoved(DataSnapshot dataSnapshot)
              {
                  Drive drive = dataSnapshot.getValue(Drive.class);
                  Long id = Long.parseLong(dataSnapshot.getKey());
                 // drive.setId(id);
                  for (int i = 0; i < driveList.size(); i++)
                  {
                     // if (driveList.get(i).getId() ==  id)
                     // {
                      //    driveList.remove(i);
                      //    break;
                     // }
                  }
                  notifyDataChange.OnDataChanged(driveList);
              }
              @Override
              public void onChildMoved(DataSnapshot dataSnapshot, String s)
              { }
              @Override
              public void onCancelled(DatabaseError databaseError) {
                  notifyDataChange.onFailure(databaseError.toException());
              }
          };
          DrivesRef.addChildEventListener(drivesRefChildEventList);
      }
  }
  public static void stopNotifyToDrivesList(){}
  private void updateList(){
      notifyToDrivesList(new NotifyDataChange<List<Drive>>() {
          @Override
          public void OnDataChanged(List<Drive> obj) {
              driveList = new ArrayList<Drive>();
              for (Drive d:obj) {
                  driveList.add(d);
              }
          }

          @Override
          public void onFailure(Exception exception) {

          }
      });
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
        DatabaseReference myRef = database.getReference("drivers");

        myRef.push().setValue(driver);
    }

    @Override
    public ArrayList<Drive> getUnhandledDrives(Action<Long> action) {
      updateList();
      ArrayList<Drive> returnVal = new ArrayList<Drive>();
        for (Drive d : driveList) {
            if(d.getStatus() == Drive.DriveStatus.AVAILABLE)
                returnVal.add(d);
        }

      return returnVal;
    }

    @Override
    public ArrayList<Drive> getFinishedDrives(Action<Long> action) {
      updateList();
        ArrayList<Drive> returnVal = new ArrayList<Drive>();
        for (Drive d : driveList) {
            if(d.getStatus() == Drive.DriveStatus.FINISHED)
                returnVal.add(d);
        }

        return returnVal;
    }

    @Override
    public ArrayList<Drive> getDriversDrives(Driver driver, Action<Long> action) {
      updateList();
        ArrayList<Drive> returnVal = new ArrayList<Drive>();
        for (Drive d : driveList) {
            if(d.getDriverId() == driver.getId())
                returnVal.add(d);
        }

        return returnVal;
    }

    @Override
    public ArrayList<Drive> getDrivesByCity(Address address, Action<Long> action) {
      updateList();
        ArrayList<Drive> returnVal = new ArrayList<Drive>();
        for (Drive d : driveList) {
            if(d.getStatus() == Drive.DriveStatus.AVAILABLE && d.getDestination().getAddressLine(0) == address.getAddressLine(0))
                returnVal.add(d);
        }

        return returnVal;
      }

    @Override
    public ArrayList<Drive> getDrivesByDistance(Driver driver, float distance, Action<Long> action) {
      updateList();
        ArrayList<Drive> returnVal = new ArrayList<Drive>();
        for (Drive d : driveList) {
            if(calculateDistance(driver.getAddress(), d.getDestination()) == distance)
                returnVal.add(d);
        }

        return returnVal;
    }

    @Override
    public ArrayList<Drive> getDrivesByDate(Date date, Action<Long> action) {
        updateList();
        ArrayList<Drive> returnVal = new ArrayList<Drive>();
        for (Drive d:driveList) {
            if(d.getBeginning() == date)
                returnVal.add(d);
        }

        return returnVal;
    }

    @Override
    public ArrayList<Drive> getDrivesByPrice(float price, Action<Long> action) {
      updateList();
      ArrayList<Drive> returnVal = new ArrayList<Drive>();
        for (Drive d:driveList) {
            if((calculateDistance(d.getSource(), d.getDestination()) / 1000) * 2 == price)
                returnVal.add(d);
        }

        return returnVal;
    }

    @Override
    public ArrayList<Driver> getDrivers(final Action<Long> action) {

        DatabaseReference myRef = database.getReference("drivers");

        //The list of the drivers the will be return
        final ArrayList<Driver> list = new ArrayList<Driver>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator(); it.hasNext();) {
                    Driver newDriver = it.next().getValue(Driver.class);
                    list.add(newDriver);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               action.onFailure(null);
               action.onProgress("error getting the list of drivers", 100);
            }
        });


        return list;
    }
}
