package com.rag.foodMeMia.domain;

import java.util.List;

public class CartItemList {
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
