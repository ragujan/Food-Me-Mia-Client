package com.rag.foodMeMia.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rag.foodMeMia.R;
import com.rag.foodMeMia.domain.FoodDomainRetrieval;

import java.util.List;

public class AllFoodListAdapter extends RecyclerView.Adapter<AllFoodListAdapter.ViewHolder> {


    List<FoodDomainRetrieval> foodDomainList;
    ViewGroup parent;

    public AllFoodListAdapter(List<FoodDomainRetrieval> foodDomainList) {
        this.foodDomainList = foodDomainList;
    }

    public AllFoodListAdapter() {
    }

    public void setFoodDomainList(List<FoodDomainRetrieval> foodDomainList) {
        this.foodDomainList = foodDomainList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        this.parent = parent;

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FoodDomainRetrieval foodDomain = foodDomainList.get(position);

        holder.textView.setText(foodDomain.getTitle().toString());
//        holder..setText(foodDomain.getPrice().toString());


        Glide.with(holder.itemView.getContext()).load(foodDomain.getImageUrl()).into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return foodDomainList.size();
    }


    public void updateData(List<FoodDomainRetrieval> newData) {
//        foodDomainList.clear();

        foodDomainList = newData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gridItemFoodPic);
            textView = itemView.findViewById(R.id.gridItemFoodName);
        }
    }
}
