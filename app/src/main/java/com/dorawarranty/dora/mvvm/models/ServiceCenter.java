package com.dorawarranty.dora.mvvm.models;

public class ServiceCenter {

    private int serviceCenterId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    public ServiceCenter(int serviceCenterId, String name, String address, double latitude, double longitude) {
        this.serviceCenterId = serviceCenterId;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(int serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }
}
