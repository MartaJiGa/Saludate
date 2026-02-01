package com.svalero.saludate.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.svalero.saludate.contract.UserProfileContract;
import com.svalero.saludate.domain.UserData;

import java.util.HashMap;
import java.util.Map;

public class UserProfileModel implements UserProfileContract.Model {

    //region Properties

    private static final String TAG = UserProfileModel.class.getSimpleName();
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;

    //endregion

    //region Constructor

    public UserProfileModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    //endregion

    //region Override from contract

    @Override
    public void updateUser(UserData userData, OnCompleteListener<AuthResult> callback) {
        db.collection("users")
                .document(userData.getFirebaseUserId())
                .set(userData, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User document created/updated with ID: " + userData.getFirebaseUserId());
                        if (callback != null) {
                            callback.onComplete(Tasks.forResult(null));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error creating/updating user document", e);
                        if (callback != null) {
                            callback.onComplete(Tasks.forException(e));
                        }
                    }
                });
    }

    @Override
    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    @Override
    public UserData getUserData() {
        //TODO: devolver usuario.
        return null;
    }

    //endregion
}
