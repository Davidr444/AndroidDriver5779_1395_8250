package com.jct.davidandyair.androiddriver5779_1395_8250.model.backend;

import android.location.Address;
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
import java.util.List;
import java.util.Map;

public class FireBaseBackend implements IBackend {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static ChildEventListener drivesRefChildEventList;
    //The list of the drives the will be return
    static ArrayList<Drive> driveList = new ArrayList<Drive>();

  public interface Action<T>
  {
      void onSuccess(T obj);
      void onFailure(Exception exception);
      void onProgress(String status, double percent);
  }
  public interface NotifyDataChange<T>
  {
      void OnDataChanged(T obj);
      void onFailure(Exception exception);
  }

  public static void notifyToDrivesList(final NotifyDataChange<List<Drive>> notifyDataChange){
      DatabaseReference DrivesRef = database.getReference("drives");
      if (notifyDataChange != null)
      {
          if (drivesRefChildEventList != null)
          {
              notifyDataChange.onFailure(new Exception("first unNotify student list"));
              return;
          }
          driveList.clear();
          drivesRefChildEventList = new ChildEventListener() {
              @Override
              public void onChildAdded(DataSnapshot dataSnapshot, String s)
              {
                  Drive drive = dataSnapshot.getValue(Drive.class);
                  String id = dataSnapshot.getKey();
                  drive.setId(Long.parseLong(id));
                  driveList.add(drive);
                  notifyDataChange.OnDataChanged(driveList);
              }
              @Override
              public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                  Drive drive = dataSnapshot.getValue(Drive.class);
                  Long id = Long.parseLong(dataSnapshot.getKey());
                  drive.setId(id);
                  for (int i = 0; i < driveList.size(); i++)
                  {
                      if (driveList.get(i).getId().equals(id))
                      {
                          driveList.set(i, student);
                          break;
                      }
                  }
                  notifyDataChange.OnDataChanged(driveList);
              }
              @Override
              public void onChildRemoved(DataSnapshot dataSnapshot)
              {
                  Drive drive = dataSnapshot.getValue(Drive.class);
                  Long id = Long.parseLong(dataSnapshot.getKey());
                  drive.setId(id);
                  for (int i = 0; i < driveList.size(); i++)
                  {
                      if (driveList.get(i).getId() ==  id)
                      {
                          driveList.remove(i);
                          break;
                      }
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

  @Override
    public void addDriver(final Driver drive, final Action<Long> action) {
        DatabaseReference myRef = database.getReference("drivers");

        myRef.push().setValue(drive).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(drive.getId());
                action.onProgress("Uploads driver data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload driver data", 100);
            }
        });
    }

    @Override
    public ArrayList<Drive> getUnhandledDrives(Action<Long> action) {
        return null;
    }

    @Override
    public ArrayList<Drive> getFinishedDrives(Action<Long> action) {
        return null;
    }

    @Override
    public ArrayList<Drive> getDriversDrives(Driver d, Action<Long> action) {
        return null;
    }

    @Override
    public ArrayList<Drive> getDrivesByCity(Address address, Action<Long> action) {
        return null;
    }

    @Override
    public ArrayList<Drive> getDrivesByDistance(Driver d, float distance, Action<Long> action) {
        return null;
    }

    @Override
    public ArrayList<Drive> getDrivesByDate(Date date, Action<Long> action) {
        return null;
    }

    @Override
    public ArrayList<Drive> getDrivesByPrice(float price, Action<Long> action) {
        return null;
    }

    @Override
    public ArrayList<Driver> getDrivers(final Action<Long> action) {

        DatabaseReference myRef = database.getReference("drivers");

        //The list of the drivers the will be return
        final ArrayList<Driver> list = new ArrayList<Driver>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Driver newDriver = postSnapshot.getValue(Driver.class);
                    list.add(newDriver);
                }
                action.onSuccess(null);
                action.onProgress("Getting the list of drivers", 100);
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
