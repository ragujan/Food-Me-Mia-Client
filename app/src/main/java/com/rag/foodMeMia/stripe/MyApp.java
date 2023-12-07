package com.rag.foodMeMia.stripe;

import android.app.Application;

import com.stripe.android.PaymentConfiguration;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_51J7i2wKy85cwwCHPPvi6Zc0GGkisZlVRIxi6ln8rVs66cBEGaJcYfKEp1rY5auwhBVAYcvqHvRE49deQqtXXumb000Wg1MVKOK"
        );
    }
}