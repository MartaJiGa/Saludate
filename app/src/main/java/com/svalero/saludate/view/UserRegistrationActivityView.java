package com.svalero.saludate.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.R;

public class UserRegistrationActivityView extends AppCompatActivity {
    FirebaseAuth mAuth;

    EditText edtEmailAddress, edtPassword, edtConfirmPassword;
    Button btnRegister;
    TextView tvGoLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_registration);
        setTitle(R.string.user_registration_view);

        mAuth = FirebaseAuth.getInstance();

        initializeComponents();
        initializeButtonListeners();
    }

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
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(UserRegistrationActivityView.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void goToLogin(){
        Intent intent = new Intent(getApplicationContext(), UserLoginActivityView.class);
        startActivity(intent);
        finish();
    }

    public void updateUI(FirebaseUser user){

    }
}
