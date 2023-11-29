package com.rag.foodMeMia.domain;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class FoodDomain implements Serializable {
    private String title;
    private String imageUrl;
    private String description;
    private Double price;
    private String added_at;
    private boolean isAvailable;
    private int star;
    private int preparationTime;
    private int calories;
    private int numberInCart;
    private String fastFoodCategory;

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public FoodDomain() {

    }

    public FoodDomain(String title, String imageUrl, String description, Double price, int star, int preparationTime, int calories) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.star = star;
        this.preparationTime = preparationTime;
        this.calories = calories;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getFastFoodCategory() {
        return fastFoodCategory;
    }

    public void setFastFoodCategory(String fastFoodCategory) {
        this.fastFoodCategory = fastFoodCategory;
    }

    public class SortByAddedDate implements Comparator<FoodDomain> {

        //this will sort from oldest to newest, to get the newest to oldest, you can just reverse it, you know that already boiii.
        @Override
        public int compare(FoodDomain foodDomain1, FoodDomain foodDomain2) {
            try {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date fdDate1 = simpleDateFormat.parse(foodDomain1.getAdded_at());
                Date fdDate2 = simpleDateFormat.parse(foodDomain2.getAdded_at());

                return fdDate1.compareTo(fdDate2);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public String getAdded_at() {
        return added_at;
    }

    public void setAdded_at(String added_at) {
        this.added_at = added_at;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
