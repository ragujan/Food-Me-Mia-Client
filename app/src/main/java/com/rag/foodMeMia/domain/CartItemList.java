package com.rag.foodMeMia.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CartItemList implements Serializable {
    private List<CartItem> cartItemList;
    private String user;

    public CartItemList() {
    }

    public CartItemList(List<CartItem> cartItemList, String user) {
        this.cartItemList = cartItemList;
        this.user = user;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
