package com.rag.foodMeMia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rag.foodMeMia.R;
import com.rag.foodMeMia.domain.CartItem;
import com.rag.foodMeMia.domain.CartItemList;
import com.rag.foodMeMia.domain.FoodDomain;
import com.rag.foodMeMia.domain.FoodDomainRetrieval;
import com.rag.foodMeMia.helper.CartItemListManagement;
import com.rag.foodMeMia.helper.ManagementCart;
import com.rag.foodMeMia.helper.TinyDB;
import com.rag.foodMeMia.interfaces.ChangeNumberItemsListener;
import com.rag.foodMeMia.util.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CartListAdapterV2 extends RecyclerView.Adapter<CartListAdapterV2.ViewHolder> {

    private CartItemListManagement cartItemListManagement;

    ChangeNumberItemsListener changeNumberItemsListener;

    private CartItemList cartItemList;


    public CartListAdapterV2(CartItemList cartItemList, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.cartItemList = cartItemList;
        this.cartItemListManagement = new CartItemListManagement(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartListAdapterV2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapterV2.ViewHolder holder, int position) {
        FoodDomainRetrieval foodDomain = cartItemList.getCartItemList().get(position).getFoodDomainRetrieval();
        Integer cartQty = cartItemList.getCartItemList().get(position).getQty();
        System.out.println("cart qty is " + cartQty);
        double itemFee = foodDomain.getPrice();
        int numberInCart = foodDomain.getNumberInCart();
        holder.title.setText(foodDomain.getTitle());
        holder.feeEacItem.setText("$ " + Double.toString(itemFee));
        holder.totalEachItem.setText("$ " + Math.round((numberInCart * itemFee)));
        holder.qtyNum.setText(Integer.toString(cartQty));
        holder.totalEachItem.setText(Double.toString(foodDomain.getPrice()* cartQty));

        Glide.with(holder.itemView.getContext())
                .load(foodDomain.getImageUrl())
                .into(holder.pic);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                FoodDomainRetrieval foodItem = cartItemList.getCartItemList().get(position).getFoodDomainRetrieval();

                FoodDomainRetrieval object = foodItem;
                List<CartItem> cartItems = cartItemList.getCartItemList();
                for (CartItem item : cartItems
                ) {
                    if (item.getFoodDomainRetrieval().getUniqueId().equals(object.getUniqueId())) {
                        item.setQty(item.getQty() + 1);
                        break;
                    }
                }
                TinyDB tinyDB = new TinyDB(holder.itemView.getContext());
                tinyDB.putObject(Constants.CART_ITEM_LIST_NAME, cartItemList);
                changeNumberItemsListener.changed();
                notifyDataSetChanged();

            }
        });
        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                FoodDomainRetrieval foodItem = cartItemList.getCartItemList().get(position).getFoodDomainRetrieval();
                FoodDomainRetrieval object = foodItem;
                List<CartItem> cartItems = cartItemList.getCartItemList();
                boolean removeCartItem = false;
                for (CartItem item : cartItems
                ) {
                    if (item.getFoodDomainRetrieval().getUniqueId().equals(object.getUniqueId())) {

                        if (item.getQty() == 1 && position == 0) {
                            removeCartItem = true;
                        } else if (item.getQty() == 1) {
                            removeCartItem = true;
                        } else {
                            item.setQty(item.getQty() - 1);
                        }
                        break;
                    }
                }
                if (removeCartItem) {
                    cartItems.remove(position);
                }

                TinyDB tinyDB = new TinyDB(holder.itemView.getContext());
                tinyDB.putObject(Constants.CART_ITEM_LIST_NAME, cartItemList);
                changeNumberItemsListener.changed();
                notifyDataSetChanged();

            }
        });

    }

    public Integer retrieveCartItemQty(int position) {
        return cartItemList.getCartItemList().get(position).getQty();
    }

    @Override
    public int getItemCount() {
        return cartItemList.getCartItemList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, feeEacItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem;
        public TextView qtyNum;


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
