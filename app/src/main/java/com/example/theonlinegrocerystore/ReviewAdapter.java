package com.example.theonlinegrocerystore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<Review> reviews = new ArrayList<>();

    public ReviewAdapter()//empty constructor of the adapter class
    {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtReviews.setText(reviews.get(position).getText());
        holder.txtDate.setText(reviews.get(position).getDate());
        holder.txtUsername.setText(reviews.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setReviews(ArrayList<Review> reviews) { //why?? we use setter here
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView txtUsername,txtDate,txtReviews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUsername=itemView.findViewById(R.id.txtUsername);
            txtDate=itemView.findViewById(R.id.txtDate);
            txtReviews=itemView.findViewById(R.id.txtReviews);
        }
    }
}
