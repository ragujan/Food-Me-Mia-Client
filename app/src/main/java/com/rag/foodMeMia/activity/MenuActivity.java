package com.rag.foodMeMia.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rag.foodMeMia.R;
import com.rag.foodMeMia.adapter.AllFoodListAdapter;
import com.rag.foodMeMia.adapter.CategoryAdapter;
import com.rag.foodMeMia.adapter.GridAdapter;
import com.rag.foodMeMia.adapter.PopularViewAdapter;
import com.rag.foodMeMia.adapter.TopSellingAdapter;
import com.rag.foodMeMia.databinding.ActivityMenu2Binding;
import com.rag.foodMeMia.domain.CategoryDomain;
import com.rag.foodMeMia.domain.FoodDomain;
import com.rag.foodMeMia.domain.FoodDomainRetrieval;
import com.rag.foodMeMia.domain.FoodItem;
import com.rag.foodMeMia.domain.TopSellingFoodDomain;
import com.rag.foodMeMia.helper.FoodItemRetrievelViewModel;
import com.rag.foodMeMia.util.Constants;
import com.rag.foodMeMia.util.firebaseUtil.FoodListRetrieval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2, topSellingAdapter, gridLayoutRecyclerViewAdapter;
    private RecyclerView recyclerViewCategoryList, recylerViewPopulerList, recylerViewTopSellingList, gridLayoutRecyclerView;

    private RecyclerView allFoodRecyclerView;
    private AllFoodListAdapter allFoodRecyclerViewAdapter;

    private ActivityMenu2Binding binding;
    private FoodItemRetrievelViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_2);
        binding = ActivityMenu2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerViewCategory();
        recyclerViewPopular();
        setRecylerViewTopSelling();
