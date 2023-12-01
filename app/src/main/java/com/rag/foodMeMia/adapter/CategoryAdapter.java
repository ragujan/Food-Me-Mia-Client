package com.rag.foodMeMia.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rag.foodMeMia.R;
import com.rag.foodMeMia.domain.CategoryDomain;
import com.rag.foodMeMia.domain.FastFoodCategory;
import com.rag.foodMeMia.interfaces.ScrollToRecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<CategoryDomain> categoryDomains;
    ScrollToRecyclerView scrollToRecyclerView;
    String categoryToLoad;

    public CategoryAdapter(List<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }

    public CategoryAdapter(List<CategoryDomain> categoryDomains, ScrollToRecyclerView scrollToRecyclerView, String categoryToLoad) {
        this.categoryDomains = categoryDomains;
        this.scrollToRecyclerView = scrollToRecyclerView;
        this.categoryToLoad = categoryToLoad;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_category, parent, false);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categoryDomains.get(position).getTitle());
        String picUrl = "category_"+ categoryDomains.get(position).getPic().toLowerCase();

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.categoryPic);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryToLoad = categoryDomains.get(position).getTitle();
                scrollToRecyclerView.scrolled();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }
}
