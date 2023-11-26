package com.rag.foodMeMia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rag.foodMeMia.databinding.ActivityShowDetailBinding;
import com.rag.foodMeMia.domain.FoodDomain;
import com.rag.foodMeMia.helper.ManagementCart;

import java.text.DecimalFormat;

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
    private FoodDomain object;
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
            object = (FoodDomain) getIntent().getSerializableExtra("object", FoodDomain.class);

        } else {
            object = (FoodDomain) getIntent().getSerializableExtra("object");
        }

        int drawableResourceId = this.getResources().getIdentifier(object.getImageUrl()
                , "drawable"
                , this.getPackageName());

        Glide.with(this)
                .load(drawableResourceId)
                .into(picFood);

        titleTxt.setText(object.getTitle());
        binding.detailViewPrice.setText("$ " + object.getPrice());
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));
        caloryTxt.setText(String.valueOf(object.getCalories()));
        starTxt.setText(String.valueOf(object.getStar()));
        timeTxt.setText(String.valueOf(object.getPreparationTime() )+ " minutes");
        totalPriceText.setText("$ " +String.valueOf(numberOrder * object.getPrice()));


        binding.detailPlusQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder+1;
                numberOrderTxt.setText(String.valueOf(numberOrder));

                totalPriceText.setText("$ " +decfor.format(numberOrder * object.getPrice()));

                detailViewQty.setText(String.valueOf(numberOrder));
                System.out.println("hey hye");
//                Toast toast = Toast.makeText(ShowDetailActivity.this,"Clicked Plus",Toast.LENGTH_SHORT );
//                toast.show();

            }
        });
        binding.detailMinusQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOrder >1){
                    numberOrder = numberOrder-1;
                }

                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceText.setText("$ " +decfor.format(numberOrder * object.getPrice()));
                detailViewQty.setText(String.valueOf(numberOrder));


            }
        });
        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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