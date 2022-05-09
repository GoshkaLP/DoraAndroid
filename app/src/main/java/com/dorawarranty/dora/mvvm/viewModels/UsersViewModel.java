package com.dorawarranty.dora.mvvm.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.mvvm.models.AuthResponse;
import com.dorawarranty.dora.mvvm.repository.UsersRepository;

public class UsersViewModel extends AndroidViewModel {

    private MutableLiveData<String> email = new MutableLiveData<>("");
    private MutableLiveData<String> passwordRegister = new MutableLiveData<>("");
    private MutableLiveData<String> passwordRegisterRepeat = new MutableLiveData<>("");
    private MutableLiveData<String> passwordAuth = new MutableLiveData<>("");

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

    public MutableLiveData<String> getPasswordRegister() {
        return passwordRegister;
    }

    public void setPasswordRegister(String password) {
        this.passwordRegister.setValue(password);
    }

    public MutableLiveData<String> getPasswordRegisterRepeat() {
        return passwordRegisterRepeat;
    }

    public void setPasswordRegisterRepeat(String repeatPassword) {
        this.passwordRegisterRepeat.setValue(repeatPassword);
    }

    public MutableLiveData<AuthResponse> checkEmail(String email) {
        return mRepository.checkEmail(email);
    }

    public void resetCheckEmail() {
        mRepository.resetCheckEmail();
    }

    public void register(String email, String password) {
        mRepository.registerEmail(email, password);
    }

    public MutableLiveData<Boolean> checkToken() {
        return mRepository.checkToken();
    }

    public MutableLiveData<AuthResponse> auth(String email, String password) {
        return mRepository.authEmail(email, password);
    }

    public void resetAuth() {
        mRepository.resetAuthResult();
    }

    public MutableLiveData<String> getPasswordAuth() {
        return passwordAuth;
    }

    public void setPasswordAuth(String password) {
        passwordAuth.setValue(password);
    }
}
