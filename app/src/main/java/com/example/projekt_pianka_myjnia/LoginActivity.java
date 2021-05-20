package com.example.projekt_pianka_myjnia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView login_registration, buttonForgotYouPassword;
    private Button buttonLogin, arrow_back_login;
    private TextInputEditText textInputEditTextPassword, textInputEditTextEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        login_registration = (TextView) findViewById(R.id.login_register);
        login_registration.setOnClickListener(LoginActivity.this);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(LoginActivity.this);

        buttonForgotYouPassword = (TextView) findViewById(R.id.forgot_your_password);
        buttonForgotYouPassword.setOnClickListener(LoginActivity.this);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.emailReset);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.passwordLogin);

        arrow_back_login = (Button) findViewById(R.id.arrow_back_registration);//Button registration new account
        arrow_back_login.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acc_arrow_back_registration:
                Intent arrow_back_login = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(arrow_back_login);
                break;

            case R.id.login_register:
                Intent login_register = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(login_register);
                break;

            case R.id.buttonLogin:
                userLogin();
                break;

            case R.id.forgot_your_password://button case change LoginActivty -> ForgotYourPasswordActivity
                Intent ForgotYourPasswordActivity = new Intent(LoginActivity.this, ForgotYourPasswordActivity.class);
                startActivity(ForgotYourPasswordActivity);
                break;
        }
    }

    private void userLogin() {
        String email = textInputEditTextEmail.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            textInputEditTextEmail.setError("Email is requried !");
            textInputEditTextEmail.requestFocus();
            return;
        }//validation email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEditTextEmail.setError("Please provide valid email!");
            textInputEditTextEmail.requestFocus();
            return;
        }//validation email
        if (password.isEmpty()) {
            textInputEditTextPassword.setError("Password is requried !");
            textInputEditTextPassword.requestFocus();
            return;
        }//validation password
        if (password.length() < 8) {
            textInputEditTextPassword.setError("Min password length is 8 characters !");
            textInputEditTextPassword.requestFocus();
            return;
        }//validation password

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {//start login
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Successfully logged in ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to login ! Check your fields", Toast.LENGTH_SHORT).show();
                }
            }
        });//end
    }
}