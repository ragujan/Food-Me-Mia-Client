package com.rag.foodMeMia.domain;

public class TopSellingFoodDomain {
    private String title;
    private double price;
    private String pic;

    public TopSellingFoodDomain(String title, double price, String pic) {
        this.title = title;
        this.price = price;
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
