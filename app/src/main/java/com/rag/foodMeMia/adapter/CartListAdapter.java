package com.rag.foodMeMia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rag.foodMeMia.R;
import com.rag.foodMeMia.domain.FoodDomain;
import com.rag.foodMeMia.helper.ManagementCart;
import com.rag.foodMeMia.interfaces.ChangeNumberItemsListener;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    ArrayList<FoodDomain> listFoodSelected;
    private ManagementCart managementCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<FoodDomain> listFoodSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listFoodSelected = listFoodSelected;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent, false);
        System.out.println("Hey Hi Hi");
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        FoodDomain foodDomain = listFoodSelected.get(position);
        double itemFee = foodDomain.getPrice();
        int numberInCart = foodDomain.getNumberInCart();
        holder.title.setText(foodDomain.getTitle());
        holder.feeEacItem.setText("$ "+Double.toString(itemFee));
        holder.totalEachItem.setText("$ "+Math.round(( numberInCart * itemFee )) );
        holder.qtyNum.setText(Integer.toString(numberInCart));

        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(foodDomain.getImageUrl(),"drawable",
                        holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.plusNumberFood( listFoodSelected, holder.getAdapterPosition(), () -> {
                    notifyDataSetChanged();
                    changeNumberItemsListener.changed();
                });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                managementCart.minusNumberFood( listFoodSelected, holder.getAdapterPosition(), () -> {
                    notifyDataSetChanged();
                    changeNumberItemsListener.changed();
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFoodSelected.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, feeEacItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, qtyNum;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cartItemTitle);
            pic = itemView.findViewById(R.id.cartItemImage);
            feeEacItem = itemView.findViewById(R.id.cartFeeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalFeeEachItem);
            plusItem = itemView.findViewById(R.id.plusItemBtn);
            minusItem = itemView.findViewById(R.id.minusItemBtn);
            qtyNum = itemView.findViewById(R.id.qtyNum);


        }
    }
}
