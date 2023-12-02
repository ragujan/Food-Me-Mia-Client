package com.rag.foodMeMia.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.rag.foodMeMia.R;
import com.rag.foodMeMia.databinding.ActivityImageTestBinding;
import com.rag.foodMeMia.util.ImageConverter;
import com.rag.foodMeMia.util.ImageRoundedBorder;

public class ImageTestActivity extends AppCompatActivity {

    ImageView imageView;
    ActivityImageTestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageView = binding.imageView3;

//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.fast_food_items_on_a_table);
//        Bitmap cicularBitMap1 = ImageConverter.getRoundedCornerBitmap(bitmap,20);
//
//        imageView.setImageBitmap(cicularBitMap1);
        ImageRoundedBorder.set(this,imageView,R.drawable.fast_food_items_on_a_table,10);


    }
}