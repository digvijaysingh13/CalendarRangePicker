package com.digi.datepickerdemo.bpcalender;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digi.datepickerdemo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCalender extends Fragment {

    private BpSimpleCalender mCalendarView;

    public FragmentCalender() {
    }

    public static FragmentCalender newInstance(Bundle data){
        FragmentCalender fc = new FragmentCalender();
        fc.setArguments(data);
        return fc;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initView(inflater.inflate(R.layout.fragment_calender, container, false));
    }

    private View initView(View v){
        mCalendarView = v.findViewById(R.id.bp_calender_view);
        mCalendarView.setMonth(getArguments());
        return v;
    }

    int[] getMonthDate(){
        int[] monthDate = new int[]{0,0};
        if(mCalendarView != null){
            monthDate[0]= mCalendarView.mAdapter.month;
            monthDate[1] = mCalendarView.mAdapter.year;
        }
        return monthDate;
    }

    BpSimpleCalender getmCalendarView(){
        return  mCalendarView;
    }

}
