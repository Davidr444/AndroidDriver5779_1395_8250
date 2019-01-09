package com.jct.davidandyair.androiddriver5779_1395_8250.model.backend;

import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.util.ArrayList;

public interface IBackend {
    public void addDriver(final Driver d, final FireBaseBackend.Action<Long> action);
    public ArrayList<Driver> getDrivers(  final FireBaseBackend.Action<Long> action);
}
