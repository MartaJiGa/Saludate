package com.svalero.saludate.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.contract.UserProfileContract;
import com.svalero.saludate.domain.UserData;
import com.svalero.saludate.model.UserProfileModel;

public class UserProfilePresenter implements UserProfileContract.Presenter {

    //region Properties

    private final UserProfileContract.View view;
    private final UserProfileContract.Model model;
    private final FirebaseAuth mAuth;

    //endregion

    //region Constructor

    public UserProfilePresenter(UserProfileContract.View view) {
        this.view = view;
        this.model = new UserProfileModel();
        this.mAuth = FirebaseAuth.getInstance();
    }

    //endregion

    //region Override from contract

    @Override
    public void updateUser(UserData userData) {
        model.updateUser(userData, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    view.clearInputs();
                    view.showSavedUserSuccess();
                } else {
                    view.clearInputs();
                    view.showError(task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public FirebaseUser getUser() {
        return model.getUser();
    }

    @Override
    public UserData getUserData() {
        return model.getUserData();
    }

    //endregion
}
