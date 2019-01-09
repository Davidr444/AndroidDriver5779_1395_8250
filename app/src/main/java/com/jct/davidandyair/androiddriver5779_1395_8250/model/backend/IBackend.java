package com.jct.davidandyair.androiddriver5779_1395_8250.model.backend;

import android.location.Address;

import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.util.ArrayList;
import java.util.Date;

public interface IBackend {

    ArrayList<Driver> getDrivers(final FireBaseBackend.Action<Long> action);
    void addDriver(final Driver d, final FireBaseBackend.Action<Long> action);
    ArrayList<Drive> getUnhandledDrives(final FireBaseBackend.Action<Long> action);
    ArrayList<Drive> getFinishedDrives(final FireBaseBackend.Action<Long> action);
    ArrayList<Drive> getDriversDrives(final Driver d, final FireBaseBackend.Action<Long> action);
    ArrayList<Drive> getDrivesByCity(Address address, final FireBaseBackend.Action<Long> action);
    ArrayList<Drive> getDrivesByDistance(final Driver d, final float distance, final FireBaseBackend.Action<Long> action);
    ArrayList<Drive> getDrivesByDate(final Date date, final FireBaseBackend.Action<Long> action);
    ArrayList<Drive> getDrivesByPrice(final float price, final FireBaseBackend.Action<Long> action);
}
