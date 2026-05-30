package com.svalero.saludate.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.R;
import com.svalero.saludate.contract.UserProfileContract;
import com.svalero.saludate.domain.UserData;
import com.svalero.saludate.model.UserProfileModel;

public class UserProfilePresenter implements UserProfileContract.Presenter {

    //region Properties

    private final UserProfileContract.View view;
    private final UserProfileContract.Model model;
    private final FirebaseAuth mAuth;
    private final Context context;

    //endregion

    //region Constructor

    public UserProfilePresenter(UserProfileContract.View view, Context context) {
        this.view = view;
        this.model = new UserProfileModel();
        this.mAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    //endregion

    //region Override from contract

    @Override
    public void updateUser(UserData userData) {
        model.updateUser(userData, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    view.savedUserDataSuccess();
                } else {
                    view.savedUserDataError(task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void updateUserPassword(String currentPassword, String newPassword) {
        model.updateUserPassword(currentPassword, newPassword, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    view.clearInputs();
                    view.updatePasswordSuccess();
                } else {
                    view.clearInputs();
                    view.updatePasswordError(task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public FirebaseUser getUser() {
        return model.getUser();
    }

    @Override
    public void getUserData(FirebaseUser firebaseUser) {
        model.getUserData(firebaseUser, new OnCompleteListener<UserData>() {
            @Override
            public void onComplete(@NonNull Task<UserData> task) {
                if (task.isSuccessful()) {
                    view.clearInputs();
                    view.setDataInControls(task.getResult());
                } else {
                    view.clearInputs();
                    view.showError(task.getException().getMessage());
                }
            }
        });
    }

    //endregion
}
