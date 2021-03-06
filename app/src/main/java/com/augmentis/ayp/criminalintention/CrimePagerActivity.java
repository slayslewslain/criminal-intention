package com.augmentis.ayp.criminalintention;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.augmentis.ayp.criminalintention.model.Crime;

import java.util.UUID;

public class CrimePagerActivity extends SingleFragmentActivity implements CrimeFragment.Callbacks {

    private UUID _crimeId;

    @Override
    protected int getLayoutResId() {

        return R.layout.activity_masterdetail;
    }

    @Override
    protected Fragment onCreateFragment() {
        _crimeId = (UUID) getIntent().getSerializableExtra(CRIME_ID);
        return CrimeFragment.newInstance(_crimeId);
    }

    protected static final String CRIME_ID = "crimePagerActivity.crimeId";

    public static Intent newIntent(Context activity, UUID id) {
        Intent intent = new Intent(activity, CrimePagerActivity.class);
        intent.putExtra(CRIME_ID, id);
        return intent;
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
//        TODO I will see what I can do here.
    }

    @Override
    public void onCrimeDelete(Crime crime) {

    }
}