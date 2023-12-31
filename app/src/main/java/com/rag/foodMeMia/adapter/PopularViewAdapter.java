package com.rag.foodMeMia.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rag.foodMeMia.R;
import com.rag.foodMeMia.activity.ShowDetailActivity;
import com.rag.foodMeMia.domain.FoodDomainRetrieval;

import java.util.List;

public class PopularViewAdapter extends RecyclerView.Adapter<PopularViewAdapter.ViewHolder> {

    List<FoodDomainRetrieval> popularFoodList;

    public PopularViewAdapter(List<FoodDomainRetrieval> popularFoodList) {
        this.popularFoodList = popularFoodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_popular, parent, false);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(popularFoodList.get(position).getTitle());
        holder.fee.setText(String.valueOf(popularFoodList.get(position).getPrice()));

        Glide.with(holder.itemView.getContext()).load(popularFoodList.get(position).getImageUrl()).into(holder.pic);

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", popularFoodList.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }
    public void updateData(List<FoodDomainRetrieval> newData) {
        popularFoodList = newData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, fee;
        private ImageView pic;

        private ImageView addBtn;

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getFee() {
            return fee;
        }

        public void setFee(TextView fee) {
            this.fee = fee;
        }

        public ImageView getPic() {
            return pic;
        }

        public void setPic(ImageView pic) {
            this.pic = pic;
        }

        public ImageView getAddBtn() {
            return addBtn;
        }

        public void setAddBtn(ImageView addBtn) {
            this.addBtn = addBtn;
        }

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            title = itemView.findViewById(R.id.foodTitle);
            pic = itemView.findViewById(R.id.foodImage);
            fee = itemView.findViewById(R.id.foodFee);
            addBtn = itemView.findViewById(R.id.addBtn);


        }
    }

    @Override
    public int getItemCount() {
        return popularFoodList.size();
    }

}
