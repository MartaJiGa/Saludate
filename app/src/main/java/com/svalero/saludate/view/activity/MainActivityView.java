package com.svalero.saludate.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.R;
import com.svalero.saludate.view.fragment.CalendarFragmentView;

public class MainActivityView extends AppCompatActivity {

    //region Properties

    BottomNavigationView bottomNavigationView;

    //endregion

    //region Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        FirebaseUser user = intent.getParcelableExtra("user");

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        prepareListeners();

        // Load main fragment
        bottomNavigationView.setSelectedItemId(R.id.nav_calendar);
        loadFragment(new CalendarFragmentView());
    }

    //endregion

    //region Methods

    private void prepareListeners(){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            if (item.getItemId() == R.id.nav_calendar) {
                fragment = new CalendarFragmentView();
            } else if (item.getItemId() == R.id.nav_user_profile) {
                //fragment = new UserProfileFragmentView();
            } else if (item.getItemId() == R.id.nav_skin_conditions) {
                //fragment = new SkinConditionsFragmentView();
            }

            if (fragment != null) {
                loadFragment(fragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, null)
                .commit();
    }

    //endregion
}
