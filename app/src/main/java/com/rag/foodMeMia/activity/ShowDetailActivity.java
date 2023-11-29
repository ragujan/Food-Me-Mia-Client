package com.rag.foodMeMia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rag.foodMeMia.databinding.ActivityShowDetailBinding;
import com.rag.foodMeMia.domain.FoodDomainRetrieval;
import com.rag.foodMeMia.helper.ManagementCart;
import com.rag.foodMeMia.helper.TinyDB;


import java.text.DecimalFormat;
import java.util.ArrayList;

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

    ActivityShowDetailBinding binding;
    private TextView detailViewQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_detail);
        binding = ActivityShowDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managementCart = new ManagementCart(ShowDetailActivity.this);
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


        binding.detailPlusQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));

                totalPriceText.setText("$ " + decfor.format(numberOrder * object.getPrice()));

                detailViewQty.setText(String.valueOf(numberOrder));
                System.out.println("hey hye");
//                Toast toast = Toast.makeText(ShowDetailActivity.this,"Clicked Plus",Toast.LENGTH_SHORT );
//                toast.show();

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
//                TinyDB tinyDB = new TinyDB(ShowDetailActivity.this);
//                tinyDB.putObject("myObject", object);
//
//                ArrayList<FoodDomainRetrieval> foodDomains = tinyDB.getListObject("CardList");
//                Toast.makeText(ShowDetailActivity.this, "added title is " + foodDomainRetrieval.getTitle(), Toast.LENGTH_SHORT).show();


//                debug section ends
//                +++++++++++++++++++
//
//                CartItemList cartItemList = tinyDB.getCartItemListObject(Constants.CART_ITEM_LIST_NAME);
//
//                if (cartItemList == null) {
//                    cartItemList = new CartItemList(new LinkedList<>(),"");
//                    CartItem cartItem = new CartItem(object, numberOrder);
//                    cartItemList.getCartItemList().add(cartItem);
//                    tinyDB.putCartItemListObject(Constants.CART_ITEM_LIST_NAME,cartItemList);
//
//                }
//
//                if(cartItemList !=null){
//                    List<CartItem> cartItems = cartItemList.getCartItemList();
//
//                    int position = 0;
//
//                    for (CartItem cart: cartItems
//                         ) {
//                        if(cart.getFoodDomainRetrieval().getUniqueId().equals(object.getUniqueId())){
//                            position++;
//                            break;
//                        }
//                    }
//
//                    cartItems.get(position).setQty(numberOrder);
//                }
//                List<CartItem> cartItems = cartItemList.getCartItemList();
//
//                for (CartItem item: cartItems
//                     ) {
//                    System.out.println("from cart item "+item.getFoodDomainRetrieval().getTitle());
//                }

//                tinyDB.clear();
                object.setNumberInCart(numberOrder);
                managementCart.insertFood(object);
            }
        });

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