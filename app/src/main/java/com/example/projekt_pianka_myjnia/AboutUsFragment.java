package com.example.projekt_pianka_myjnia;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class AboutUsFragment extends Fragment implements View.OnClickListener {
    private Button auf_arrow_back_home;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);

        auf_arrow_back_home = (Button) rootView.findViewById(R.id.auf_arrow_back_home);
        auf_arrow_back_home.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()){
            case R.id.auf_arrow_back_home:
                Intent aboutUs = new Intent(getContext(), MainActivity.class);
                startActivity(aboutUs);
                break;
        }
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}