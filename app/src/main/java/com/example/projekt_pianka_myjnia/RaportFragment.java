package com.example.projekt_pianka_myjnia;

import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class RaportFragment extends Fragment implements View.OnClickListener {
    private Button rap_arrow_back_home, btn_date, rap_send_btn ;
    private TextInputEditText rap_subject_EditText, rap_desc_EditText;
    private TextView tv_date;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_raport, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Raports");

        rap_arrow_back_home = (Button) rootView.findViewById(R.id.rap_arrow_back_home);
        rap_arrow_back_home.setOnClickListener(this);

        rap_send_btn = (Button) rootView.findViewById(R.id.rap_send_btn);
        rap_send_btn.setOnClickListener(this);

        rap_subject_EditText = (TextInputEditText) rootView.findViewById(R.id.rap_subject_EditText);
        rap_desc_EditText = (TextInputEditText) rootView.findViewById(R.id.rap_desc_EditText);

        btn_date = rootView.findViewById(R.id.btn_date);
        tv_date = rootView.findViewById(R.id.tv_date);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar kal = Calendar.getInstance();
                int year = kal.get(Calendar.YEAR);
                int month = kal.get(Calendar.MONTH);
                int day = kal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog, dateSetListener, year, month, day);

                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy:" + day + "/" + month + "/" + year);
                String date = day + "/" + month + "/" + year;
                tv_date.setText(date);
            }
        };

        return rootView;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.rap_arrow_back_home:
                fragment = new NotificationFragment();
                loadFragment(fragment);
                break;
            case R.id.rap_send_btn:
                sendRaport();
                break;
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
    private void sendRaport(){
        String report_subject  = rap_subject_EditText.getText().toString().trim();
        String report_description  = rap_desc_EditText.getText().toString().trim();
        String report_date = tv_date.getText().toString().trim();

        if (report_subject.isEmpty()) {
            rap_subject_EditText.setError("Report subject is requried !");
            rap_subject_EditText.requestFocus();
            return;
        }//data raport check
        if (report_description.isEmpty()) {
            rap_desc_EditText.setError("Report description is requried !");
            rap_desc_EditText.requestFocus();
            return;
        }//data raport check

        HashMap<String, String> raport = new HashMap<String, String>();
        raport.put("Subject: ", report_subject);
        raport.put("Description: ", report_description);
        raport.put("Date: ", report_date);
        final int random = new Random().nextInt(1) + 2000;
        rootNode = FirebaseDatabase.getInstance();
        databaseReference = rootNode.getReference();
        databaseReference.child("Raports").child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "id: " + random).setValue(raport).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "The report has been sent ", Toast.LENGTH_SHORT).show();
                fragment = new NotificationFragment();
                loadFragment(fragment);
            }
        });
    }
}
