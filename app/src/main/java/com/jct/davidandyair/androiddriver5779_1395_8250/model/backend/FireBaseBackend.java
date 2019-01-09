package com.jct.davidandyair.androiddriver5779_1395_8250.model.backend;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireBaseBackend implements IBackend {
    FirebaseDatabase database;

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

  @Override
    public void addDriver(final Driver drive, final Action<Long> action) {
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
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
    public ArrayList<Driver> getDrivers(final Action<Long> action) {
        // Get the Firebase reference
        database = FirebaseDatabase.getInstance();
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
