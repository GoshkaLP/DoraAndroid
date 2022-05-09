package com.dorawarranty.dora.mvvm.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.mvvm.models.AuthResponse;

public class UsersRepository {

    private Context context;
    private ServiceLocator mServiceLocator;
    private MutableLiveData<AuthResponse> checkEmail = new MutableLiveData<>(new AuthResponse(3, "reset"));
    private MutableLiveData<Boolean> checkToken = new MutableLiveData<>();
    private MutableLiveData<AuthResponse> authResult = new MutableLiveData<>(new AuthResponse(2, "reset"));


    public UsersRepository(Context context) {
        this.context = context;
        mServiceLocator = ServiceLocator.getInstance();
//        mServiceLocator.getSecurityService().removeToken();
    }


    public MutableLiveData<AuthResponse> checkEmail(String email) {
        mServiceLocator.getNetworkLogic().checkEmail(email, result -> {
            String message = result.getMessage();
            if (message.equals("SUCCESS")) {
                checkEmail.setValue(new AuthResponse(0, "ok"));
            } else if (message.equals("ACCOUNT_ALREADY_EXISTS")) {
                checkEmail.setValue(new AuthResponse(1, "exists"));
            } else if (message.equals("WRONG_EMAIL_FORMAT")) {
                checkEmail.setValue(new AuthResponse(2, "Неверный формат e-mail"));
            }
        });
        return checkEmail;
    }

    public void resetCheckEmail() {
        checkEmail.setValue(new AuthResponse(3, "reset"));
    }

    public MutableLiveData<Boolean> checkToken() {
        mServiceLocator.getNetworkLogic().checkToken((result) -> {
            String message = result.getMessage();
            if (message.equals("WRONG_TOKEN")) {
                checkToken.setValue(false);
            } else if (message.equals("SUCCESS")) {
                checkToken.setValue(true);
            }
        });
        return checkToken;
    }

    public void registerEmail(String email, String password) {
        mServiceLocator.getNetworkLogic().registerUser(email, password, result -> {
            String message = result.getMessage();
            if (message.equals("SUCCESS")) {
                String token = (String) result.getData().get("token");
                mServiceLocator.getSecurityService().saveToken(token);
            }
        });
    }

    public MutableLiveData<AuthResponse> authEmail(String email, String password) {
        mServiceLocator.getNetworkLogic().authUser(email, password, result -> {
            String message = result.getMessage();
            if (message.equals("SUCCESS")) {
                String token = (String) result.getData().get("token");
                mServiceLocator.getSecurityService().saveToken(token);
                authResult.setValue(new AuthResponse(1, "ok"));
            } else if (message.equals("WRONG_PASSWORD")) {
                authResult.setValue(new AuthResponse(0, "Вы ввели неверный пароль"));
            }
        });
        return authResult;
    }

    public void resetAuthResult() {
        authResult.setValue(new AuthResponse(2, "reset"));
    }
}
