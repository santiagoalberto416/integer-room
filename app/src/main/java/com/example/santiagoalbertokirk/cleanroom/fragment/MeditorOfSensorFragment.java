package com.example.santiagoalbertokirk.cleanroom.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.santiagoalbertokirk.cleanroom.R;
import com.github.anastr.speedviewlib.SpeedView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeditorOfSensorFragment extends Fragment {


    private SpeedView mMeterView;
    private TextView mTitle;
    private float mValueText = 0;
    private String mTitleText = "";

    public MeditorOfSensorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mValueText = getArguments().getFloat("value");
            mTitleText = getArguments().getString("mTitle");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_blank, container, false);
        mTitle = (TextView)view.findViewById(R.id.header);
        mMeterView = (SpeedView)view.findViewById(R.id.circleView);
        mMeterView.setUnit("");
        setValueToMeter(mValueText);
        setTitle(mTitleText);
        return view;

    }

    public static Fragment newInstance(ModelViewSensor sensor)
    {
        MeditorOfSensorFragment myFragment = new MeditorOfSensorFragment();
        Bundle args = new Bundle();
        args.putFloat("value", sensor.getValue());
        args.putString("mTitle", sensor.getTitle());
        myFragment.setArguments(args);
        return myFragment;
    }

    public void setTitle(String text){
        mTitle.setText(text);
    }

    public void setValueToMeter(float value){
        mMeterView.speedTo(value);
    }

}
