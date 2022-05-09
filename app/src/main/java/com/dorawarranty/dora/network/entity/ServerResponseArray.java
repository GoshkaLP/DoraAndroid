package com.dorawarranty.dora.network.entity;

import com.dorawarranty.dora.mvvm.models.WarrantyUnit;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ServerResponseArray {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Map<String, Object>> data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }
}
