package com.jct.davidandyair.androiddriver5779_1395_8250.model.entities;

import java.io.Serializable;


public class Driver implements Serializable {
    private String lastName;
    private String firstName;
    private long id;
    private String phoneNumber;
    private String emailAddress;
    private int creditCardNumber;
    private String password;
    private String key;

    public Driver()
    {
        lastName = null;
        firstName = null;
        phoneNumber = null;
        emailAddress = null;
        // id and creditCardNumber are integers and they are automatically initialized with a grabage value!
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    //endregion

}
