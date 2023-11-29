package com.rag.foodMeMia.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rag.foodMeMia.R;
import com.rag.foodMeMia.activity.ShowDetailActivity;
import com.rag.foodMeMia.domain.FoodDomainRetrieval;
import com.rag.foodMeMia.domain.TopSellingFoodDomain;
import com.rag.foodMeMia.viewHolders.TopSellingViewHolder;

import java.util.List;

public class TopSellingAdapter extends RecyclerView.Adapter<TopSellingViewHolder> {

    public TopSellingAdapter(List<FoodDomainRetrieval> domains) {
        this.domains = domains;
    }

    List<FoodDomainRetrieval> domains;
    @NonNull
    @Override
    public TopSellingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_top_selling,parent,false);

        return new TopSellingViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TopSellingViewHolder holder, int position) {
        holder.getTitle().setText(domains.get(position).getTitle());
        holder.getPrice().setText(String.valueOf(domains.get(position).getPrice()));
        Glide.with(holder.itemView.getContext()).load(domains.get(position).getImageUrl()).into(holder.getPic());

        holder.itemView.findViewById(R.id.topSellingFoodConstraintLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", domains.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
    public void updateData(List<FoodDomainRetrieval> newData) {
        domains = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return domains.size();
    }
}
