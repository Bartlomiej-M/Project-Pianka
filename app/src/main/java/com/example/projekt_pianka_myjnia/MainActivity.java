package com.example.projekt_pianka_myjnia;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button draw_open;
    public ImageView profilImageheader;
    private TextView brudaski_score;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FirebaseStorage firebaseStorage;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        draw_open = (Button) findViewById(R.id.draw_open);
        draw_open.setOnClickListener(MainActivity.this);



        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(
                this,              /* host Activity */
                drawerLayout,                    /* DrawerLayout object */
                // ((BaseActivity) getActivityCompat()).getToolbar(), <== delete this argument
                R.string.open,  /* "open drawer" description for accessibility */
                R.string.close  /* "close drawer" description for accessibility */
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Fragment fragment = null;
                switch (id) {


                    case R.id.home:
                        Intent login_register = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(login_register);
                        break;

                    case R.id.myShoppingCard:

                        break;

                    case R.id.notification:
                        fragment = new NotificationFragment();
                        loadFragment(fragment);
                        break;

                    case R.id.wishList:

                        break;

                    case R.id.account:
                        fragment = new AccountFragment();
                        loadFragment(fragment);
                        break;

                    case R.id.dashboard:

                        break;

                    case R.id.settings:

                        break;

                    case R.id.aboutUs:
                        fragment = new AboutUsFragment();
                        loadFragment(fragment);
                        break;

                    case R.id.logout:
                        logout();
                        break;

                    default:
                        return true;
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).commit();
        fragmentTransaction.addToBackStack(null);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        Toast.makeText(this, "Successfully logged out ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.draw_open:
                drawerLayout.openDrawer(GravityCompat.START);
                firebaseAuth = FirebaseAuth.getInstance();//FireBase connecting try
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                firebaseStorage = FirebaseStorage.getInstance();
                databaseReference = FirebaseDatabase.getInstance().getReference("Users");//userID = user.getUid();

                final TextView brudaski_scoreTextView = (TextView) findViewById(R.id.brudaski_score);
                ImageView profilImageheader = (ImageView)  drawerLayout.findViewById(R.id.profilImageheader);

                databaseReference.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userProfile = snapshot.getValue(User.class);
                        Glide.with(drawerLayout).load(userProfile.getProfil_picture()).into(profilImageheader);

                        if (userProfile != null) {
                            int brudaski_score = userProfile.brudaski_score;
                            brudaski_scoreTextView.setText(String.valueOf(brudaski_score));

                        } else {
                            brudaski_scoreTextView.setText("Problem z ładowaniem");
                        }
                    }//Displaying user data

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "User data display error ", Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
}