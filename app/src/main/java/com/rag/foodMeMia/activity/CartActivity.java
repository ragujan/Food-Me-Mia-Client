package com.rag.foodMeMia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rag.foodMeMia.adapter.CartListAdapter;
import com.rag.foodMeMia.adapter.CartListAdapterV2;
import com.rag.foodMeMia.databinding.ActivityCartBinding;
import com.rag.foodMeMia.helper.CartItemListManagement;
import com.rag.foodMeMia.helper.ManagementCart;
import com.rag.foodMeMia.helper.TinyDB;
import com.rag.foodMeMia.helper.Util;
import com.rag.foodMeMia.interfaces.ChangeNumberItemsListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    //    private ManagementCart managementCart;
    private CartItemListManagement cartItemListManagement;

    private TextView itemsTotalAmount, taxAmount, deliveryServicesAmount, cartFinalTotalAmount, emptyTxt;

    private double tax;
    private ScrollView scrollView;

    ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        binding = binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        managementCart = new ManagementCart(CartActivity.this);
        cartItemListManagement = new CartItemListManagement(CartActivity.this);

        TinyDB t = new TinyDB(CartActivity.this);

//        t.clear();
        initView();
        if (!isCartListEmpty()) {
            System.out.println("cart is not empty");
            calculateCard();
            initList();
        } else {

            System.out.println("cart is empty");
        }

        binding.homeBtnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        binding.homeBtnNavText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {

        itemsTotalAmount = binding.itemsTotalAmount;
        taxAmount = binding.taxAmount;
        deliveryServicesAmount = binding.deliveryServicesAmount;
        cartFinalTotalAmount = binding.cartTotal;
        recyclerViewList = binding.myCartRecylerView;
        scrollView = binding.myCartScrollView;
        emptyTxt = binding.emptyCartMessage;


    }

    private boolean isCartListEmpty() {
        boolean state = false;
        if (cartItemListManagement.getCartItemList() == null) {
            return true;
        }
        return false;

    }

    private void initList() {
        if (cartItemListManagement.getCartItemList().getCartItemList() == null) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
//        adapter = new CartListAdapter(managementCart.getListCart(), CartActivity.this, new ChangeNumberItemsListener() {
//            @Override
//            public void changed() {
//                Toast.makeText(CartActivity.this, "Making toast hi hi", Toast.LENGTH_SHORT).show();
//                calculateCard();
//            }
//        });
//        if (cartItemListManagement.getCartItemList() != null) {
        adapter = new CartListAdapterV2(cartItemListManagement.getCartItemList(), CartActivity.this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();

            }
        });
        recyclerViewList.setAdapter(adapter);

//        }
//        adapter = new CartListAdapter(managementCart.getListCart(), CartActivity.this, new ChangeNumberItemsListener() {
//            @Override
//            public void changed() {
//                calculateCard();
//
//            }
//        });

//        if (managementCart.getListCart().isEmpty()) {
//            emptyTxt.setVisibility(View.VISIBLE);
//            scrollView.setVisibility(View.GONE);
//        } else {
//            emptyTxt.setVisibility(View.GONE);
//            scrollView.setVisibility(View.VISIBLE);
//        }


    }

    private void calculateCard() {

        double percentTax = 0.02;
        double deliveryCharge = 10.00;

//        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100.0) / 100.0;
//
//        double total = Math.round((managementCart.getTotalFee() + tax + deliveryCharge) * 100.00) / 100.00;
//
//        double itemTotal = Math.round(managementCart.getTotalFee() * 100.00) / 100.00;

        tax = Math.round((cartItemListManagement.getTotalFee() * percentTax) * 100.0) / 100.0;

        double total = Math.round((cartItemListManagement.getTotalFee() + tax + deliveryCharge) * 100.00) / 100.00;

        double itemTotal = Math.round(cartItemListManagement.getTotalFee() * 100.00) / 100.00;


        itemsTotalAmount.setText("$ " + Double.toString(itemTotal));
        taxAmount.setText("$ " + Double.toString(tax));
        deliveryServicesAmount.setText("$ " + Double.toString(deliveryCharge));
        cartFinalTotalAmount.setText("$ " + Util.twoDecimalPrice(total));


    }

}
