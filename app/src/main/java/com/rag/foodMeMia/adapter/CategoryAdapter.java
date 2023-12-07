package com.rag.foodMeMia.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rag.foodMeMia.R;
import com.rag.foodMeMia.domain.CategoryDomain;
import com.rag.foodMeMia.domain.FastFoodCategory;
import com.rag.foodMeMia.helper.CategorySelectionViewModel;
import com.rag.foodMeMia.interfaces.ScrollToRecyclerView;
import com.rag.foodMeMia.interfaces.SetSelectedCategory;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<CategoryDomain> categoryDomains;
    ScrollToRecyclerView scrollToRecyclerView;
    String categoryToLoad;

    SetSelectedCategory setSelectedCategory;


    public CategoryAdapter(List<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }

    public CategoryAdapter(List<CategoryDomain> categoryDomains
            , ScrollToRecyclerView scrollToRecyclerView
            , SetSelectedCategory setSelectedCategory) {
        this.categoryDomains = categoryDomains;
        this.scrollToRecyclerView = scrollToRecyclerView;
        this.setSelectedCategory = setSelectedCategory;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_category, parent, false);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Integer adapterPosition = holder.getAdapterPosition();
        holder.categoryName.setText(categoryDomains.get(adapterPosition).getTitle());
        String picUrl = "category_" + categoryDomains.get(adapterPosition).getPic().toLowerCase();

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.categoryPic);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryToLoad = categoryDomains.get(holder.getAdapterPosition()).getTitle();
                scrollToRecyclerView.scrolled();
                setSelectedCategory.setCategory(categoryDomains.get(adapterPosition).getTitle());
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
