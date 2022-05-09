package com.dorawarranty.dora.security;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SecurityService {
    private Context context;
    private SharedPreferences sharedPref;

    public SecurityService(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences("main", Context.MODE_PRIVATE);
    }


    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", "Bearer " + token);
        editor.apply();
    }

    public String getToken() {
        return sharedPref.getString("token", "");
    }

    public void removeToken() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("token");
        editor.apply();
    }
}
