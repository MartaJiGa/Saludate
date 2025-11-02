package com.svalero.saludate.view;

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
import com.svalero.saludate.contract.UserRegistrationContract;
import com.svalero.saludate.presenter.UserRegistrationPresenter;

public class UserRegistrationActivityView extends AppCompatActivity implements UserRegistrationContract.View {

    //region Properties

    FirebaseAuth mAuth;

    UserRegistrationPresenter presenter;

    EditText edtEmailAddress, edtPassword, edtConfirmPassword;
    Button btnRegister;
    TextView tvGoLogin;

    //endregion

    //region Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_registration);
        setTitle(R.string.user_registration_view);

        mAuth = FirebaseAuth.getInstance();
        presenter = new UserRegistrationPresenter(this);

        initializeComponents();
        initializeButtonListeners();
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
        edtConfirmPassword.setText("");
    }

    @Override
    public void showError(String message) {
        String errorMessage = getString(R.string.error_registration_failed) + ": " + message;

        Toast.makeText(UserRegistrationActivityView.this, errorMessage, Toast.LENGTH_SHORT).show();
        Log.e(getString(R.string.error_registration_failed), message);
    }

    //endregion

    //region Methods

    public void initializeComponents(){
        edtEmailAddress = findViewById(R.id.edt_register_email_address);
        edtPassword = findViewById(R.id.edt_register_password);
        edtConfirmPassword = findViewById(R.id.edt_register_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvGoLogin = findViewById(R.id.tv_register_go_login);
    }

    public void initializeButtonListeners(){
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        tvGoLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
    }

    public void registerUser(){
        String email, password, confirmPassword;
        email = String.valueOf(edtEmailAddress.getText());
        password = String.valueOf(edtPassword.getText());
        confirmPassword = String.valueOf(edtConfirmPassword.getText());

        if(TextUtils.isEmpty(email)){
            Toast.makeText(UserRegistrationActivityView.this, R.string.error_empty_email, Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(UserRegistrationActivityView.this, R.string.error_empty_password, Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(UserRegistrationActivityView.this, R.string.error_empty_password_confirmation, Toast.LENGTH_SHORT).show();
        } else if(!password.equals(confirmPassword)){
            Toast.makeText(UserRegistrationActivityView.this, R.string.error_passwords_comparison, Toast.LENGTH_SHORT).show();
        } else{
            presenter.registerUser(email, password);
        }
    }

    public void goToLogin(){
        Intent intent = new Intent(getApplicationContext(), UserLoginActivityView.class);
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