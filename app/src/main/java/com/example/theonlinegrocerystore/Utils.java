package com.example.theonlinegrocerystore;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static int ID=0;
    private static final String DB_NAME="Fake_database";
    private static final String ALL_ITEMS_KEY="fake_database";//Why do we initialise this psf keys here
    private static final String CART_ITEMS_KEY="cart_items";
    private static Gson gson= new Gson();
    private static Type groceryListType=new TypeToken<ArrayList<Groceryitem>>(){}.getType();

    public static void initSharedPreference(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<Groceryitem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY,null),groceryListType);
        if(null==allItems)
        {
            initAllItems(context);
        }

    }
    public static void clearSharedPreferences(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    private static void  initAllItems(Context context)
    {
        ArrayList<Groceryitem> allItems = new ArrayList<>();
        Groceryitem milk = new Groceryitem("Milk","Milk is a nutrient-rich,while liquid food produced by the mammary glands of mammals","https://cdn.grofers.com/app/images/products/full_screen/pro_32685.jpg","drink",2.3,0);
        allItems.add(milk);

        Groceryitem soda = new Groceryitem("Soda","Tastes Yummm","https://www.smartlifetechsolutions.com/wp-content/uploads/2019/02/soda.jpg","Drink",0.99,15);
        soda.setPopularityPoint(5);
        soda.setUserPoint(15);
        allItems.add(soda);

        Groceryitem icecream = new Groceryitem("Ice Cream","Delicious","https://5.imimg.com/data5/QK/BE/GLADMIN-8998678/choco-brownie-dip-500x500.jpg","food",5.4,10);
        icecream.setPopularityPoint(10);
        icecream.setUserPoint(7);
        allItems.add(icecream);
        //allItems.add(icecream);

        //Now loading the data in sp

        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY,gson.toJson(allItems));
        editor.commit();
    }

    //Now to get this arraylist with get methods
    public static ArrayList<Groceryitem> getAllItems(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<Groceryitem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY,null),groceryListType);
        return allItems;
    }
    public static void changeRate(Context context,int itemId,int newRate)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        ArrayList<Groceryitem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY,null),groceryListType);
        if(null!=allItems)
        {
            ArrayList<Groceryitem> newItems = new ArrayList<>();
            for(Groceryitem i:allItems)
            {
                if(i.getId()==itemId)
                {
                    i.setRate(newRate);
                    newItems.add(i);
                }
                else
                {
                    newItems.add(i);
                }
            }

            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY,gson.toJson(newItems));
            editor.commit();
        }
    }

    public static void addReview(Context context,Review review)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        ArrayList<Groceryitem> allItems = getAllItems(context);
        if (null!=allItems)
        {
            ArrayList<Groceryitem> newItems = new ArrayList<>();
            for(Groceryitem i: allItems)
            {
                if(i.getId()==review.getGroceryItemId())
                {
                    ArrayList<Review> reviews = i.getReviews();
                    reviews.add(review);
                    i.setReviews(reviews);
                    newItems.add(i);
                }
                else
                {
                    newItems.add(i);
                }
            }
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY,gson.toJson(newItems));
            editor.commit();
        }
    }
    public static ArrayList<Review> getReviewsById(Context context,int itemId)
    {

        ArrayList<Groceryitem> allItems=getAllItems(context);
        if(null!=allItems)
        {
            for(Groceryitem i:allItems)
            {
                if(i.getId()==itemId)
                {
                    ArrayList<Review> reviews = i.getReviews();
                    return reviews;
                }
            }
        }
        return null;
    }
    public static void addItemTOCart(Context context,Groceryitem item)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<Groceryitem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY,null),groceryListType);
        if(cartItems == null)
        {
            cartItems = new ArrayList<>();
        }
        cartItems.add(item);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.putString(CART_ITEMS_KEY,gson.toJson(cartItems));
        editor.commit();

    }
    public static ArrayList<Groceryitem> getCartItems(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<Groceryitem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY,null),groceryListType);
        return cartItems;
    }

    public static int getID() {
        ID++;
        return ID;
    }
}
