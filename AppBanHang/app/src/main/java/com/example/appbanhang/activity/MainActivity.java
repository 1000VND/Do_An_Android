package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.fragment.FavouriteFragment;
import com.example.appbanhang.fragment.HomeFragment;
import com.example.appbanhang.fragment.OrderFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Intent intent;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "Internet not availble. Check your internet", Toast.LENGTH_SHORT).show();
        } else {
            super.onCreate(savedInstanceState);
            replaceFragment(new HomeFragment());
            setContentView(R.layout.activity_main);
            initView();
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (!isConnected()) {
                        Toast.makeText(getApplicationContext(), "Internet not availble. Check your internet", Toast.LENGTH_SHORT).show();
                    } else {

                        item.setChecked(true);
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.nav_home:
                                replaceFragment(new HomeFragment());
                                break;
                            case R.id.nav_favourite:
                                replaceFragment(new FavouriteFragment());
                                break;
                            case R.id.nav_order:
                                replaceFragment(new OrderFragment());
                                break;
                            case R.id.info:
                                Toast.makeText(MainActivity.this, "Infomation", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.exit:
                                intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    return true;
                }
            });

//            if (auth.getCurrentUser() != null) {
//                Toast.makeText(MainActivity.this, "Please wait you are already login in", Toast.LENGTH_SHORT).show();
//            }
        }

    }

    private void initView() {
        auth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.isConnected())
                return true;
            else
                return false;
        } else
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutoolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //lấy id của các item menu

        int id = item.getItemId();
        switch (id){
            case R.id.cart:
                intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

}