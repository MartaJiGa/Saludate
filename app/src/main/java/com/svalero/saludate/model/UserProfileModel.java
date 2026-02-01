package com.svalero.saludate.model;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.contract.UserProfileContract;
import com.svalero.saludate.domain.UserData;

public class UserProfileModel implements UserProfileContract.Model {

    //region Properties

    private final FirebaseAuth mAuth;

    //endregion

    //region Constructor

    public UserProfileModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    //endregion

    //region Override from contract

    @Override
    public void updateUser(UserData userData, OnCompleteListener<AuthResult> callback) {
        //TODO: Update user
    }

    @Override
    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    //endregion
}
