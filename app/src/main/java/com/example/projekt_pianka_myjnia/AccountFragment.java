package com.example.projekt_pianka_myjnia;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.content.ContentValues.TAG;

import androidx.fragment.app.FragmentManager;


public class AccountFragment extends Fragment implements View.OnClickListener {

    private String userID;
    private ImageView profilImage;
    private Button changeProfilImage, acc_arrow_back_registration, acc_button_change_username, acc_button_change_password, acc_button_check_history, acc_button_peymant, acc_button_personal_data;

    FirebaseStorage firebaseStorage;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_account, container, false);


        acc_arrow_back_registration = (Button) rootView.findViewById(R.id.acc_arrow_back_registration);
        acc_arrow_back_registration.setOnClickListener(this::onClick);

        acc_button_change_username = (Button) rootView.findViewById(R.id.acc_button_change_username);
        acc_button_change_username.setOnClickListener(this);

        acc_button_change_password = (Button) rootView.findViewById(R.id.acc_button_change_password);
        acc_button_change_password.setOnClickListener(this::onClick);

        acc_button_check_history = (Button) rootView.findViewById(R.id.acc_button_check_history);
        acc_button_check_history.setOnClickListener(this::onClick);

        acc_button_peymant = (Button) rootView.findViewById(R.id.acc_button_peymant);
        acc_button_peymant.setOnClickListener(this::onClick);

        acc_button_personal_data = (Button) rootView.findViewById(R.id.acc_button_personal_data);
        acc_button_personal_data.setOnClickListener(this::onClick);

        firebaseAuth = FirebaseAuth.getInstance();//FireBase connecting try
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");//userID = user.getUid();
        ImageView profilImage = (ImageView) rootView.findViewById(R.id.profilImage);// profil picture

        final TextView usernameTextView = (TextView) rootView.findViewById(R.id.username_textView);//display username R.id.
        final TextView emailTextView = (TextView) rootView.findViewById(R.id.email_textView);//display email R.id.

        databaseReference.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                Glide.with(getContext()).load(userProfile.getProfil_picture()).into(profilImage);
                if (userProfile != null) {
                    String username = userProfile.username;
                    String email = userProfile.email;

                    usernameTextView.setText(username);
                    emailTextView.setText(email);

                } else {
                    usernameTextView.setText("Problem z ładowaniem");
                    emailTextView.setText("Problem z ładowaniem");
                }
            }//Displaying user data

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "User data display error ", Toast.LENGTH_LONG).show();
            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////IMAGE DISPLAY <-HERE START
        changeProfilImage = (Button) rootView.findViewById(R.id.changeProfil);//button change profil picture
        changeProfilImage.setOnClickListener(this);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(/////////display image <-very important
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            Uri imageUri = result.getData().getData();
                            profilImage.setImageURI(imageUri);
                            uploadImageToFirebase(imageUri);

                        } else {
                            Toast.makeText(getContext(), "Problem z wyswetlenie zdjecia", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        changeProfilImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                someActivityResultLauncher.launch(openGalleryIntent);

            }
        });

        return rootView;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////IMAGE DISPLAY <-HERE END

    public void uploadImageToFirebase(Uri imageUri) {
        StorageReference fileRef = firebaseStorage.getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        databaseReference.getDatabase().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profil_picture").setValue(uri.toString());
                        Toast.makeText(getContext(), "Pomyslnie dodano zdjeci ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Image not ! Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {//////////////////////////////////////////////////////////////////////OnClick add button////////////<----------HERE
        Fragment fragment2 = null;

        switch (v.getId()) {
            case R.id.acc_arrow_back_registration:
                Intent login_register = new Intent(getContext(), MainActivity.class);
                startActivity(login_register);
                break;

            case R.id.acc_button_change_username:
                fragment2 = new ChangeUsernameFragment();
                loadFragment(fragment2);
                break;

            case R.id.acc_button_change_password:
           /*     fragment = new ChangePasswordFragment();
                loadFragment(fragment);*/
                break;

            case R.id.acc_button_check_history:
                fragment2 = new OrderHistoryFragment();
                loadFragment(fragment2);
                break;

            case R.id.acc_button_peymant:
          /*      fragment = new PaymentFragment();
                loadFragment(fragment);*/
                break;
            case R.id.acc_button_personal_data:
             /*   fragment = new PersonalDataFragment();
                loadFragment(fragment);*/
                break;
            default:

        }
    }

    private void loadFragment(Fragment fragment2) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment2).commit();
        fragmentTransaction.addToBackStack(null);
    }
}