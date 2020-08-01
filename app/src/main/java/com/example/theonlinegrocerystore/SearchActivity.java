package com.example.theonlinegrocerystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";//what is logt
    private MaterialToolbar toolbar;
    private EditText searchBox;
    private ImageView btnSearch;
    private TextView txtFirstCat,txtSecondCat,txtThirdCat,txtAllCategories;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;

    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        initBottomView();

        setSupportActionBar(toolbar);





        adapter=new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSearch();
            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               initSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void initSearch()
    {
        if(!searchBox.getText().toString().equals(""));
        {
            String name = searchBox.getText().toString();
            ArrayList<Groceryitem> items = Utils.searchforItem(this,name);
            if(null!=items)
            {
                adapter.setItems(items);
            }
        }
    }
    private void initBottomView()
    {
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        //TODO navigate to another activity
                        break;
                    case R.id.searchnew:

                        break;
                    case R.id.cart:

                        break;
                    default:
                        break;

                }
                return false;
            }
        });
    }
    private void initViews()
    {
        toolbar=findViewById(R.id.toolbar);
        searchBox=findViewById(R.id.searchBox);
        btnSearch=findViewById(R.id.btnSearch);
        txtFirstCat=findViewById(R.id.txtFirstCat);
        txtSecondCat=findViewById(R.id.txtSecondCat);
        txtThirdCat=findViewById(R.id.txtThirdCat);
        txtAllCategories=findViewById(R.id.txtAllCategories);
        bottomNavigationView=findViewById(R.id.bottomNavigation);
        recyclerView=findViewById(R.id.recyclerView);
    }
}