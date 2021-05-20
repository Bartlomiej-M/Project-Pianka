package com.example.projekt_pianka_myjnia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class OrderHistoryFragment extends Fragment implements View.OnClickListener {
    private Button foharrow_back_account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_history, container, false);

        foharrow_back_account = (Button) rootView.findViewById(R.id.foharrow_back_account);
        foharrow_back_account.setOnClickListener(this);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.foharrow_back_account:
                fragment = new AccountFragment();
                loadFragment(fragment);
                break;

        }
    }

    private void loadFragment(Fragment fragment2) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment2).commit();
        fragmentTransaction.addToBackStack(null);
    }
}