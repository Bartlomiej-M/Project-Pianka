package com.example.projekt_pianka_myjnia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotYourPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Button arrow_back_reset;
    private Button buttonResetYouPassword;
    private TextInputEditText textInputEditTextEmail;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_your_password);
        auth = FirebaseAuth.getInstance();

        arrow_back_reset = (Button) findViewById(R.id.arrow_back_reset);
        arrow_back_reset.setOnClickListener(ForgotYourPasswordActivity.this);

        buttonResetYouPassword = (Button) findViewById(R.id.buttonResetYouPassword);
        buttonResetYouPassword.setOnClickListener(ForgotYourPasswordActivity.this);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.emailReset);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_back_reset:
                Intent arrow_back_reset = new Intent(ForgotYourPasswordActivity.this, LoginActivity.class);
                startActivity(arrow_back_reset);
                break;

            case R.id.buttonResetYouPassword:
                resetPassword();
                break;
        }
    }

    private void resetPassword() {
        String email = textInputEditTextEmail.getText().toString().trim();

        if (email.isEmpty()) {
            textInputEditTextEmail.setError("Email is required !");
            textInputEditTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEditTextEmail.setError("Please provide valid email!");
            textInputEditTextEmail.requestFocus();
            return;
        }//validation email

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotYourPasswordActivity.this, "Check you email to reset your password", Toast.LENGTH_LONG).show();
                    Intent login_register = new Intent(ForgotYourPasswordActivity.this, LoginActivity.class);
                    startActivity(login_register);
                } else {
                    Toast.makeText(ForgotYourPasswordActivity.this, "Something went wrong ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}



