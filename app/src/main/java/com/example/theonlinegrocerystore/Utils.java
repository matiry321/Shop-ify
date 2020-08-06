package com.example.theonlinegrocerystore;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String TAG ="Utils" ;
    private static int ID=0;
    private static int ORDER_ID = 0;
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
        Groceryitem milk = new Groceryitem("Milk","Milk is a nutrient-rich,while liquid food produced by the mammary glands of mammals","https://cdn.grofers.com/app/images/products/full_screen/pro_32685.jpg","Drink",2.3,0);
        allItems.add(milk);

        Groceryitem soda = new Groceryitem("Soda","Tastes Yummm","https://www.smartlifetechsolutions.com/wp-content/uploads/2019/02/soda.jpg","Drink",0.99,15);
        soda.setPopularityPoint(5);
        soda.setUserPoint(15);
        allItems.add(soda);


        Groceryitem icecream = new Groceryitem("Ice Cream","Delicious","https://www.bigbasket.com/media/uploads/p/l/40012979_2-amul-ice-cream-choco-caramel.jpg","Food",5.4,10);
        icecream.setPopularityPoint(10);
        icecream.setUserPoint(7);
        allItems.add(icecream);

        Groceryitem spaghetti = new Groceryitem("Spaghetti","It is a long, thin, solid, cylindrical pasta","https://www.rachaelrayshow.com/sites/default/files/styles/video_1920x1080/public/images/2019-02/sweet_and_sour_and_smoky_-_red_onion_and_smoked_bacon_spaghetti_with_cherry_peppers_1920.jpg?itok=7M_1uZin","Food",3.85,6);
        allItems.add(spaghetti);

        Groceryitem soap = new Groceryitem("Soap","Soap is a salt of a fatty acid[1] used in a variety of cleansing and lubricant","https://n4.sdlcdn.com/imgs/a/s/8/Yardley-English-Lavender-Bath-Soap-SDL510890431-1-afa1d.jpg","Cleanser",5.4,10);
        allItems.add(soap);

        Groceryitem juice = new Groceryitem("Juice","It's a drink that is made from the extraction of a real fruit juice and some additive ingredients","data:image/jpeg;base64,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","Drink",3.45,25);
        allItems.add(juice);

        Groceryitem walnut = new Groceryitem("Walnut","Walnuts provide healthy fats, fiber, vitamins and minerals — and that's just the beginning of how they may support your health","https://5.imimg.com/data5/GR/HO/MY-60544801/dried-walnuts-500x500.jpg","Nuts",5.6,4);
        allItems.add(walnut);

        Groceryitem pistachio = new Groceryitem("Pistachio","Pistachios are edible seeds that contain healthy fats, protein, fiber, and antioxidants","https://5.imimg.com/data5/YA/BY/MY-42240619/fresh-pistachio-nuts-500x500.jpg","Nuts",9.85,15);
        allItems.add(pistachio);


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
    public static void addItemTOCart(Context context,Groceryitem item) {
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

    public static ArrayList<String> getCategories(Context context)//why do we define arraylists in utils class and define the context
    {
        ArrayList<Groceryitem> allItems = getAllItems(context);
        if(null!=allItems)
        {
            ArrayList<String> categories = new ArrayList<>();
            for(Groceryitem item :allItems)
            {
                boolean doesExist=false;
                for(String s:categories)
                {
                    if(item.getCategory().equals(s))
                    {
                        doesExist = true;
                    }
                }
                if(!doesExist)
                {
                    categories.add(item.getCategory());
                }
            }
            return categories;
        }
        return null;
    }

    public static ArrayList<Groceryitem> searchforItem(Context context,String text) {
        ArrayList<Groceryitem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<Groceryitem> items = new ArrayList<>();
            for (Groceryitem item : allItems) {
                if (item.getName().equalsIgnoreCase(text)) {
                    items.add(item);
                }
                String[] names = item.getName().split(" ");
                for (int i = 0; i < names.length; i++) {
                    if (text.equalsIgnoreCase(names[i])) {
                        boolean doesExist = false;

                        for(Groceryitem j:items)
                        {
                            if(j.getId() == item.getId())
                            {
                                doesExist = true;
                            }
                        }
                        if(!doesExist)
                        {
                            items.add(item);
                        }

                    }
                }
            }
            return items;
        }
        return null;
    }

    public  static  ArrayList<Groceryitem> getItemByCategory(Context context,String category)
    {
        ArrayList<Groceryitem> allItems = getAllItems(context);
        if(null!=allItems)
        {
            ArrayList<Groceryitem> items = new ArrayList<>();
            for(Groceryitem item:allItems)
            {
                if(item.getCategory().equals(category))
                {
                    items.add(item);
                }
            }
            return items;
        }
        return null;
    }
    public static ArrayList<Groceryitem> getCartItems(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<Groceryitem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY,null),groceryListType);
        return cartItems;
    }
    public static void deleteItemFromCart(Context context,Groceryitem item)
    {
        ArrayList<Groceryitem> cartItems= getCartItems(context);
        if(null!=cartItems)
        {
            ArrayList<Groceryitem> newItems = new ArrayList<>();
            for(Groceryitem i: cartItems)
            {
                if(i.getId()!=item.getId())
                {
                    newItems.add(i);
                }
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(CART_ITEMS_KEY);
            editor.putString(CART_ITEMS_KEY,gson.toJson(newItems));
            editor.commit();
        }
    }
    public static void clearcartitems(Context context)//Clearing the cart items
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.commit();
    }
    public static void increasepopularitypoint(Context context,Groceryitem item,int points)
    {
        ArrayList<Groceryitem> allItems = getAllItems(context);
        if(null != allItems)
        {
            ArrayList<Groceryitem> newItems = new ArrayList<>();
            for(Groceryitem i:allItems)
            {
                if(i.getId() == item.getId())
                {
                    i.setPopularityPoint(i.getPopularityPoint() + points);
                    newItems.add(i);
                }else {
                    newItems.add(i);
                }
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            Gson gson = new Gson();
            editor.putString(ALL_ITEMS_KEY,gson.toJson(newItems));
            editor.commit();
        }
    }

    public static void ChangeUserPoint(Context context,Groceryitem item,int points)
    {
        Log.d(TAG, "ChangeUserPoint: Attempting to add" + points + "to" +item.getName());
        ArrayList<Groceryitem> allItems  = getAllItems(context);
        if(null != allItems)
        {
            ArrayList<Groceryitem> newitems = new ArrayList<>();
            for(Groceryitem i:allItems)
            {
                if(i.getId() == item.getId())
                {
                    i.setUserPoint(i.getUserPoint() + points);
                }
                newitems.add(i);
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY,gson.toJson(newitems));
            editor.commit();
        }
    }

    public static int getOrderId() {
        ORDER_ID++;
        return ORDER_ID;
    }

    public static int getID() {
        ID++;
        return ID;
    }
}

