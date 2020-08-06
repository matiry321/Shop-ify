package com.example.theonlinegrocerystore;

import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThirdCatFragment extends Fragment {
    private static final String TAG = "ThirdCatFragment";
    private static final String ORDER_KEY="order";
    private Button btnBack,btnCheckout;
    private TextView txtItems,txtPrice,txtAddress,phonenumber;
    private RadioGroup rgPaymentMethod;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_third,container,false);

        initView(view);
        Bundle bundle = getArguments();
        if(null != bundle)
        {
            final String jsonOrder = bundle.getString(ORDER_KEY);
            if(null!=jsonOrder)
            {
                Gson gson= new Gson();
                Type type = new TypeToken<Order>() {}.getType();
                final Order order = gson.fromJson(jsonOrder,type);
                if(null != order)
                {
                    String items = "";
                    for(Groceryitem i: order.getItems())
                    {
                        items+="\n\t" + i.getName();
                    }
                    txtItems.setText(items);
                    txtAddress.setText(order.getAddress());
                    phonenumber.setText(order.getPhoneNumber());
                    txtPrice.setText(String.valueOf(order.getTotalPrice()));

                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle backBundle = new Bundle();
                            backBundle.putString(ORDER_KEY,jsonOrder);
                            SecondCartFragment secondCartFragment = new SecondCartFragment();
                            secondCartFragment.setArguments(backBundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container,secondCartFragment);
                            transaction.commit();
                        }
                    });

                    btnCheckout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (rgPaymentMethod.getCheckedRadioButtonId())
                            {
                                case R.id.rbPayPal:
                                    order.setPaymentMethod("PayPal");
                                    break;
                                case R.id.rbCreditCard:
                                    order.setPaymentMethod("Credit Card");
                                    break;
                                default:
                                    order.setPaymentMethod("Unknown");
                                    break;
                            }
                            order.setSuccess(true);


                            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY);

                            OkHttpClient client = new OkHttpClient.Builder()
                                    .addInterceptor(interceptor)
                                    .build();
                            //TODO Retrofit
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://jsonplaceholder.typicode.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(client)
                                    .build();

                            OrderEndPoint endPoint = retrofit.create(OrderEndPoint.class);
                            Call<Order> call = endPoint.newOrder(order);
                            call.enqueue(new Callback<Order>() {
                                @Override
                                public void onResponse(Call<Order> call, Response<Order> response) {
                                    Log.d(TAG,"onResponse: code: " + response.code());
                                    if(response.isSuccessful())
                                    {
                                        Bundle resultBundle = new Bundle();
                                        resultBundle.putString(ORDER_KEY,gson.toJson(response.body()));
                                        PaymentResultFragment paymentResultFragment = new PaymentResultFragment();
                                        paymentResultFragment.setArguments(resultBundle);
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container,paymentResultFragment);
                                        transaction.commit();
                                    }
                                    else
                                    {
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container,new PaymentResultFragment());
                                        transaction.commit();
                                    }
                                }


                                @Override
                                public void onFailure(Call<Order> call, Throwable t) {
                                    t.printStackTrace();

                                }
                            });
                        }
                    });
                }

            }
        }
        return view;
    }
    private void initView(View view)
    {
        btnBack=view.findViewById(R.id.btnBack);
        btnCheckout=view.findViewById(R.id.btnCheckout);
        txtItems=view.findViewById(R.id.txtItems);
        txtPrice=view.findViewById(R.id.txtPrice);
        txtAddress=view.findViewById(R.id.txtAddress);
        phonenumber=view.findViewById(R.id.phonenumber);
        rgPaymentMethod=view.findViewById(R.id.rgPaymentMethod);
    }
}
