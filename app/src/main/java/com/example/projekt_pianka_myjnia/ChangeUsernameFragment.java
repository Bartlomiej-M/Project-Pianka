package com.example.projekt_pianka_myjnia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class ChangeUsernameFragment extends Fragment implements View.OnClickListener {
    private Button fcuarrow_back_registration, fcuchangeUsername;
    private TextInputEditText fcuusernameChange;
    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_change_username, container, false);

        mAuth = FirebaseAuth.getInstance();//FireBase connecting try

        fcuarrow_back_registration = (Button) rootView.findViewById(R.id.fcuarrow_back_account);
        fcuarrow_back_registration.setOnClickListener(this);

        fcuusernameChange = (TextInputEditText) rootView.findViewById(R.id.fcuusernameChange);

        fcuchangeUsername = (Button) rootView.findViewById(R.id.fcuchangeUsername);
        fcuchangeUsername.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.fcuarrow_back_account:
                fragment = new AccountFragment();
                loadFragment(fragment);
                break;

            case R.id.fcuchangeUsername:
                setFcuchangeUsername();
                break;
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }


    public void setFcuchangeUsername() {
        String username = fcuusernameChange.getText().toString().trim();

        if (username.isEmpty()) {
            fcuusernameChange.setError("Username is requried !");
            fcuusernameChange.requestFocus();
            return;
        }//Username check

        HashMap hashMap = new HashMap();
        hashMap.put("username", username);
/*
        reference.child("Users").child(FirebaseAuth.getInstance().getUid()).child("username").setValue(username);
        Toast.makeText(getContext(), "Pomyslnie zmieniono username ", Toast.LENGTH_SHORT).show();*/
/*        databaseReference.getDatabase().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profil_picture").setValue(uri.toString());
        Toast.makeText(getContext(), "Pomyslnie dodano zdjeci ", Toast.LENGTH_SHORT).show();*/
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference();
        reference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getContext(), "The operation was successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}