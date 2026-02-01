package com.svalero.saludate.contract;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.domain.UserData;

public interface UserProfileContract {
    interface Model {
        void updateUser(UserData userData, OnCompleteListener<AuthResult> callback);
        FirebaseUser getUser();
        void getUserData(FirebaseUser firebaseUser, OnCompleteListener<UserData> callback);
    }

    interface View {
        void clearInputs();
        void setDataInControls(UserData retrievedUserData);
        void showSavedUserSuccess(String message);
        void showError(String message);
    }

    interface Presenter {
        void updateUser(UserData userData);
        FirebaseUser getUser();
        void getUserData(FirebaseUser firebaseUser);
    }
}
