package com.example.theonlinegrocerystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PaymentResultFragment extends Fragment {

    private static final String ORDER_KEY ="order" ;
    private TextView txtMessage;
    private Button btnHome;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_payment_result,container,false);

        initViews(view);

        Bundle bundle = getArguments();
        if(null != bundle)
        {
            String jsonOrder = bundle.getString(ORDER_KEY);
            if(null != jsonOrder)
            {
                Gson gson = new Gson();
                Type type = new TypeToken<Order>(){}.getType();
                Order order = gson.fromJson(jsonOrder,type);
                if(null!=order)
                {
                    if(order.isSuccess())
                    {
                        txtMessage.setText("Payment was successful\n Your Order will arrive in 3 days");
                        Utils.clearcartitems(getActivity());
                        for(Groceryitem item :order.getItems())
                        {
                            Utils.increasepopularitypoint(getActivity(),item,1);
                            Utils.ChangeUserPoint(getActivity(),item,4);
                        }
                    }
                    else
                    {
                        txtMessage.setText("Payment Failed \n Please try another payment method");
                    }
                }
            }else
            {
               txtMessage.setText("Payment Failed \n Please try another payment method");
            }
        }
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return view;
    }
    private void initViews(View view)
    {
        txtMessage=view.findViewById(R.id.txtMessage);
        btnHome=view.findViewById(R.id.btnHome);
    }
}
