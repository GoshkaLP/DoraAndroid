package com.dorawarranty.dora.mvvm.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.mvvm.repository.UsersRepository;

public class UsersViewModel extends AndroidViewModel {

    private MutableLiveData<String> email = new MutableLiveData<>("");
    private MutableLiveData<String> password = new MutableLiveData<>("");
    private MutableLiveData<String> changePassword = new MutableLiveData<>("");

    private UsersRepository mRepository;

    public UsersViewModel(@NonNull Application application) {

        super(application);

        mRepository = ServiceLocator.getInstance().getUsersRepository();
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public MutableLiveData<String> getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(String changePassword) {
        this.changePassword.setValue(changePassword);
    }

    public boolean checkEmail(String email) {
        return mRepository.checkEmail(email);
    }

    public void register(String email, String password) {
        mRepository.addUser(email, password);
    }
}
