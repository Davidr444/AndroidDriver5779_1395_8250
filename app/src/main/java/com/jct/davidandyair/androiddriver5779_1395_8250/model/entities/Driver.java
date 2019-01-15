package com.jct.davidandyair.androiddriver5779_1395_8250.model.entities;

import android.location.Address;


public class Driver {
    private String lastName;
    private String firstName;
    private long id;
    private String phoneNumber;
    private String emailAddress;
    private int creditCardNumber;
    private String hashedPassword;

    public Driver()
    {
        lastName = null;
        firstName = null;
        phoneNumber = null;
        emailAddress = null;
        // id and creditCardNumber are integers and they are automatically initialized with a grabage value!
    }
    public Address getAddress(){
    //TODO: implement this function to get the address of the driver.
        return null;
    }

    //region gets
    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public long getId() {
        return id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    //endregion

    //region sets
    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        //TODO: HASH THE PASSWORD
        this.hashedPassword = hashedPassword;
    }
    //endregion

}