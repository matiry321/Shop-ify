package com.example.theonlinegrocerystore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SecondCartFragment extends Fragment  {

    private static final String ORDER_KEY="order";

    private EditText edtTxtAddress,edtZipCode,edtPhoneNumber,edtEmail;
    private Button btnNext,btnBack;
    private TextView txtWarning;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cart_second,container,false);

        initViews(view);
        Bundle bundle = getArguments();
        if(null!=bundle)
        {
            String jsonOrder = bundle.getString(ORDER_KEY);
            if(null!=jsonOrder)
            {
                Gson gson= new Gson();
                Type type = new TypeToken<Order>() {}.getType();
                Order order = gson.fromJson(jsonOrder,type);
                if(null!=order)
                {
                    edtTxtAddress.setText(order.getAddress());
                    edtPhoneNumber.setText(order.getPhoneNumber());
                    edtZipCode.setText(order.getZipCode());
                    edtEmail.setText(order.getEmail());
                }
            }
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new FirstCartFragment());
                transaction.commit();

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData())
                {
                    txtWarning.setVisibility(View.GONE);
                    ArrayList<Groceryitem> cartItems=Utils.getCartItems(getActivity());
                    if(null != cartItems)
                    {
                        Order order = new Order();
                        order.setItems(cartItems);
                        order.setAddress(edtTxtAddress.getText().toString());
                        order.setPhoneNumber(edtPhoneNumber.getText().toString());
                        order.setZipCode(edtZipCode.getText().toString());
                        order.setEmail(edtEmail.getText().toString());
                        order.setTotalPrice(calculateTotalPrice(cartItems));

                        Gson gson = new Gson();
                        String jsonOrder=gson.toJson(order);
                        Bundle bundle = new Bundle();
                        bundle.putString(ORDER_KEY,jsonOrder);

                        //we entered the value on the second cardfragment address n all now if we click on next to navigate to third cart fragment
                        ThirdCatFragment thirdCatFragment = new ThirdCatFragment();
                        thirdCatFragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container,thirdCatFragment);
                        transaction.commit();
                    }

                }
                else {
                    txtWarning.setVisibility(View.VISIBLE);
                    txtWarning.setText("Please Fill All The Blanks");
                }
            }
        });
        return view;
    }
    private double calculateTotalPrice(ArrayList<Groceryitem> items)
    {
        double price = 0;
        for(Groceryitem item:items)
        {
            price += item.getPrice();
        }
        price=Math.round(price*100.0)/100.0;
       return price;
    }

    private boolean validateData()
    {
        if(edtTxtAddress.getText().toString().equals("")||edtTxtAddress.getText().toString().equals("") ||
        edtZipCode.getText().toString().equals("") || edtEmail.getText().toString().equals(""))
        {
            return false;
        }
        return true;
    }

    private void initViews(View view)
    {
        edtTxtAddress=view.findViewById(R.id.edtTxtAddress);
        edtZipCode=view.findViewById(R.id.edtZipCode);
        edtPhoneNumber=view.findViewById(R.id.edtPhoneNumber);
        btnNext=view.findViewById(R.id.btnNext);
        btnBack=view.findViewById(R.id.btnBack);
        txtWarning=view.findViewById(R.id.txtWarning);
        edtEmail=view.findViewById(R.id.edtEmail);
    }
}
