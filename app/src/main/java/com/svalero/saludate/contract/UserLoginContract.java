package com.svalero.saludate.contract;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public interface UserLoginContract {
    interface Model {
        void loginUser(String email, String password, OnCompleteListener<AuthResult> callback);
        FirebaseUser getUser();
        boolean isUserLoggedIn();
    }

    interface View {
        void navigateToMain();
        void clearInputs();
        void showError(String message);
    }

    interface Presenter {
        void loginUser(String email, String password);
        FirebaseUser getUser();
        boolean isUserLoggedIn();
    }
}