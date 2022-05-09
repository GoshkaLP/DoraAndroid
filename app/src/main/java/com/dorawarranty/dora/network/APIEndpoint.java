package com.dorawarranty.dora.network;

import com.dorawarranty.dora.network.entity.AuthRequest;
import com.dorawarranty.dora.network.entity.ServerResponse;
import com.dorawarranty.dora.network.entity.ServerResponseArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIEndpoint {
    @GET("/api/users/email/availability/check/{email}")
    Call<ServerResponse> checkEmail(@Path("email") String email);

    @GET("/api/users/token/check")
    Call<ServerResponse> checkToken(@Header("Authorization") String bearer);

    @POST("/api/users/register/email")
    Call<ServerResponse> registerUser(@Body AuthRequest user);

    @POST("/api/users/auth/email")
    Call<ServerResponse> authUser(@Body AuthRequest user);

    @GET("/api/products/units")
    Call<ServerResponseArray> getUnits(@Header("Authorization") String bearer);

    @GET("/api/products/units/{unitId}")
    Call<ServerResponse> getUnit(@Header("Authorization") String bearer, @Path("unitId") int unitId);

    @GET("/api/warrantyClaim/status/{unitId}")
    Call<ServerResponse> getClaimStatus(@Header("Authorization") String bearer, @Path("unitId") int unitId);
}
