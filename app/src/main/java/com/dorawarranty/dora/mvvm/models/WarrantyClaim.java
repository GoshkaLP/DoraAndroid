package com.dorawarranty.dora.mvvm.models;

public class WarrantyClaim {
    private String status;

    public WarrantyClaim(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
