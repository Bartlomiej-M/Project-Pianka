package com.example.projekt_pianka_myjnia;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdatePasswordFragment extends Fragment implements View.OnClickListener {
    private Button upfarrow_back_account, upfUpdate_password_button;
    private TextInputEditText passwordOldUpdate, passwordNewUpdate;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_update_password, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        upfarrow_back_account = (Button) rootView.findViewById(R.id.upfarrow_back_account);
        upfarrow_back_account.setOnClickListener(this);

        upfUpdate_password_button = (Button) rootView.findViewById(R.id.upfUpdate_password_button);
        upfUpdate_password_button.setOnClickListener(this);

        passwordOldUpdate = (TextInputEditText) rootView.findViewById(R.id.passwordOldUpdate);
        passwordNewUpdate = (TextInputEditText) rootView.findViewById(R.id.passwordNewUpdate);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.upfarrow_back_account:
                fragment = new AccountFragment();
                loadFragment(fragment);
                break;
            case R.id.upfUpdate_password_button:
                updatePassword();
                break;
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

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

    private void updatePassword() {
        String oldPassword = passwordOldUpdate.getText().toString().trim();
        String newPassword = passwordNewUpdate.getText().toString().trim();

        if (oldPassword.isEmpty()) {
            passwordOldUpdate.setError("Password is requried !");
            passwordOldUpdate.requestFocus();
            return;
        }//Odl Password first validation check
        if (newPassword.isEmpty()) {
            passwordNewUpdate.setError("Password is requried !");
            passwordNewUpdate.requestFocus();
            return;
        }//New Password first validation check
        if (!isValidPassword(newPassword)) {
            passwordNewUpdate.setError("Password does not meet the requirements! ");
            passwordNewUpdate.requestFocus();
            return;
        }//Password second validation check


        FirebaseUser user = firebaseAuth.getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Hasło zostało zmienione", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "problem II etap", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "problem I etap", Toast.LENGTH_SHORT).show();
            }
        });
    }

}