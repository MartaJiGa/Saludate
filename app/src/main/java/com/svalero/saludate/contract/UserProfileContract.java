package com.svalero.saludate.contract;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.domain.UserData;

public interface UserProfileContract {
    interface Model {
        void updateUser(UserData userData, OnCompleteListener<AuthResult> callback);
        void updateUserPassword(String currentPassword, String newPassword, OnCompleteListener<Void> callback);
        FirebaseUser getUser();
        void getUserData(FirebaseUser firebaseUser, OnCompleteListener<UserData> callback);
    }

    interface View {
        void clearInputs();
        void setDataInControls(UserData retrievedUserData);
        void savedUserDataSuccess();
        void savedUserDataError(String message);
        void updatePasswordSuccess();
        void updatePasswordError(String message);
        void showError(String message);
    }

    interface Presenter {
        void updateUser(UserData userData);
        void updateUserPassword(String currentPassword, String newPassword);
        FirebaseUser getUser();
        void getUserData(FirebaseUser firebaseUser);
    }
}
