package com.rrt.rrtbackend.entity.user;

public class UserAddress {
   private long id;
    private String name;
    private String number;
    private int pincode;
    private String state;
    private String city;
    private String addressLine1;
    private String addressLine2;
    private String addressType;
    private String landMark;

    public UserAddress(long id,String name, String number, int pincode, String state, String city, String addressLine1,
            String addressLine2, String addressType, String landMark) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.pincode = pincode;
        this.state = state;
        this.city = city;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressType = addressType;
        this.landMark = landMark;
    }

    public UserAddress(Address address) {
        this.id = address.getAddressId();
        this.name = address.getName();
        this.number = address.getNumber();
        this.pincode = address.getPincode();
        this.state = address.getState();
        this.city = address.getCity();
        this.addressLine1 = address.getAddressLine1();
        this.addressLine2 = address.getAddressLine2();
        this.addressType = address.getAddressType();
        this.landMark = address.getLandMark();
    }

    UserAddress() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
