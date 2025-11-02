package com.svalero.saludate.contract;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public interface UserRegistrationContract {
    interface Model {
        void registerUser(String email, String password, OnCompleteListener<AuthResult> callback);
        FirebaseUser getUser();
    }

    interface View {
        void navigateToMain();
        void clearInputs();
        void showError(String message);
    }

    interface Presenter {
        void registerUser(String email, String password);
        FirebaseUser getUser();
    }
}
