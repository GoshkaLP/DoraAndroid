package com.dorawarranty.dora.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.annotation.IntRange;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.network.entity.AddClaimRequest;
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


    @IntRange(from = 0, to = 3)
    public static int getConnectionType(Context context) {
        int result = 0; // Returns connection type. 0: none; 1: mobile data; 2: wifi; 3: vpn
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    result = 2;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    result = 1;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    result = 3;
                }
            }
        }
        return result;
    }
    public static boolean isNetworkAvailable(Context context) {
        int type = getConnectionType(context);
        return type != 0;
    }

    public NetworkLogic() {
        baseUrl = "https://dora.gmrybkin.com";
//        baseUrl = "http://10.0.2.2:8086";
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
        api.getUnitPhoto(mServiceLocator.getSecurityService().getToken(), unitId).enqueue(new CallbackWithRetry<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Callback.accept(response.body());
            }

//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("requestError", "Connection error " + t.getMessage());
//            }
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

    public void getServiceCenters(int manufacturerId, Consumer<ServerResponseArray> Callback) {
        api.getServiceCenters(mServiceLocator.getSecurityService().getToken(), manufacturerId).enqueue(new Callback<ServerResponseArray>() {
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

    public void createClaim(int unitId, int serviceCenterId, String problem, Consumer<ServerResponse> Callback) {
        AddClaimRequest claim = new AddClaimRequest(unitId, serviceCenterId, problem);
        api.createClaim(mServiceLocator.getSecurityService().getToken(), claim).enqueue(new Callback<ServerResponse>() {
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
