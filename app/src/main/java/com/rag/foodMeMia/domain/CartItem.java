package com.rag.foodMeMia.domain;

import java.io.Serializable;

public class CartItem implements Serializable {
    private FoodDomainRetrieval foodDomainRetrieval;

    private Integer qty;

    public CartItem() {
    }

    public CartItem(FoodDomainRetrieval foodDomainRetrieval, Integer qty) {
        this.foodDomainRetrieval = foodDomainRetrieval;
        this.qty = qty;
    }

    public FoodDomainRetrieval getFoodDomainRetrieval() {
        return foodDomainRetrieval;
    }

    public void setFoodDomainRetrieval(FoodDomainRetrieval foodDomainRetrieval) {
        this.foodDomainRetrieval = foodDomainRetrieval;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }


}
