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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegistration, arrow_back_registration;
    private TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();//FireBase connecting try

        textInputEditTextFullname = (TextInputEditText) findViewById(R.id.fullnameRegistration);
        textInputEditTextUsername = (TextInputEditText) findViewById(R.id.usernameRegistration);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.passwordRegistration);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.emailRegistration);

        buttonRegistration = (Button) findViewById(R.id.buttonRegistration);//Button registration new account
        buttonRegistration.setOnClickListener(RegistrationActivity.this);

        arrow_back_registration = (Button) findViewById(R.id.arrow_back_registration);//Button registration new account
        arrow_back_registration.setOnClickListener(RegistrationActivity.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_back_registration:
                Intent arrow_back_registration = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(arrow_back_registration);
                break;

            case R.id.buttonRegistration:
                registerUser();
                break;

        }
    }//button operation

    private static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }//Password validation 1-4 steps

    private void registerUser() {
        String fullname = textInputEditTextFullname.getText().toString().trim();
        String username = textInputEditTextUsername.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();
        String email = textInputEditTextEmail.getText().toString().trim();
        String profil_picture = "Empty picture";

        if (fullname.isEmpty()) {
            textInputEditTextFullname.setError("Fullname is requried !");
            textInputEditTextFullname.requestFocus();
            return;
        }//Fullname check
        if (username.isEmpty()) {
            textInputEditTextUsername.setError("Username is requried !");
            textInputEditTextUsername.requestFocus();
            return;
        }//Username check
        if (password.isEmpty()) {
            textInputEditTextPassword.setError("Password is requried !");
            textInputEditTextPassword.requestFocus();
            return;
        }//Password first validation check
        if (!isValidPassword(password)) {
            textInputEditTextPassword.setError("Password does not meet the requirements! ");
            textInputEditTextPassword.requestFocus();
            return;
        }//Password second validation check
        if (email.isEmpty()) {
            textInputEditTextEmail.setError("Email is requried !");
            textInputEditTextEmail.requestFocus();
            return;
        }//Email name check
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEditTextEmail.setError("Please provide valid email!");
            textInputEditTextEmail.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)//start registration
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullname, username, email, profil_picture);

                            rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference();
                            reference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(RegistrationActivity.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });//end registration
    }
}