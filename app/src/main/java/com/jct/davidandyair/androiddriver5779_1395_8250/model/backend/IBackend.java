package com.jct.davidandyair.androiddriver5779_1395_8250.model.backend;

import android.location.Address;

import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IBackend {

    List<Driver> getDrivers(final FireBaseBackend.Action<Long> action);
    void addDriver(final Driver d);
    List<Drive> getUnhandledDrives(final FireBaseBackend.Action<Long> action);
    List<Drive> getFinishedDrives(final FireBaseBackend.Action<Long> action);
    List<Drive> getDriversDrives(final Driver d, final FireBaseBackend.Action<Long> action);
    List<Drive> getDrivesByCity(Address address, final FireBaseBackend.Action<Long> action);
    List<Drive> getDrivesByDistance(final Driver d, final float distance, Address currentLoaction, final FireBaseBackend.Action<Long> action);
    List<Drive> getDrivesByDate(final Date date, final FireBaseBackend.Action<Long> action);
    List<Drive> getDrivesByPrice(final float price, final FireBaseBackend.Action<Long> action);
    List<String> getDriversNames(final FireBaseBackend.Action<Long> action);
    void changeStatus(Drive drive, Drive.DriveStatus status);
    void updateDrive(final Drive toUpdate);
    float getPrice(Address source, Address destination);
    public void notifyToDrivesList(final FireBaseBackend.NotifyDataChange<List<Drive>> notifyDataChange);
    public void addNotifyDataChangeListener(FireBaseBackend.NotifyDataChange<List<Drive>> l);
    public void removeNotifyDataChangeListener(FireBaseBackend.NotifyDataChange<List<Drive>> l);
}
