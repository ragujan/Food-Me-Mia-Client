package com.rag.foodMeMia.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rag.foodMeMia.R;
import com.rag.foodMeMia.databinding.IntroFragmentBinding;


public class IntroActivity extends AppCompatActivity {
    private IntroFragmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_fragment);
        binding = IntroFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IntroActivity.this,MenuActivity.class);
                startActivity(i);
            }
        });

    }
}