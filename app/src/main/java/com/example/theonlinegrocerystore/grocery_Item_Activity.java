package com.example.theonlinegrocerystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.security.Provider;
import java.util.ArrayList;

public class grocery_Item_Activity extends AppCompatActivity implements AddReviewDialog.AddReview {
    private static final String TAG = "GroceryItemActivity";
    private static final String GROCERY_ITEM_KEY = "incoming_item";
    private boolean isBound;
    private TrackUserTime mService;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackUserTime.LocalBinder binder= (TrackUserTime.LocalBinder) service;
            mService = binder.getService();
            isBound=true;
            mService.setItem(incomingItem);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;

        }
    };
    @Override
    public void onAddReviewResult(Review review) {
        Log.d(TAG,"onAddReviewResult:new review" +review);
        Utils.addReview(this,review);
        Utils.ChangeUserPoint(this,incomingItem,3);
        ArrayList<Review> reviews=Utils.getReviewsById(this,review.getGroceryItemId());
        if(null!=reviews)
        {
            adapter.setReviews(reviews);
        }


    }

    private RecyclerView reviewRecView;
    private TextView txtName,txtprice,txtDescription,txtAddReview;
    private ImageView itemImage,firstfillStar,firstemptystar,secfillStar,secemptystar,thirdfillStar,thirdemptystar;
    private Button btnAddtocart;
    private RelativeLayout firstRelLayout,secstarrellayout,thirdstarrellayout;
    private MaterialToolbar toolbar;

    private Groceryitem incomingItem;
    private ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery__item_);
        initViews();
        setSupportActionBar(toolbar);
        adapter=new ReviewAdapter();
        Intent intent = getIntent();
        if(null != intent)
        {
            incomingItem = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            if(null != incomingItem)
            {
                Utils.ChangeUserPoint(this,incomingItem,1);
                txtName.setText(incomingItem.getName());
                txtDescription.setText(incomingItem.getDescription());
                txtprice.setText(String.valueOf(incomingItem.getPrice())+"$");
                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageurl())
                        .into(itemImage);

                //Now to show reviews in recyclerview
                ArrayList<Review> reviews = Utils.getReviewsById(this,incomingItem.getId());
                reviewRecView.setAdapter(adapter);
                reviewRecView.setLayoutManager(new LinearLayoutManager(this));
                if(null != reviews)
                { if(reviews.size()>0)
                    {
                        adapter.setReviews(reviews);
                    }
                }
                btnAddtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //to add the item to the cart
                        //TODO Add item to the cart
                        Utils.addItemTOCart(grocery_Item_Activity.this,incomingItem);
                        Intent cartIntent = new Intent(grocery_Item_Activity.this,CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                    }
                });
                txtAddReview.setOnClickListener(new View.OnClickListener() { //to add review of hte item crete the dialog box then create its java file
                    @Override
                    public void onClick(View v) {
                        //TODO: show a dialog
                        AddReviewDialog dialog = new AddReviewDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(GROCERY_ITEM_KEY,incomingItem);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(),"add review");
                    }
                });
                handleRating();
            }
        }
    }
    private void handleRating()
    {
        switch (incomingItem.getRate())
        {
            case 0:
                firstemptystar.setVisibility(View.VISIBLE);
                firstfillStar.setVisibility(View.GONE);
                secemptystar.setVisibility(View.VISIBLE);
                secfillStar.setVisibility(View.GONE);
                secemptystar.setVisibility(View.VISIBLE);
                secfillStar.setVisibility(View.GONE);
                break;
            case 1:
                firstemptystar.setVisibility(View.GONE);
                firstfillStar.setVisibility(View.VISIBLE);
                secemptystar.setVisibility(View.VISIBLE);
                secfillStar.setVisibility(View.GONE);
                secemptystar.setVisibility(View.VISIBLE);
                secfillStar.setVisibility(View.GONE);
                break;
            case 2:
                firstemptystar.setVisibility(View.GONE);
                firstfillStar.setVisibility(View.VISIBLE);
                secemptystar.setVisibility(View.GONE);
                secfillStar.setVisibility(View.VISIBLE);
                secemptystar.setVisibility(View.VISIBLE);
                secfillStar.setVisibility(View.GONE);
                break;
            case 3:
                firstemptystar.setVisibility(View.GONE);
                firstfillStar.setVisibility(View.VISIBLE);
                secemptystar.setVisibility(View.GONE);
                secfillStar.setVisibility(View.VISIBLE);
                secemptystar.setVisibility(View.GONE);
                secfillStar.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        firstRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incomingItem.getRate()!=1)
                {
                    Utils.changeRate(grocery_Item_Activity.this,incomingItem.getId(),1);
                    Utils.ChangeUserPoint(grocery_Item_Activity.this,incomingItem,(1-incomingItem.getRate())+2);
                    incomingItem.setRate(1);
                    handleRating();
                }
            }
        });

        secstarrellayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incomingItem.getRate()!=2)
                {
                    Utils.changeRate(grocery_Item_Activity.this,incomingItem.getId(),2);
                    Utils.ChangeUserPoint(grocery_Item_Activity.this,incomingItem,(2-incomingItem.getRate())+2);
                    incomingItem.setRate(2);
                    handleRating();
                }
            }
        });

        thirdstarrellayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incomingItem.getRate()!=3)
                {
                    Utils.changeRate(grocery_Item_Activity.this,incomingItem.getId(),3);
                    Utils.ChangeUserPoint(grocery_Item_Activity.this,incomingItem,(3-incomingItem.getRate())+2);
                    incomingItem.setRate(3);
                    handleRating();
                }
            }
        });
    }
    private void initViews()
    {
        txtName=findViewById(R.id.txtName);
        txtprice=findViewById(R.id.txtprice);
        txtDescription=findViewById(R.id.txtDescription);
        txtAddReview=findViewById(R.id.txtAddReview);
        itemImage=findViewById(R.id.itemImage);
        firstfillStar=findViewById(R.id.firstfillStar);
        firstemptystar=findViewById(R.id.firstemptystar);
        secfillStar=findViewById(R.id.secfillStar);
        secemptystar=findViewById(R.id.secemptystar);
        thirdfillStar=findViewById(R.id.thirdfillStar);
        thirdemptystar=findViewById(R.id.thirdemptystar);
        btnAddtocart=findViewById(R.id.btnAddtocart);
        firstRelLayout=findViewById(R.id.firstRelLayout);
        secstarrellayout=findViewById(R.id.secstarrellayout);
        thirdstarrellayout=findViewById(R.id.thirdstarrellayout);
        reviewRecView=findViewById(R.id.reviewRecView);
        toolbar=findViewById(R.id.toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this,TrackUserTime.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(isBound)
        {
            unbindService(connection);
        }
    }
}