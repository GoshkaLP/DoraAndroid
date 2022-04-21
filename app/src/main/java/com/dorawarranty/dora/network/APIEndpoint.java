package com.dorawarranty.dora.network;

import com.dorawarranty.dora.network.entity.Auth;
import com.dorawarranty.dora.network.entity.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIEndpoint {
    @GET("/api/users/email/availability/check/{email}")
    Call<ServerResponse> checkEmail(@Path("email") String email);

    @POST("/api/users/register/email")
    Call<ServerResponse> registerUser(@Body Auth user);
}
