package com.rag.foodMeMia.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;
import com.rag.foodMeMia.R;
import com.rag.foodMeMia.databinding.ActivityUserHomeBinding;
import com.rag.foodMeMia.util.ListenerUtil;
import com.stripe.android.PaymentConfiguration;

import org.json.JSONObject;

public class UserHomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityUserHomeBinding binding;

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "hey on resumed ",Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(UserHomeActivity.this, AuthActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        binding = ActivityUserHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            binding.textView17.setText("user email is " + currentUser.getEmail());
        }else{
            startActivity(new Intent(UserHomeActivity.this, AuthActivity.class));
        }


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser != null){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(UserHomeActivity.this, AuthActivity.class));
                }
            }
        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomeActivity.this, ActivityCheckout.class));
            }
        });

        ListenerUtil.onClickBtnIntent(binding.homePageBtn,UserHomeActivity.this, MenuActivity.class);


    }


}