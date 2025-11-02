package com.svalero.saludate.model;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.contract.UserLoginContract;

public class UserLoginModel implements UserLoginContract.Model {

    //region Properties

    private final FirebaseAuth mAuth;

    //endregion

    //region Constructor

    public UserLoginModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    //endregion

    //region Override from contract

    @Override
    public void loginUser(String email, String password, OnCompleteListener<AuthResult> callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(callback);
    }

    @Override
    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    //endregion
}