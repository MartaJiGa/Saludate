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

public class UserLoginView extends AppCompatActivity {
    FirebaseAuth mAuth;

    EditText edtEmailAddress, edtPassword;
    Button btnLogin;
    TextView tvGoRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_login);

        mAuth = FirebaseAuth.getInstance();

        initializeComponents();
        initializeButtonListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

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
                goToLogin();
            }
        });
    }

    public void loginUser(){
        String email, password;
        email = String.valueOf(edtEmailAddress.getText());
        password = String.valueOf(edtPassword.getText());

        if(TextUtils.isEmpty(email)){
            Toast.makeText(UserLoginView.this, R.string.error_empty_email, Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(UserLoginView.this, R.string.error_empty_password, Toast.LENGTH_SHORT).show();
        } else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(UserLoginView.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    public void goToLogin(){
        Intent intent = new Intent(getApplicationContext(), UserRegistrationView.class);
        startActivity(intent);
        finish();
    }

    public void reload(){

    }

    public void updateUI(FirebaseUser user){

    }
}
