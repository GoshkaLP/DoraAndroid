package com.dorawarranty.dora.mvvm.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.Event;
import com.dorawarranty.dora.mvvm.models.AuthResponse;
import com.dorawarranty.dora.mvvm.repository.UsersRepository;

public class UsersViewModel extends AndroidViewModel {

    private MutableLiveData<String> email = new MutableLiveData<>("");
    private MutableLiveData<String> passwordRegister = new MutableLiveData<>("");
    private MutableLiveData<String> passwordRegisterRepeat = new MutableLiveData<>("");
    private MutableLiveData<String> passwordAuth = new MutableLiveData<>("");

    private MutableLiveData<String> changePasswordOld = new MutableLiveData<>("");
    private MutableLiveData<String> changePasswordNew = new MutableLiveData<>("");
    private MutableLiveData<String> changePasswordNewRepeat = new MutableLiveData<>("");

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

    public MutableLiveData<Event<String>> checkEmail(String email) {
        return mRepository.checkEmail(email);
    }

    public void register(String email, String password) {
        mRepository.registerEmail(email, password);
    }

    public MutableLiveData<Boolean> checkToken() {
        return mRepository.checkToken();
    }

    public MutableLiveData<Event<String>> auth(String email, String password) {
        return mRepository.authEmail(email, password);
    }

    public MutableLiveData<String> getPasswordAuth() {
        return passwordAuth;
    }

    public void setPasswordAuth(String password) {
        passwordAuth.setValue(password);
    }


    public MutableLiveData<String> getChangePasswordOld() {
        return changePasswordOld;
    }

    public void setChangePasswordOld(String password) {
        changePasswordOld.setValue(password);
    }

    public MutableLiveData<String> getChangePasswordNew() {
        return changePasswordNew;
    }

    public void setChangePasswordNew(String password) {
        changePasswordNew.setValue(password);
    }

    public MutableLiveData<String> getChangePasswordNewRepeat() {
        return changePasswordNewRepeat;
    }

    public void setChangePasswordNewRepeat(String password) {
        changePasswordNewRepeat.setValue(password);
    }

    public MutableLiveData<Event<Boolean>> logout() {
        return mRepository.logout();
    }

    public MutableLiveData<Event<String>> changePassword(String oldPassword, String newPassword) {
        return mRepository.changePassword(oldPassword, newPassword);
    }
}
