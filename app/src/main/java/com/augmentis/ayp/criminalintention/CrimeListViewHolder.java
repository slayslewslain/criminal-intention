package com.augmentis.ayp.criminalintention;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.augmentis.ayp.criminalintention.model.Crime;
import com.augmentis.ayp.criminalintention.model.CrimeDateFormat;
import com.augmentis.ayp.criminalintention.model.CrimeLab;
import com.augmentis.ayp.criminalintention.model.PictureUtils;

import java.io.File;
import java.util.UUID;

/**
 * Created by Chayanit on 28-Jul-16.
 */
public class CrimeListViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "CrimeListViewHolder";
    public TextView _titleTextView;
    public TextView _dateTextView;
    public CheckBox _solvedCheckBox;
    public ImageView _imageCrime;

    private File photoFile;
    private Crime _crime;

    UUID _crimeId;
    int _position;

    Fragment _f;

    private CrimeListFragment.Callbacks callbacks;

    public CrimeListViewHolder(Fragment f, View itemView) {
        super(itemView);

        _f = f;

        _titleTextView = (TextView)
                itemView.findViewById(R.id.list_item_crime_title_text_view);
        _solvedCheckBox = (CheckBox)
                itemView.findViewById(R.id.list_item_crime_solved_check_box);

        _dateTextView = (TextView)
                itemView.findViewById(R.id.list_item_crime_date_text_view);

        _imageCrime = (ImageView)
                itemView.findViewById(R.id.list_item_crime_image_view);

        _solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _crime.setSolved(b);
                CrimeLab.getInstance(_f.getActivity()).updateCrime(_crime);
                ((CrimeListActivity) _f.getActivity()).onCrimeSelected(_crime);
            }
        });

        itemView.setOnClickListener(this);
    }

    public void bind(Crime crime) {
        _crimeId = crime.getId();
        _crime = crime;

        photoFile = CrimeLab.getInstance(_f.getActivity()).getPhotoFile(crime);

        _titleTextView.setText(crime.getTitle());
        _dateTextView.setText(CrimeDateFormat.toFullDate(_f.getActivity(), crime.getCrimeDate()));
        _solvedCheckBox.setChecked(crime.isSolved());
        _crime.setSolved(crime.isSolved());


        if (photoFile == null || !photoFile.exists()) {
            _imageCrime.setImageDrawable(null);
        } else {
            final Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(),
                    _f.getActivity());

            _imageCrime.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "send position : " + _position);
        callbacks = (CrimeListFragment.Callbacks) _f.getActivity();
        callbacks.onCrimeSelected(_crime);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CrimeLab crimeLab = CrimeLab.getInstance(_f.getActivity());
        Crime crime = crimeLab.getCrimeById(_crimeId);

        crime.setSolved(isChecked);
        crimeLab.updateCrime(crime);
    }
}