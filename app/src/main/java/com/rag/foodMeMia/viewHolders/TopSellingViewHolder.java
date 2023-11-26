package com.rag.foodMeMia.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rag.foodMeMia.R;

public class TopSellingViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView price;
    private ImageView pic;

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getPrice() {
        return price;
    }

    public void setPrice(TextView price) {
        this.price = price;
    }

    public ImageView getPic() {
        return pic;
    }

    public void setPic(ImageView pic) {
        this.pic = pic;
    }

    public TopSellingViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.topSellTitle);
        price = itemView.findViewById(R.id.topSellingFoodPrice);
        pic = itemView.findViewById(R.id.gridItemFoodName);

    }
}