//        recyclerViewGridItems();
        viewFoodItems();
        binding.carBtnNavText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        binding.cartBtnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }


    private void setRecylerViewTopSelling() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recylerViewTopSellingList = findViewById(R.id.topSellingRecylerView);
        recylerViewTopSellingList.setLayoutManager(linearLayoutManager);


        List<TopSellingFoodDomain> topSellingFoodDomains = new LinkedList<>();
        topSellingFoodDomains.add(new TopSellingFoodDomain("Potato Chips", 100.00, "potato_chips"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Poke BBQ", 1400.00, "poke_bbq"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Veggie Burger", 500.00, "veggie_burger"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Smoothie Juice", 1200.00, "smoothi_juice"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Beef Fry", 1500.00, "beef_fry"));

        topSellingFoodDomains.add(new TopSellingFoodDomain("Potato Chips", 100.00, "potato_chips"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Poke BBQ", 1400.00, "poke_bbq"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Veggie Burger", 500.00, "veggie_burger"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Smoothie Juice", 1200.00, "smoothi_juice"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Beef Fry", 1500.00, "beef_fry"));


        topSellingFoodDomains.add(new TopSellingFoodDomain("Potato Chips", 100.00, "potato_chips"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Poke BBQ", 1400.00, "poke_bbq"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Veggie Burger", 500.00, "veggie_burger"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Smoothie Juice", 1200.00, "smoothi_juice"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Beef Fry", 1500.00, "beef_fry"));

        topSellingFoodDomains.add(new TopSellingFoodDomain("Potato Chips", 100.00, "potato_chips"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Poke BBQ", 1400.00, "poke_bbq"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Veggie Burger", 500.00, "veggie_burger"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Smoothie Juice", 1200.00, "smoothi_juice"));
        topSellingFoodDomains.add(new TopSellingFoodDomain("Beef Fry", 1500.00, "beef_fry"));
        topSellingAdapter = new TopSellingAdapter(topSellingFoodDomains);
        recylerViewTopSellingList.setAdapter(topSellingAdapter);
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recylerViewPopulerList = findViewById(R.id.recyclerView2);
        recylerViewPopulerList.setLayoutManager(linearLayoutManager);

        List<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("Pepperoni Pizza", "pizza1", "slices pepperoni", 13.0, 5, 5, 1000));
        foodList.add(new FoodDomain("Cheese Burger", "burger", "special sauce, lettuce, tomato ", 15.20, 5, 5, 1000));
        foodList.add(new FoodDomain("Vegetable Burger", "pizza3", "Olive oil, vegetable oil, pittled kalamata", 25.0, 5, 5, 1000));
        foodList.add(new FoodDomain("Doner Kebab Gyro", "kebab", "Olive oil, vegetable oil, pittled kalamata", 25.0, 5, 5, 1000));
        foodList.add(new FoodDomain("Pasta", "pasta", "Olive oil, vegetable oil, pittled kalamata", 25.0, 5, 5, 1000));

        adapter2 = new PopularViewAdapter(foodList);
        recylerViewPopulerList.setAdapter(adapter2);

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.HORIZONTAL
                , false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView11);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        List<CategoryDomain> categoryList = new ArrayList<>();
        categoryList.add(new CategoryDomain("Pizza", "cat_1"));
        categoryList.add(new CategoryDomain("Burger", "cat_2"));
        categoryList.add(new CategoryDomain("Hot Dog", "cat_3"));
        categoryList.add(new CategoryDomain("Drink", "cat_4"));
        categoryList.add(new CategoryDomain("Donut", "cat_5"));


        adapter = new CategoryAdapter(categoryList);

        recyclerViewCategoryList.setAdapter(adapter);

    }

    public void recyclerViewGridItems() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MenuActivity.this, 2, RecyclerView.VERTICAL, false);
        gridLayoutRecyclerView = findViewById(R.id.allFoodRecylerView);
        gridLayoutRecyclerView.setLayoutManager(gridLayoutManager);

        List<FoodItem> foodItems = new LinkedList<>();

        foodItems.add(new FoodItem("Pepporani Pizza", "pizza1"));
        foodItems.add(new FoodItem("Cheese Burger", "burger"));
        foodItems.add(new FoodItem("Vegetable Burger", "pizza3"));
        foodItems.add(new FoodItem("Doner Kebab Gyro", "kebab"));
        foodItems.add(new FoodItem("Pasta", "pasta"));


        foodItems.add(new FoodItem("Pepporani Pizza", "pizza1"));
        foodItems.add(new FoodItem("Cheese Burger", "burger"));
        foodItems.add(new FoodItem("Vegetable Burger", "pizza3"));
        foodItems.add(new FoodItem("Doner Kebab Gyro", "kebab"));
        foodItems.add(new FoodItem("Pasta", "pasta"));

        foodItems.add(new FoodItem("Pepporani Pizza", "pizza1"));
        foodItems.add(new FoodItem("Cheese Burger", "burger"));
        foodItems.add(new FoodItem("Vegetable Burger", "pizza3"));
        foodItems.add(new FoodItem("Doner Kebab Gyro", "kebab"));
        foodItems.add(new FoodItem("Pasta", "pasta"));


        foodItems.add(new FoodItem("Pepporani Pizza", "pizza1"));
        foodItems.add(new FoodItem("Cheese Burger", "burger"));
        foodItems.add(new FoodItem("Vegetable Burger", "pizza3"));
        foodItems.add(new FoodItem("Doner Kebab Gyro", "kebab"));
        foodItems.add(new FoodItem("Pasta", "pasta"));

        gridLayoutRecyclerViewAdapter = new GridAdapter(foodItems);
        gridLayoutRecyclerView.setAdapter(gridLayoutRecyclerViewAdapter);

    }

    @SuppressLint("CheckResult")
    public void viewFoodItems() {
        AllFoodListAdapter recyclerViewAdapter = new AllFoodListAdapter(new LinkedList<>());
        FoodListRetrieval.getAllFoods(recyclerViewAdapter).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        resultsSet -> {

                            if (resultsSet.get(Constants.DATA_RETRIEVAL_STATUS).equals("Success")) {
                                List<FoodDomainRetrieval> foodDomainList = (List<FoodDomainRetrieval>) resultsSet.get("foodDomainList");
                                AllFoodListAdapter adapter = (AllFoodListAdapter) resultsSet.get("adapter");
                                Map<String, Object> map = new HashMap<>();
                                map.put("foodDomainList", foodDomainList);
                                map.put("adapter", adapter);
                                foodDomainList.forEach(e -> System.out.println("food title is " + e.getTitle()));
//                                viewModel.setFoodItemsRetrieved(map);

                                GridLayoutManager gridLayoutManager = new GridLayoutManager(MenuActivity.this, 2, RecyclerView.VERTICAL, false);
                                allFoodRecyclerView = findViewById(R.id.allFoodRecylerView);
                                allFoodRecyclerView.setLayoutManager(gridLayoutManager);
                                allFoodRecyclerViewAdapter = adapter;
                                allFoodRecyclerView.setAdapter(allFoodRecyclerViewAdapter);


                            }
                        },
                        throwable -> {

                            throwable.printStackTrace();
                        }
                );

    }
}
