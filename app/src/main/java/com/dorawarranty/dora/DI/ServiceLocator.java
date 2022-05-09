package com.dorawarranty.dora.DI;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.dorawarranty.dora.MainActivity;
import com.dorawarranty.dora.mvvm.repository.UsersRepository;
import com.dorawarranty.dora.mvvm.repository.WarrantyRepository;
import com.dorawarranty.dora.network.NetworkLogic;
import com.dorawarranty.dora.security.SecurityService;

public class ServiceLocator {

    private static ServiceLocator serviceLocator;
    private Context context;
    private NetworkLogic mNetworkLogic;
    private SecurityService mSecurityService;
    private UsersRepository mUsersRepository;
    private WarrantyRepository mWarrantyRepository;


    public ServiceLocator(Context context) {
        serviceLocator = this;
        this.context = context;
        mNetworkLogic = new NetworkLogic();
        mSecurityService = new SecurityService(context);
        mUsersRepository = new UsersRepository(context);
        mWarrantyRepository = new WarrantyRepository(context);
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

    public SecurityService getSecurityService() {
        return mSecurityService;
    }

    public WarrantyRepository getWarrantyRepository() {
        return mWarrantyRepository;
    }

}
