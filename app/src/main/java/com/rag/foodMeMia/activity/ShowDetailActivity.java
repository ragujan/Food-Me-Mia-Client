package com.rag.foodMeMia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rag.foodMeMia.databinding.ActivityShowDetailBinding;
import com.rag.foodMeMia.domain.CartItem;
import com.rag.foodMeMia.domain.CartItemList;
import com.rag.foodMeMia.domain.FoodDomainRetrieval;
import com.rag.foodMeMia.helper.CartItemListManagement;
import com.rag.foodMeMia.helper.ManagementCart;
import com.rag.foodMeMia.helper.TinyDB;
import com.rag.foodMeMia.util.Constants;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShowDetailActivity extends AppCompatActivity {

    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    private ConstraintLayout addToCartBtn;
    private TextView titleTxt,
            feeTxt,
            descriptionTxt,
            numberOrderTxt,
            totalPriceText,
            starTxt,
            caloryTxt,
            timeTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private FoodDomainRetrieval object;
    private int numberOrder = 1;

    private ManagementCart managementCart;
    private CartItemListManagement cartItemListManagement;

    ActivityShowDetailBinding binding;
    private TextView detailViewQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_detail);
        binding = ActivityShowDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managementCart = new ManagementCart(ShowDetailActivity.this);
        cartItemListManagement = new CartItemListManagement(ShowDetailActivity.this);
        initView();
        getBundle();
    }

    private void getBundle() {
        System.out.println("get bundle is being called");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            object = (FoodDomainRetrieval) getIntent().getSerializableExtra("object", FoodDomainRetrieval.class);

        } else {
            object = (FoodDomainRetrieval) getIntent().getSerializableExtra("object");
        }
        Toast.makeText(ShowDetailActivity.this, "title is " + object.getTitle(), Toast.LENGTH_SHORT).show();


        Glide.with(this)
                .load(object.getImageUrl())
                .into(picFood);

        titleTxt.setText(object.getTitle());
        binding.detailViewPrice.setText("$ " + object.getPrice());
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));
        caloryTxt.setText(String.valueOf(object.getCalories()));
        starTxt.setText(String.valueOf(object.getStar()));
        timeTxt.setText(String.valueOf(object.getPreparationTime()) + " minutes");
        totalPriceText.setText("$ " + String.valueOf(numberOrder * object.getPrice()));

        if (!checkAlreadyAdded()) {
            binding.cartStatusImage.setVisibility(View.INVISIBLE);
        }

        binding.detailPlusQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));

                totalPriceText.setText("$ " + decfor.format(numberOrder * object.getPrice()));

                detailViewQty.setText(String.valueOf(numberOrder));

            }
        });
        binding.detailMinusQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }

                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceText.setText("$ " + decfor.format(numberOrder * object.getPrice()));
                detailViewQty.setText(String.valueOf(numberOrder));


            }
        });
        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ++++++++++++++++++++
//                debug section starts
//                tinyDB.clear();
//                debug section ends
//                +++++++++++++++++++

                cartItemListManagement.addCartItem(object, numberOrder);

                object.setNumberInCart(numberOrder);
                managementCart.insertFood(object);
                if (checkAlreadyAdded()) {
                    binding.cartStatusImage.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(ShowDetailActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    public boolean checkAlreadyAdded() {
        boolean isAdded = false;
        TinyDB tinyDB = new TinyDB(ShowDetailActivity.this);

        CartItemList cartItemList = tinyDB.getObject(Constants.CART_ITEM_LIST_NAME, CartItemList.class);

        if (cartItemList != null) {
            List<CartItem> cartItems = cartItemList.getCartItemList();
            for (CartItem item : cartItems
            ) {
                if (item.getFoodDomainRetrieval().getUniqueId().equals(object.getUniqueId())) {

                    isAdded = true;
                    break;
                }
            }

        }
        return isAdded;

    }

    public void initView() {
        addToCartBtn = binding.addToCartBtn;
        titleTxt = binding.detailViewTitle;
        feeTxt = binding.detailViewPrice;
        descriptionTxt = binding.detailDescription;
        numberOrderTxt = binding.detailViewQty;
        plusBtn = binding.detailPlusQtyBtn;
        minusBtn = binding.detailMinusQtyBtn;
        picFood = binding.detailFoodPic;
        totalPriceText = binding.detailViewTotalAmount;
        starTxt = binding.starRate;
        caloryTxt = binding.caloryCount;
        timeTxt = binding.preparationTime;
        detailViewQty = binding.detailViewQty;

    }
}