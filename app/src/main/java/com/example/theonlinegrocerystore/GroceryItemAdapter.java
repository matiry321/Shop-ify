package com.example.theonlinegrocerystore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder> {
    private static final String GROCERY_ITEM_KEY = "incoming_item";
    private ArrayList<Groceryitem> items=new ArrayList<>();
    private Context context;

    public GroceryItemAdapter(Context context) {
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtName.setText(items.get(position).getName());
        holder.txtprice.setText(String.valueOf(items.get(position).getPrice())+"$");
        Glide.with(context)
                .asBitmap()
                .load(items.get(position).getImageurl())
                .into(holder.image);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,grocery_Item_Activity.class);
                intent.putExtra(GROCERY_ITEM_KEY, items.get(position));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setItems(ArrayList<Groceryitem> items)
    {
        this.items=items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder//Step1
    {
        private TextView txtprice,txtName;
        private ImageView image;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtprice=itemView.findViewById(R.id.txtprice);
            txtName=itemView.findViewById(R.id.txtName);
            image=itemView.findViewById(R.id.image);
            parent=itemView.findViewById(R.id.parent);
        }
    }
}
