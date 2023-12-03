package com.rag.foodMeMia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rag.foodMeMia.R;
import com.rag.foodMeMia.databinding.ActivityAuthBinding;
import com.rag.foodMeMia.fragment.LoginFragment;
import com.rag.foodMeMia.fragment.RegisterFragment;
import com.rag.foodMeMia.helper.ItemViewModel;

public class AuthActivity extends AppCompatActivity {

    ActivityAuthBinding binding;
    private FirebaseAuth mAuth;

    private ItemViewModel viewModel;

    //    @SuppressLint("MissingInflatedId")
    @Override
    public void onStart() {
        super.onStart();
        System.out.println("on start");
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(AuthActivity.this, UserHomeActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("on create ");
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(AuthActivity.this, UserHomeActivity.class);
            startActivity(intent);
        } else {
            loadFragment(new LoginFragment());
        }
//        setContentView(R.layout.activity_main);


        binding.showLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new LoginFragment());
            }
        });
        binding.showRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new RegisterFragment());
            }
        });


        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getUserRegisterSuccessful().observe(this, item->{
            if(item.booleanValue()){
                loadFragment(new LoginFragment());
            }
        } );
    }

    private void checkUserAuth() {

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayoutContainer, fragment.getClass(), null)
                .setReorderingAllowed(true)
                .commit();
    }
}