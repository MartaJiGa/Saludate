package com.svalero.saludate.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.R;
import com.svalero.saludate.contract.UserLoginContract;
import com.svalero.saludate.presenter.UserLoginPresenter;

public class UserLoginActivityView extends AppCompatActivity implements UserLoginContract.View {

    //region Properties
    FirebaseAuth mAuth;

    UserLoginPresenter presenter;

    EditText edtEmailAddress, edtPassword;
    Button btnLogin;
    TextView tvGoRegister;

    //endregion

    //region Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Saludate);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_login);
        setTitle(R.string.user_login_view);

        mAuth = FirebaseAuth.getInstance();
        presenter = new UserLoginPresenter(this);

        initializeComponents();
        initializeButtonListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        goToMain();
    }

    //endregion

    //region Override from contract

    @Override
    public void navigateToMain() {
        goToMain();
    }

    @Override
    public void clearInputs() {
        edtEmailAddress.setText("");
        edtPassword.setText("");
    }

    @Override
    public void showError(String message) {
        String errorMessage = getString(R.string.error_authentication_failed) + ": " + message;

        Toast.makeText(UserLoginActivityView.this, errorMessage, Toast.LENGTH_SHORT).show();
        Log.e(getString(R.string.error_authentication_failed), message);
    }

    //endregion

    //region Methods

    public void initializeComponents(){
        edtEmailAddress = findViewById(R.id.edt_login_email_address);
        edtPassword = findViewById(R.id.edt_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvGoRegister = findViewById(R.id.tv_login_go_register);
    }

    public void initializeButtonListeners(){
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        tvGoRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToUserRegistration();
            }
        });
    }

    public void loginUser(){
        String email, password;
        email = String.valueOf(edtEmailAddress.getText());
        password = String.valueOf(edtPassword.getText());

        if(TextUtils.isEmpty(email)){
            Toast.makeText(UserLoginActivityView.this, R.string.error_empty_email, Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(UserLoginActivityView.this, R.string.error_empty_password, Toast.LENGTH_SHORT).show();
        } else{
            presenter.loginUser(email, password);
        }
    }

    public void goToUserRegistration(){
        Intent intent = new Intent(getApplicationContext(), UserRegistrationActivityView.class);
        startActivity(intent);
        finish();
    }

    public void goToMain(){
        FirebaseUser user = presenter.getUser();

        if(user != null){
            Intent intent = new Intent(getApplicationContext(), MainActivityView.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
    }

    //endregion
}
