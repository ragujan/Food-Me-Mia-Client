package com.rag.foodMeMia.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.rag.foodMeMia.R;

public class ImageRoundedBorder {
    public static void set(Context context, ImageView imageView, int resourceId, int size){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        Bitmap cicularBitMap1 = ImageConverter.getRoundedCornerBitmap(bitmap,size);

        imageView.setImageBitmap(cicularBitMap1);
    }
}
