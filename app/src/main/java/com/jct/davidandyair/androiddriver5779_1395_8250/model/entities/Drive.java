package com.jct.davidandyair.androiddriver5779_1395_8250.model.entities;

import android.location.Address;
import android.location.Location;

import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.FireBaseBackend;

import java.sql.Time;
import java.util.Date;

public class Drive {
    public enum DriveStatus {
        AVAILABLE, IN_PROGRESS, FINISHED
    }

    private DriveStatus status;
    private Address source;
    private Address destination;
    private Date beginning;
    private Date end;
    private String name;
    private String phoneNumber;
    private String eMailAddress;
    private long driverId;

    public Drive()
    {
        this.status = null;
        this.source = null;
        this.destination = null;
        this.beginning = null;
        this.end = null;
        this.name = null;
        this.phoneNumber = null;
        this.eMailAddress = null;
        this.driverId = 0;
    }

    public Drive(DriveStatus status, Address source, Address destination, Time beginning, Time end,
                 String name, String phoneNumber, String eMailAddress, int driverId)
    {
        this.status = status;
        this.source = source;
        this.destination = destination;
        this.beginning = beginning;
        this.end = end;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.eMailAddress = eMailAddress;
        this.driverId = driverId;
    }

    //region sets
    public void setEnd(Date end) {
        this.end = end;
    }
    public void setDestination(Address destination) {
        this.destination = destination;
    }
    public void setBeginning(Date beginning) {
        this.beginning = beginning;
    }
    public void seteMailAddress(String eMailAddress) {
        this.eMailAddress = eMailAddress;
    }
    public void setSource(Address source) {
        this.source = source;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setStatus(DriveStatus status) {
        this.status = status;
    }
    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }
    //endregion

    //region gets
    public DriveStatus getStatus() {
        return status;
    }
    public Address getDestination() {
        return destination;
    }
    public Address getSource() {
        return source;
    }
    public String getName() {
        return name;
    }
    public String geteMailAddress() {
        return eMailAddress;
    }
    public Date getBeginning() {
        return beginning;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public Date getEnd() {
        return end;
    }
    public long getDriverId() {
        return driverId;
    }
    //endregion

}