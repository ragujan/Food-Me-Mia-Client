package com.rag.foodMeMia.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.rag.foodMeMia.R;
import com.rag.foodMeMia.fragment.CardBackFragment;
import com.rag.foodMeMia.fragment.CardFrontFragment;

public class CardFlipActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_flip_container);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerCardFlip, new CardFrontFragment())
                    .commit();


            System.out.println("came here");
        }


    }

    boolean showingBack = true;
    private void flipCard() {
        System.out.println("hey flip card");
        System.out.println("hey flip card");
        System.out.println("hey flip card");
        System.out.println("hey flip card");
        System.out.println("hey flip card");
        System.out.println("hey flip card");
        int container = R.id.containerCardFlip;




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
                .replace(R.id.containerCardFlip, new CardBackFragment())

                // Add this transaction to the back stack, letting users press
                // Back to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();
        System.out.println(R.id.containerCardFlip);
    }


}
