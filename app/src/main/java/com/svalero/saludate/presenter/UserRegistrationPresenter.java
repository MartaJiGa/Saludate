package com.svalero.saludate.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.contract.UserRegistrationContract;
import com.svalero.saludate.model.UserRegistrationModel;

public class UserRegistrationPresenter implements UserRegistrationContract.Presenter {

    //region Properties

    private final UserRegistrationContract.View view;
    private final UserRegistrationContract.Model model;
    private final FirebaseAuth mAuth;

    //endregion

    //region Constructor

    public UserRegistrationPresenter(UserRegistrationContract.View view) {
        this.view = view;
        this.model = new UserRegistrationModel();
        this.mAuth = FirebaseAuth.getInstance();
    }

    //endregion

    //region Override from contract

    @Override
    public void registerUser(String email, String password) {
        model.registerUser(email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    view.clearInputs();
                    view.navigateToMain();
                } else {
                    view.showError(task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public FirebaseUser getUser() {
        return model.getUser();
    }

    //endregion
}