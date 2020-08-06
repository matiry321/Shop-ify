package com.example.theonlinegrocerystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initViews();
        initBottomNavView();
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new FirstCartFragment());
        transaction.commit();
    }

    private void initBottomNavView()
    {
        bottomNavView.setSelectedItemId(R.id.cart);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        Intent homeintent = new Intent(CartActivity.this,MainActivity.class);
                        homeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homeintent);
                        break;
                    case R.id.searchnew:
                        Intent searchintent = new Intent(CartActivity.this,SearchActivity.class);
                        searchintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(searchintent);
                        break;
                    case R.id.cart:
                       // Toast.makeText(getActivity(), "Search the item", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;

                }
                return false;
            }
        });
        //check oncreateview method
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent cartIntent = new Intent(CartActivity.this,MainActivity.class);
        startActivity(cartIntent);
    }

    private void initViews()
    {
        bottomNavView=findViewById(R.id.bottomNavView);
        toolbar=findViewById(R.id.toolbar);
    }
}