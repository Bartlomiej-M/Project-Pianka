package com.example.projekt_pianka_myjnia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NotificationFragment extends Fragment implements View.OnClickListener {

    private Button not_arrow_back_home, not_raport_button;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    NotifcationAdapter notifcationAdapter;
    ArrayList<notification> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = rootView.findViewById(R.id.notificationlist);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notification");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();

        notifcationAdapter = new NotifcationAdapter(getContext(), list);
        recyclerView.setAdapter(notifcationAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    notification notification = dataSnapshot.getValue(notification.class);
                    list.add(notification);
                }
                notifcationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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