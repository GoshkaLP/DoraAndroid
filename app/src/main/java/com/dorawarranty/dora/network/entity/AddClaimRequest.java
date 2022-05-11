package com.dorawarranty.dora.network.entity;

import com.google.gson.annotations.SerializedName;

public class AddClaimRequest {
    @SerializedName("unit_id")
    private int unitId;

    @SerializedName("service_center_id")
    private int serviceCenterId;

    @SerializedName("problem")
    private String problem;

    public AddClaimRequest(int unitId, int serviceCenterId, String problem) {
        this.unitId = unitId;
        this.serviceCenterId = serviceCenterId;
        this.problem = problem;
    }
}
