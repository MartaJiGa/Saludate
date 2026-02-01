package com.svalero.saludate.contract;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.domain.UserData;

public interface UserProfileContract {
    interface Model {
        void updateUser(UserData userData, OnCompleteListener<AuthResult> callback);
        FirebaseUser getUser();
    }

    interface View {
        void clearInputs();
        void showError(String message);
    }

    interface Presenter {
        void updateUser(UserData userData);
        FirebaseUser getUser();
    }
}
