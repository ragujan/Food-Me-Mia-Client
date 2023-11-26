package com.rag.foodMeMia.domain;

public class FoodItem {
    private String foodName;
    private String foodPic;

    public FoodItem(String foodName, String foodPic) {
        this.foodName = foodName;
        this.foodPic = foodPic;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPic() {
        return foodPic;
    }

    public void setFoodPic(String foodPic) {
        this.foodPic = foodPic;
    }
}
