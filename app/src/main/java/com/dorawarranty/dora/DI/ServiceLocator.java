package com.dorawarranty.dora.DI;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.dorawarranty.dora.MainActivity;
import com.dorawarranty.dora.mvvm.repository.UsersRepository;
import com.dorawarranty.dora.network.NetworkLogic;

public class ServiceLocator {
    private static ServiceLocator serviceLocator;

    private Context context;

    private NetworkLogic mNetworkLogic;

    private UsersRepository mUsersRepository;


    public ServiceLocator(Context context) {
        serviceLocator = this;
        this.context = context;
        mNetworkLogic = new NetworkLogic();
        mUsersRepository = new UsersRepository(context);
    }

    public static ServiceLocator getInstance() {
        return serviceLocator;
    }

    public static void init (Context context){
        serviceLocator = new ServiceLocator(context);
    }

    public NetworkLogic getNetworkLogic() {
        return mNetworkLogic;
    }

    public UsersRepository getUsersRepository() {
        return mUsersRepository;
    }
}
