package com.example.projekt_pianka_myjnia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonWelcomeLogin, buttonWelcomeRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        buttonWelcomeLogin = (Button) findViewById(R.id.buttonWelcomeLogin);
        buttonWelcomeLogin.setOnClickListener(WelcomeActivity.this);

        buttonWelcomeRegistration = (Button) findViewById(R.id.buttonWelcomeRegistration);
        buttonWelcomeRegistration.setOnClickListener(WelcomeActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonWelcomeLogin:
                Intent buttonWelcomeLogin = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(buttonWelcomeLogin);
                break;

            case R.id.buttonWelcomeRegistration:
                Intent buttonWelcomeRegistration = new Intent(WelcomeActivity.this, RegistrationActivity.class);
                startActivity(buttonWelcomeRegistration);
                break;
        }
    }
}
