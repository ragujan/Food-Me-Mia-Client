package com.rag.foodMeMia.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rag.foodMeMia.R;
import com.rag.foodMeMia.adapter.AllFoodListAdapter;
import com.rag.foodMeMia.adapter.CategoryAdapter;
import com.rag.foodMeMia.adapter.GridAdapter;
import com.rag.foodMeMia.adapter.PopularViewAdapter;
import com.rag.foodMeMia.adapter.TopSellingAdapter;
import com.rag.foodMeMia.databinding.ActivityMenuBinding;
import com.rag.foodMeMia.domain.CategoryDomain;
import com.rag.foodMeMia.domain.FastFoodCategory;
import com.rag.foodMeMia.domain.FoodDomainRetrieval;
import com.rag.foodMeMia.domain.FoodItem;
import com.rag.foodMeMia.helper.CategorySelectionViewModel;
import com.rag.foodMeMia.helper.FoodItemRetrievelViewModel;
import com.rag.foodMeMia.util.Constants;
import com.rag.foodMeMia.util.ImageRoundedBorder;
import com.rag.foodMeMia.util.firebaseUtil.FoodListRetrieval;
import com.rag.foodMeMia.util.firebaseUtil.Search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, gridLayoutRecyclerViewAdapter;
    private RecyclerView recyclerViewCategoryList, recylerViewPopulerList, gridLayoutRecyclerView;

    private RecyclerView allFoodRecyclerView, popularFoodRecyclerView, topSellingFoodRecyclerView;
    private AllFoodListAdapter allFoodRecyclerViewAdapter;
    private PopularViewAdapter popularViewAdapter;
    private TopSellingAdapter topSellingAdapter;

    private ActivityMenuBinding binding;
    private String searchText;


    String categoryToLoad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerViewCategory();
        recyclerViewPopular();
        setRecyclerviewTopSelling();
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

        binding.allItemsHeaderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.allItemsHeaderTextView.getCurrentTextColor() == ContextCompat.getColor(MenuActivity.this, R.color.mainOrange)) {
                    binding.allItemsHeaderTextView.setTextColor(ContextCompat.getColor(MenuActivity.this, R.color.black));
                }
                viewFoodItems();
            }
        });

        binding.seeMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollToAllFoodItems();
                if (binding.allItemsHeaderTextView.getCurrentTextColor() == ContextCompat.getColor(MenuActivity.this, R.color.mainOrange)) {
                    binding.allItemsHeaderTextView.setTextColor(ContextCompat.getColor(MenuActivity.this, R.color.black));
                }
            }
        });
        binding.searchTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText = editable.toString();

            }
        });
        binding.searchIconText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewFoodItemsBySearch(searchText);
                scrollToAllFoodItems();
            }
        });

        binding.searchTextField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b) {
                }
            }
        });

        binding.closeIconTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.searchTextField.getText().clear();
                viewFoodItems();
                binding.searchTextField.clearFocus();
            }
        });
        binding.userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, AuthActivity.class));
            }
        });

        ImageRoundedBorder.set(this,binding.banner,R.drawable.fast_food_items_on_a_table,10);
    }
    private void scrollToAllFoodItems(){
        viewFoodItemsBySearch(searchText);
    }

    @SuppressLint("CheckResult")
    public void viewFoodItemsBySearch(String searchText) {

        if (searchText.isEmpty()) return;
        AllFoodListAdapter recyclerViewAdapter = new AllFoodListAdapter(new LinkedList<>());
        Search.searchByText(recyclerViewAdapter, searchText).observeOn(AndroidSchedulers.mainThread())
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
                                binding.scrollView3.scrollTo(0, binding.allItemsHeaderTextView.getTop());
                            }
                        },
                        throwable -> {
                            throwable.printStackTrace();
                        }
                );
    }

    @SuppressLint("CheckResult")
    private void setRecyclerviewTopSelling() {


        topSellingAdapter = new TopSellingAdapter(new LinkedList<>());

        FoodListRetrieval.getTopSelling(topSellingAdapter, 6).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        resultsSet -> {

                            if (resultsSet.get(Constants.DATA_RETRIEVAL_STATUS).equals("Success")) {
                                List<FoodDomainRetrieval> foodDomainList = (List<FoodDomainRetrieval>) resultsSet.get("foodDomainList");
                                TopSellingAdapter adapter = (TopSellingAdapter) resultsSet.get("adapter");
                                Map<String, Object> map = new HashMap<>();
                                map.put("foodDomainList", foodDomainList);
                                map.put("adapter", adapter);

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                                topSellingFoodRecyclerView = findViewById(R.id.topSellingRecylerView);
                                topSellingFoodRecyclerView.setLayoutManager(linearLayoutManager);
                                topSellingAdapter = adapter;
                                topSellingFoodRecyclerView.setAdapter(topSellingAdapter);

                            }
                        },
                        throwable -> {
                            throwable.printStackTrace();
                        }
                );

    }

    @SuppressLint("CheckResult")
    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recylerViewPopulerList = findViewById(R.id.popularFoodListeItemRecyclerView);
        recylerViewPopulerList.setLayoutManager(linearLayoutManager);

        List<FoodDomainRetrieval> foodList = new ArrayList<>();

        popularViewAdapter = new PopularViewAdapter(new ArrayList<>());
        recylerViewPopulerList.setAdapter(popularViewAdapter);

        popularViewAdapter = new PopularViewAdapter(new LinkedList<>());
        FoodListRetrieval.getPopular(popularViewAdapter, 5).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        resultsSet -> {

                            if (resultsSet.get(Constants.DATA_RETRIEVAL_STATUS).equals("Success")) {
                                List<FoodDomainRetrieval> foodDomainList = (List<FoodDomainRetrieval>) resultsSet.get("foodDomainList");
                                PopularViewAdapter adapter = (PopularViewAdapter) resultsSet.get("adapter");
                                Map<String, Object> map = new HashMap<>();
                                map.put("foodDomainList", foodDomainList);
                                map.put("adapter", adapter);

                                popularFoodRecyclerView = findViewById(R.id.popularFoodListeItemRecyclerView);
                                popularFoodRecyclerView.setLayoutManager(linearLayoutManager);
                                popularViewAdapter = adapter;
                                popularFoodRecyclerView.setAdapter(popularViewAdapter);

                            }
                        },
                        throwable -> {
                            throwable.printStackTrace();
                        }

                );


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

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.HORIZONTAL
                , false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView11);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        List<CategoryDomain> categoryList = new ArrayList<>();
        List<FastFoodCategory> names = Arrays.asList(FastFoodCategory.values());


        for (int i = 0; i < names.size(); i++) {
            categoryList.add(new CategoryDomain(names.get(i).toString(), names.get(i).toString()));
        }

        categoryList.forEach(e -> System.out.println("list url is " + e.getPic()));

        adapter = new CategoryAdapter(categoryList, () -> {
            binding.scrollView3.scrollTo(0, binding.allItemsHeaderTextView.getTop());
        }, (String categoryName) -> {
            if (binding.allItemsHeaderTextView.getCurrentTextColor() == ContextCompat.getColor(MenuActivity.this, R.color.black)) {
                binding.allItemsHeaderTextView.setTextColor(ContextCompat.getColor(MenuActivity.this, R.color.mainOrange));
            }
            loadCategoryFoodList(categoryName);
        });
        recyclerViewCategoryList.setAdapter(adapter);
    }

    @SuppressLint("CheckResult")
    private void loadCategoryFoodList(String categoryToLoad) {
//        Toast.makeText(this, "Selected Category is " + categoryToLoad, Toast.LENGTH_SHORT).show();

        AllFoodListAdapter recyclerViewAdapter = new AllFoodListAdapter(new LinkedList<>());
        FoodListRetrieval.getAllFoods(recyclerViewAdapter, categoryToLoad).observeOn(AndroidSchedulers.mainThread())
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


}
