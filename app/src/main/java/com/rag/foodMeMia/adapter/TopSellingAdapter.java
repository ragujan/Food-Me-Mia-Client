package com.rag.foodMeMia.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rag.foodMeMia.R;
import com.rag.foodMeMia.domain.TopSellingFoodDomain;
import com.rag.foodMeMia.viewHolders.TopSellingViewHolder;

import java.util.List;

public class TopSellingAdapter extends RecyclerView.Adapter<TopSellingViewHolder> {

    public TopSellingAdapter(List<TopSellingFoodDomain> domains) {
        this.domains = domains;
    }

    List<TopSellingFoodDomain> domains;
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

        int drawableResouceId = holder.itemView.getContext()
                .getResources()
                .getIdentifier(
                        domains.get(position).getPic()
                        ,"drawable"
                        ,holder.itemView.getContext().getPackageName()
                );
        Glide.with(holder.itemView.getContext()).load(drawableResouceId).into(holder.getPic());
    }

    @Override
    public int getItemCount() {
        return domains.size();
    }
}
