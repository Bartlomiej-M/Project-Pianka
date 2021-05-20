package com.example.projekt_pianka_myjnia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button draw_open;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;


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

                        break;

                    case R.id.logout:

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.draw_open:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }
}