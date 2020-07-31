package com.example.theonlinegrocerystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Utils.initSharedPreference(this);
        setSupportActionBar(toolbar); //methos to set action bar of our own
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.drawer_open,R.string.drawer_close);//to create toggle of the navigation drawer
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.cart:
                        Toast.makeText(MainActivity.this, "Cart has been selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.categories:
                        Toast.makeText(MainActivity.this, "Categories has been selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aboutus:
                        Toast.makeText(MainActivity.this, "We will tell u about us", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;

                }
                return false;
            }
        });

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();//Placing the created fragment in the main activity
        transaction.replace(R.id.container,new MainFragment());
        transaction.commit();
    }
    private void initViews()
    {
        drawer=findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar=findViewById(R.id.toolbar);
    }
}