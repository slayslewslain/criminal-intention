package com.augmentis.ayp.criminalintention;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

public abstract class SingleFragmentActivity extends FragmentActivity {

    public static final String TAG = "SingleFragmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);

        if(f == null) {
            f = onCreateFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_container, f)
                    .commit();
            Log.d(TAG, "Fragment is created");
        } else {
            Log.d(TAG, "Fragment didnt create");
        }
    }

    protected abstract Fragment onCreateFragment();
}