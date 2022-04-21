package com.dorawarranty.dora.network;

import com.dorawarranty.dora.network.entity.Auth;
import com.dorawarranty.dora.network.entity.ServerResponse;
import com.dorawarranty.dora.network.entity.Users;

import java.util.Map;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkLogic {

    private String baseUrl;
    private Retrofit mRetrofit;
    private APIEndpoint api;

    public NetworkLogic() {
        baseUrl = "https://dora.gmrybkin.com";
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = mRetrofit.create(APIEndpoint.class);
    }

    public void checkEmail(String email, Consumer<Boolean> Callback) {
//        APIEndpoint api = mRetrofit.create(APIEndpoint.class);

        api.checkEmail(email).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body().getMessage().equals("SUCCESS")) {
                    Callback.accept(true);
                } else {
                    Callback.accept(false);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }


    public void registerUser(String email, String password, Consumer<Users> Callback) {
        Auth user = new Auth(email, password);
        api.registerUser(user).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body().getMessage().equals("SUCCESS")) {
                    Map<String, Object> data = response.body().getData();
                    String token = (String) data.get("token");
                    String email = (String) data.get("email");
                    int id = (Integer) data.get("id");
                    Users user = new Users(id, email, token);
                    Callback.accept(user);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }
}
