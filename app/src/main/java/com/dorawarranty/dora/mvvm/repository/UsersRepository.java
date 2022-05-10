package com.dorawarranty.dora.mvvm.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.Event;
import com.dorawarranty.dora.mvvm.models.AuthResponse;

public class UsersRepository {

    private Context context;
    private ServiceLocator mServiceLocator;
    private MutableLiveData<Event<String>> checkEmail = new MutableLiveData<>();
    private MutableLiveData<Boolean> checkToken = new MutableLiveData<>();
    private MutableLiveData<Event<String>> authResult = new MutableLiveData<>();
    private MutableLiveData<Event<Boolean>> isLogOuted = new MutableLiveData<>();
    private MutableLiveData<Event<String>> passwordChangeResult = new MutableLiveData<>();


    public UsersRepository(Context context) {
        this.context = context;
        mServiceLocator = ServiceLocator.getInstance();
//        mServiceLocator.getSecurityService().removeToken();
    }


    public MutableLiveData<Event<String>> checkEmail(String email) {
        mServiceLocator.getNetworkLogic().checkEmail(email, result -> {
            String message = result.getMessage();
            if (message.equals("SUCCESS")) {
                checkEmail.setValue(new Event<>("ok"));
            } else if (message.equals("ACCOUNT_ALREADY_EXISTS")) {
                checkEmail.setValue(new Event<>("exists"));
            } else if (message.equals("WRONG_EMAIL_FORMAT")) {
                checkEmail.setValue(new Event<>("Неверный формат e-mail"));
            }
        });
        return checkEmail;
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

    public MutableLiveData<Event<String>> authEmail(String email, String password) {
        mServiceLocator.getNetworkLogic().authUser(email, password, result -> {
            String message = result.getMessage();
            if (message.equals("SUCCESS")) {
                String token = (String) result.getData().get("token");
                mServiceLocator.getSecurityService().saveToken(token);
                authResult.setValue(new Event<>("ok"));
            } else if (message.equals("WRONG_PASSWORD")) {
                authResult.setValue(new Event<>( "Вы ввели неверный пароль"));
            }
        });
        return authResult;
    }


    public MutableLiveData<Event<Boolean>> logout() {
        mServiceLocator.getNetworkLogic().logout(result -> {
            String message = result.getMessage();
            if (message.equals("SUCCESS")) {
                mServiceLocator.getSecurityService().removeToken();
                isLogOuted.setValue(new Event<>(true));
            }
        });
        return isLogOuted;
    }

    public MutableLiveData<Event<String>> changePassword(String oldPassword, String newPassword) {
        mServiceLocator.getNetworkLogic().changePassword(oldPassword, newPassword, result -> {
            String message = result.getMessage();
            if (message.equals("WRONG_PASSWORD")) {
                passwordChangeResult.setValue(new Event<>("Неверный старый пароль!"));
            } else if (message.equals("OLD_PASSWORD")) {
                passwordChangeResult.setValue(new Event<>("Новый пароль совпадает со старым!"));
            } else if (message.equals("SUCCESS")) {
                mServiceLocator.getSecurityService().removeToken();
                String token = (String) result.getData().get("token");
                mServiceLocator.getSecurityService().saveToken(token);
                passwordChangeResult.setValue(new Event<>("ok"));
            }
        });
        return passwordChangeResult;
    }
}
