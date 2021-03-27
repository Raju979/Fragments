package com.example.android.fragmentexample2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    static final String STATE_FRAGMENT = "state_of_fragment";
    private Button mButton;
    private boolean isFragmentDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        // initialize the button
        mButton = findViewById(R.id.open_button2);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });
        if (savedInstanceState != null) {
            isFragmentDisplayed =
                    savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                // If the fragment is displayed, change button to "close".
                mButton.setText(R.string.close);
            }
        }
    }

    private void displayFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance();

        // get the fragment manager and start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // add the simple fragment
        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();

        // update the button text
        mButton.setText("Close");

        // set boolean flag to indicate fragment is open
        isFragmentDisplayed = true;
    }

    private void closeFragment() {
        // get the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // check to see if the fragment is already showing
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            // create and commit the transaction to remote the fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }

        // update the button text
        mButton.setText("Open");

        // set boolean flag to indicate fragment is closed
        isFragmentDisplayed = false;
    }

    public void launchMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the state of the fragment
        outState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
    }
}