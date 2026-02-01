package com.svalero.saludate.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.svalero.saludate.R;
import com.svalero.saludate.contract.UserContract;
import com.svalero.saludate.presenter.UserPresenter;
import com.svalero.saludate.view.activity.UserLoginActivityView;

public class UserFragmentView extends Fragment implements UserContract.View {

    //region Properties

    private View view;
    private UserPresenter presenter;
    private Button userProfileBtn;
    private Button settingsBtn;
    private Button logoutBtn;

    //endregion

    //region Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user, container, false);
        presenter = new UserPresenter(this);

        initializeControls();
        prepareListeners();

        return view;
    }

    //endregion

    //region Methods

    private void initializeControls(){
        userProfileBtn = view.findViewById(R.id.btn_user_profile);
        settingsBtn = view.findViewById(R.id.btn_settings);
        logoutBtn = view.findViewById(R.id.btn_logout);
    }

    private void prepareListeners(){
        userProfileBtn.setOnClickListener(v -> {
            replaceFragment(new UserProfileFragmentView());
        });

        settingsBtn.setOnClickListener(v -> {
            replaceFragment(new SettingsFragmentView());
        });

        logoutBtn.setOnClickListener(v -> {
            askIfWantToLogout();
        });
    }

    private void replaceFragment(Fragment fragment){
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void askIfWantToLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.logout_question);
        builder.setPositiveButton(R.string.answer_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                presenter.signOut();
            }
        });
        builder.setNegativeButton(R.string.answer_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void goToLoginActivity(){
        Intent intent = new Intent(this.getContext(), UserLoginActivityView.class);
        startActivity(intent);
        this.getActivity().finish();
    }

    //endregion
}
