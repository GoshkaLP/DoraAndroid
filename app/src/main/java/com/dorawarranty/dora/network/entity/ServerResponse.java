package com.dorawarranty.dora.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ServerResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Map<String, Object> data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
