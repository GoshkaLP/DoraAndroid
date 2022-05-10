package com.dorawarranty.dora.network.entity;

import com.google.gson.annotations.SerializedName;

public class ScanQrRequest {

    @SerializedName("qr")
    private String qrCode;

    public ScanQrRequest(String qrCode) {
        this.qrCode = qrCode;
    }


}
