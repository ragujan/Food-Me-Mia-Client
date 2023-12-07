package com.rag.foodMeMia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.rag.foodMeMia.R;
import com.rag.foodMeMia.databinding.ActivityCartBinding;
import com.rag.foodMeMia.databinding.ActivityIntroBinding;
import com.rag.foodMeMia.fragment.CardBackFragment;
import com.rag.foodMeMia.fragment.CardFrontFragment;
import com.rag.foodMeMia.util.ListenerUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class IntroActivity extends AppCompatActivity {
    private ActivityIntroBinding binding;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(binding.coinFlipLayout.getId(), new CardFrontFragment())
//                .commit();


        loadFragment(new CardFrontFragment());
        Observable<Long> loadingObservable = Observable.interval(2, TimeUnit.SECONDS);

        compositeDisposable.add(loadingObservable.subscribe(
                tick -> {
                    // Perform the loading action
                    if (!showingBack) {
                        flipCardBack();
                    } else {
                        flipCardFront();
                    }
                },
                throwable -> {
                    // Handle errors if needed
                }
        ));

        ListenerUtil.onClickBtnIntent(binding.getStartedButton,IntroActivity.this, MenuActivity.class);

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.coinFlipLayout, fragment.getClass(), null)
                .setReorderingAllowed(true)
                .commit();
    }

    boolean showingBack = false;
    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear(); // Dispose of all subscriptions
    }
    @Override
    public void onResume(){
        super.onResume();
        compositeDisposable.clear(); // Dispose of all subscriptions
        Observable<Long> loadingObservable = Observable.interval(2, TimeUnit.SECONDS);

        compositeDisposable.add(loadingObservable.subscribe(
                tick -> {
                    // Perform the loading action
                    if (!showingBack) {
                        flipCardBack();
                    } else {
                        flipCardFront();
                    }
                },
                throwable -> {
                    // Handle errors if needed
                }
        ));
    }
    private void flipCardBack() {


        if (showingBack) {
            getSupportFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

        showingBack = true;

        // Create and commit a new fragment transaction that adds the fragment
        // for the back of the card, uses custom animations, and is part of the
        // fragment manager's back stack.

        getSupportFragmentManager()
                .beginTransaction()

                // Replace the default fragment animations with animator
                // resources representing rotations when switching to the back
                // of the card, as well as animator resources representing
                // rotations when flipping back to the front, such as when the
                // system Back button is pressed.
                .setCustomAnimations(
                        R.anim.card_flip_right_in,
                        R.anim.card_flip_right_out,
                        R.anim.card_flip_left_in,
                        R.anim.card_flip_left_out)

                // Replace any fragments in the container view with a fragment
                // representing the next page, indicated by the just-incremented
                // currentPage variable.
                .replace(R.id.coinFlipLayout, new CardBackFragment())

                // Add this transaction to the back stack, letting users press
                // Back to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();
    }

    private void flipCardFront() {

        if (!showingBack) {
            getSupportFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

        showingBack = false;

        // Create and commit a new fragment transaction that adds the fragment
        // for the back of the card, uses custom animations, and is part of the
        // fragment manager's back stack.

        getSupportFragmentManager()
                .beginTransaction()

                // Replace the default fragment animations with animator
                // resources representing rotations when switching to the back
                // of the card, as well as animator resources representing
                // rotations when flipping back to the front, such as when the
                // system Back button is pressed.
                .setCustomAnimations(
                        R.anim.card_flip_right_in,
                        R.anim.card_flip_right_out,
                        R.anim.card_flip_left_in,
                        R.anim.card_flip_left_out)

                // Replace any fragments in the container view with a fragment
                // representing the next page, indicated by the just-incremented
                // currentPage variable.
                .replace(R.id.coinFlipLayout, new CardFrontFragment())

                // Add this transaction to the back stack, letting users press
                // Back to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();
    }


}