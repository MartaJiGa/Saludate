package com.svalero.saludate.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.contract.UserLoginContract;
import com.svalero.saludate.model.UserLoginModel;

public class UserLoginPresenter implements UserLoginContract.Presenter {

    //region Properties

    private final UserLoginContract.View view;
    private final UserLoginContract.Model model;
    private final FirebaseAuth mAuth;

    //endregion

    //region Constructor

    public UserLoginPresenter(UserLoginContract.View view) {
        this.view = view;
        this.model = new UserLoginModel();
        this.mAuth = FirebaseAuth.getInstance();
    }

    //endregion

    //region Override from contract

    @Override
    public void loginUser(String email, String password) {
        model.loginUser(email, password, new OnCompleteListener<AuthResult>() {
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
