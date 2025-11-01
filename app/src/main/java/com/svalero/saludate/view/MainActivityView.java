package com.svalero.saludate.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

public class MainActivityView extends AppCompatActivity {

    //region Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: Set content view

        Intent intent = getIntent();
        FirebaseUser user = intent.getParcelableExtra("user");
    }

    //endregion
}
