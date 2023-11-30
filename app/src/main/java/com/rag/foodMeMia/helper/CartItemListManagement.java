package com.rag.foodMeMia.helper;

import android.content.Context;
import android.widget.Toast;

import com.rag.foodMeMia.activity.ShowDetailActivity;
import com.rag.foodMeMia.adapter.CartListAdapterV2;
import com.rag.foodMeMia.domain.CartItem;
import com.rag.foodMeMia.domain.CartItemList;
import com.rag.foodMeMia.domain.FoodDomainRetrieval;
import com.rag.foodMeMia.interfaces.ChangeNumberItemsListener;
import com.rag.foodMeMia.util.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CartItemListManagement {
    private Context context;
    private TinyDB tinyDB;

    public CartItemListManagement(Context context) {
        this.context = context;
        tinyDB = new TinyDB(context);
    }

    public CartItemList getCartItemList() {

        CartItemList cartItemList = tinyDB.getObject(Constants.CART_ITEM_LIST_NAME, CartItemList.class);
        return cartItemList;
    }

    public void addCartItem(FoodDomainRetrieval object, Integer numberOrder) {

        CartItemList cartItemList = tinyDB.getObject(Constants.CART_ITEM_LIST_NAME, CartItemList.class);
        if (cartItemList == null) {
            cartItemList = new CartItemList(new LinkedList<>(), "");
            CartItem cartItem = new CartItem(object, numberOrder);
            cartItemList.getCartItemList().add(cartItem);
            tinyDB.putObject(Constants.CART_ITEM_LIST_NAME, cartItemList);
        }
        if (cartItemList != null) {
            List<CartItem> cartItems = cartItemList.getCartItemList();

            boolean isCartItemFound = false;
            for (CartItem item : cartItems
            ) {
                if (item.getFoodDomainRetrieval().getUniqueId().equals(object.getUniqueId())) {
                    item.setQty(numberOrder);
                    isCartItemFound = true;
                    break;
                }
            }

            if (!isCartItemFound) {
                CartItem newCartItem = new CartItem(object, numberOrder);
                cartItemList.getCartItemList().add(newCartItem);
            }
            tinyDB.putObject(Constants.CART_ITEM_LIST_NAME, cartItemList);
        }

    }


    public void increaseQty(
            FoodDomainRetrieval foodItem
            , int position
            , ChangeNumberItemsListener changeNumberItemsListener
    ) {
        CartItemList cartItemList = getCartItemList();
        if (cartItemList != null) {
            FoodDomainRetrieval object = foodItem;
            List<CartItem> cartItems = cartItemList.getCartItemList();
            for (CartItem item : cartItems
            ) {
                if (item.getFoodDomainRetrieval().getUniqueId().equals(object.getUniqueId())) {
                    item.setQty(item.getQty() + 1);
                    break;
                }
            }
            tinyDB.putObject(Constants.CART_ITEM_LIST_NAME, cartItemList);
            changeNumberItemsListener.changed();
        }
    }


    public void decreaseQty(
            FoodDomainRetrieval foodItem
            , int position
            , ChangeNumberItemsListener changeNumberItemsListener

    ) {

        CartItemList cartItemList = getCartItemList();

        if (cartItemList != null) {
            FoodDomainRetrieval object = foodItem;
            List<CartItem> cartItems = cartItemList.getCartItemList();
            int pos = 0;
            boolean removeCartItem = false;
            for (CartItem item : cartItems
            ) {
                if (item.getFoodDomainRetrieval().getUniqueId().equals(object.getUniqueId())) {

                    if (item.getQty() == 1 && pos == 0) {
                        removeCartItem = true;
                    } else if (item.getQty() == 1) {
                        removeCartItem = true;
                        pos++;
                    } else {
                        item.setQty(item.getQty() - 1);
                        pos++;
                    }
                    break;
                }
            }
            if (removeCartItem) {
                cartItems.remove(pos);
            }

            tinyDB.putObject(Constants.CART_ITEM_LIST_NAME, cartItemList);
            changeNumberItemsListener.changed();
        }

    }

    public Double getTotalFee() {
        double fee = 0;
        List<CartItem> cartItems = getCartItemList().getCartItemList();
        for (CartItem item : cartItems
        ) {
            fee += item.getFoodDomainRetrieval().getPrice() * item.getQty();
        }
        return fee;
    }
}
