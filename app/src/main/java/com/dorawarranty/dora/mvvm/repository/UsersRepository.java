package com.dorawarranty.dora.mvvm.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.network.entity.Users;

public class UsersRepository {

    private Context context;
    private LiveData<Users> mUser;
    private ServiceLocator mServiceLocator;
    private boolean checkEmail;


    public UsersRepository(Context context) {
        this.context = context;
        mServiceLocator = ServiceLocator.getInstance();
    }

    public boolean checkEmail(String email) {
        mServiceLocator.getNetworkLogic().checkEmail(email, (result) -> {
            checkEmail = result;
        });
        return checkEmail;
    }

    public void addUser(String email, String password) {
        mServiceLocator.getNetworkLogic().registerUser(email, password, (user) -> {
            SharedPreferences sharedPref = ((Activity) context).getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("id", user.getId());
            editor.putString("token", user.getToken());
            editor.putString("email", user.getEmail());
            editor.apply();
        });
    }
}
