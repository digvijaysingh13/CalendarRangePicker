package com.digi.datepickerdemo.bpcalender;

import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Calendar;
import java.util.Locale;

public class CalenderPagerAdapter extends FragmentPagerAdapter {
    final BpDate[] mSelectedDates = new BpDate[2];
    final Calendar mCurrentCalender = Calendar.getInstance(Locale.ENGLISH);
    private Calendar mDateProvider = Calendar.getInstance(Locale.ENGLISH);
    private SparseArray<FragmentCalender> mFragRefMap = new SparseArray<>();
    private  Runnable iRefreshCal;

    private IDateSelected iDateSelected = (IDateSelected) date->{
        if(mSelectedDates[0] == null){
            mSelectedDates[0] = date;
            mSelectedDates[1] = null;
        }else if(mSelectedDates[1] == null){
            mSelectedDates[1] = date;
        }else{
            mSelectedDates[0] = date;
            mSelectedDates[1] = null;
        }
        iRefreshCal.run();
    };

    public CalenderPagerAdapter(@NonNull FragmentManager fm, final Runnable refreshCalCb) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        iRefreshCal = refreshCalCb;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        mDateProvider.setTimeInMillis(mCurrentCalender.getTimeInMillis());
        mDateProvider.add(Calendar.MONTH, position);
        int year = mDateProvider.get(Calendar.YEAR);
        int month = mDateProvider.get(Calendar.MONTH);
        int maxDate = mDateProvider.getActualMaximum(Calendar.DAY_OF_MONTH);
        mDateProvider.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = mDateProvider.get(Calendar.DAY_OF_WEEK);
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("last_day", maxDate);
        bundle.putInt("first_day_of_week", firstDayOfWeek);
        bundle.putSerializable("cb", iDateSelected);
        FragmentCalender fc = FragmentCalender.newInstance(bundle);
        mFragRefMap.put(position, fc);
        return fc;
    }

    @Override
    public int getCount() {
        return 24;
    }

    @Nullable
    public FragmentCalender getCalenderAt(int index) {
        return mFragRefMap.get(index, null);
    }
}
