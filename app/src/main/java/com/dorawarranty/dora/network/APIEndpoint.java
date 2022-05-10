package com.dorawarranty.dora.network;

import com.dorawarranty.dora.network.entity.AuthRequest;
import com.dorawarranty.dora.network.entity.ChangePasswordRequest;
import com.dorawarranty.dora.network.entity.ScanQrRequest;
import com.dorawarranty.dora.network.entity.ServerResponse;
import com.dorawarranty.dora.network.entity.ServerResponseArray;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

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
    @Headers({"Connection: close"})
    Call<ServerResponse> getUnit(@Header("Authorization") String bearer, @Path("unitId") int unitId);

    @GET("/api/products/units/photo/{unitId}")
    Call<ResponseBody> getUnitPhoto(@Header("Authorization") String bearer, @Path("unitId") int unitId);

    @GET("/api/warrantyClaim/status/{unitId}")
    Call<ServerResponse> getClaimStatus(@Header("Authorization") String bearer, @Path("unitId") int unitId);

    @POST("/api/products/units/scan")
    Call<ServerResponse> scanQrUnit(@Header("Authorization") String bearer, @Body ScanQrRequest qrCode);

    @GET("/api/users/logout")
    Call<ServerResponse> logout(@Header("Authorization") String bearer);

    @POST("/api/users/change/password/email")
    Call<ServerResponse> changePassword(@Header("Authorization") String bearer, @Body ChangePasswordRequest changePassword);
}
