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


public class NotificationFragment extends Fragment implements View.OnClickListener {

    private Button not_arrow_back_home, not_raport_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        not_arrow_back_home = (Button) rootView.findViewById(R.id.not_arrow_back_home);
        not_arrow_back_home.setOnClickListener(this);

        not_raport_button = (Button) rootView.findViewById(R.id.not_raport_button);
        not_raport_button.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.not_arrow_back_home:
                Intent login_register = new Intent(getContext(), MainActivity.class);
                startActivity(login_register);
                break;
            case R.id.not_raport_button:
                fragment = new RaportFragment();
                loadFragment(fragment);
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