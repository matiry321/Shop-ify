package com.example.theonlinegrocerystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigation;
    private RecyclerView rec1 ,rec2,rec3;
    private GroceryItemAdapter adap1,adap2,adap3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dumy,container,false);
        initView(view);
       // Utils.clearSharedPreferences(getActivity());
      //  initRecViews();//to initialise our recycler view
        bottomNavigation.setSelectedItemId(R.id.home);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        Toast.makeText(getActivity(), "Home is selected", Toast.LENGTH_SHORT).show();
                        //TODO navigate to another activity
                        break;
                    case R.id.searchnew:
                        Intent intent = new Intent(getActivity(),SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        Toast.makeText(getActivity(), "Search the item", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;

                }
                return false;
            }
        });
        return view;//check oncreateview method
    }
    public void onResume() {

        super.onResume();
        initRecViews();
    }
    private void initRecViews()
    {
        adap1 = new GroceryItemAdapter(getActivity());
        rec1.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        rec1.setAdapter(adap1);

        adap2 = new GroceryItemAdapter(getActivity());
        rec2.setAdapter(adap2);
        rec2.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        adap3 = new GroceryItemAdapter(getActivity());
        rec3.setAdapter(adap3);
        rec3.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        ArrayList<Groceryitem> newItems = Utils.getAllItems(getActivity());
        if(null!=newItems)
        {
            Comparator<Groceryitem> newItemsComparator = new Comparator<Groceryitem>() {
                @Override
                public int compare(Groceryitem o1, Groceryitem o2) {
                    return o1.getId()-o2.getId();
                }
            };

            Comparator<Groceryitem> reversecomparator = Collections.reverseOrder(newItemsComparator);
            Collections.sort(newItems,reversecomparator);
            adap1.setItems(newItems);
        }
        ArrayList<Groceryitem> popularitems = Utils.getAllItems(getActivity());
        if(null!=popularitems)
        {
            Comparator<Groceryitem> popularityItemsComparator = new Comparator<Groceryitem>() {
                @Override
                public int compare(Groceryitem o1, Groceryitem o2) {
                    return o1.getPopularityPoint()-o2.getPopularityPoint();
                }
            };
            Collections.sort(popularitems,Collections.<Groceryitem>reverseOrder(popularityItemsComparator));
            adap2.setItems(popularitems);
        }
        ArrayList<Groceryitem> suggestedItems = Utils.getAllItems(getActivity());
        if(null!=suggestedItems)
        {
            Comparator<Groceryitem> SuggestedItemsComparator = new Comparator<Groceryitem>() {
                @Override
                public int compare(Groceryitem o1, Groceryitem o2) {
                    return o1.getPopularityPoint()-o2.getPopularityPoint();
                }
            };
            Collections.sort(suggestedItems,Collections.<Groceryitem>reverseOrder(SuggestedItemsComparator));
            adap3.setItems(suggestedItems);
        }


    }
    private void initView(View view)
    {
        bottomNavigation=view.findViewById(R.id.bottomNavigation);
        rec1=view.findViewById(R.id.rec1);
        rec2=view.findViewById(R.id.rec2);
        rec3=view.findViewById(R.id.rec3);
    }
}
