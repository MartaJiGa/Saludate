package com.svalero.saludate.model;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.svalero.saludate.contract.UserContract;

public class UserModel implements UserContract.Model {

    //region Properties

    private static final String TAG = UserModel.class.getSimpleName();
    private final FirebaseAuth mAuth;

    //endregion

    //region Constructor

    public UserModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    //endregion

    //region Override from contract

    @Override
    public void signOut() {
        mAuth.signOut();
    }

    //endregion
}
