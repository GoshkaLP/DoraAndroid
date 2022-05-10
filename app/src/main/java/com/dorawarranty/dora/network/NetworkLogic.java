package com.dorawarranty.dora.network;

import android.util.Log;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.network.entity.AuthRequest;
import com.dorawarranty.dora.network.entity.ChangePasswordRequest;
import com.dorawarranty.dora.network.entity.ScanQrRequest;
import com.dorawarranty.dora.network.entity.ServerResponse;
import com.dorawarranty.dora.network.entity.ServerResponseArray;

import java.util.function.Consumer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkLogic {

    private String baseUrl;
    private Retrofit mRetrofit;
    private APIEndpoint api;
    private ServiceLocator mServiceLocator;

    public NetworkLogic() {
//        baseUrl = "https://dora.gmrybkin.com";
        baseUrl = "http://10.0.2.2:8086";
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = mRetrofit.create(APIEndpoint.class);
        mServiceLocator = ServiceLocator.getInstance();
    }

    public void checkEmail(String email, Consumer<ServerResponse> Callback) {

        api.checkEmail(email).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("requestError", "Connection error " + t.getMessage());
            }
        });
    }


    public void registerUser(String email, String password, Consumer<ServerResponse> Callback) {
        AuthRequest user = new AuthRequest(email, password);
        api.registerUser(user).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("requestError", "Connection error " + t.getMessage());
            }
        });
    }

    public void checkToken(Consumer<ServerResponse> Callback) {
        api.checkToken(mServiceLocator.getSecurityService().getToken()).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("requestError", "Connection error " + t.getMessage());
            }
        });
    }

    public void authUser(String email, String password, Consumer<ServerResponse> Callback) {
        AuthRequest user = new AuthRequest(email, password);
        api.authUser(user).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("requestError", "Connection error " + t.getMessage());
            }
        });
    }

    public void getUnits(Consumer<ServerResponseArray> Callback) {
        api.getUnits(mServiceLocator.getSecurityService().getToken()).enqueue(new Callback<ServerResponseArray>() {
            @Override
            public void onResponse(Call<ServerResponseArray> call, Response<ServerResponseArray> response) {
                Callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponseArray> call, Throwable t) {
                Log.e("requestError", "Connection error " + t.getMessage());
            }
        });
    }

    public void getUnit(int unitId, Consumer<ServerResponse> Callback) {
        api.getUnit(mServiceLocator.getSecurityService().getToken(), unitId).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("requestError", "Connection error " + t.getMessage());
            }
        });
    }

    public void getUnitPhoto(int unitId, Consumer<ResponseBody> Callback) {
        api.getUnitPhoto(mServiceLocator.getSecurityService().getToken(), unitId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("requestError", "Connection error " + t.getMessage());
            }
        });
    }

    public void getClaimStatus(int unitId, Consumer<ServerResponse> Callback) {
        api.getClaimStatus(mServiceLocator.getSecurityService().getToken(), unitId).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("requestError", "Connection error " + t.getMessage());
            }
        });
    }

    public void scanQrUnit(String code, Consumer<ServerResponse> Callback) {
        ScanQrRequest request = new ScanQrRequest(code);
        api.scanQrUnit(mServiceLocator.getSecurityService().getToken(), request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("requestError", "Connection error " + t.getMessage());
            }
        });
    }

    public void logout(Consumer<ServerResponse> Callback) {
        api.logout(mServiceLocator.getSecurityService().getToken()).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("requestError", "Connection error " + t.getMessage());
            }
        });
    }

    public void changePassword(String oldPassword, String newPassword, Consumer<ServerResponse> Callback) {
        ChangePasswordRequest form = new ChangePasswordRequest(oldPassword, newPassword);
        api.changePassword(mServiceLocator.getSecurityService().getToken(), form).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("requestError", "Connection error " + t.getMessage());
            }
        });
    }
}
