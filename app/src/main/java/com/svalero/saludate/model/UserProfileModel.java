package com.svalero.saludate.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.svalero.saludate.contract.UserProfileContract;
import com.svalero.saludate.domain.UserData;

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
    public void updateUserEmail(String email, OnCompleteListener<AuthResult> callback) {
        FirebaseUser user = mAuth.getCurrentUser();
        String emailBeforeUpdate = user.getEmail();

        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated. Before: " + emailBeforeUpdate + ". After: " + email);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating user email address", e);
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
    public void getUserData(FirebaseUser firebaseUser, OnCompleteListener<UserData> callback) {
        db.collection("users")
                .document(firebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                UserData userData = document.toObject(UserData.class);
                                Log.d(TAG, "User data retrieved: " + document.getData());

                                if (callback != null) {
                                    callback.onComplete(Tasks.forResult(userData));
                                }
                            } else {
                                Log.d(TAG, "No such document");
                                if (callback != null) {
                                    callback.onComplete(Tasks.forResult(null));
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting document.", task.getException());
                            if (callback != null) {
                                callback.onComplete(Tasks.forException(task.getException()));
                            }
                        }
                    }
                });
    }

    //endregion
}
