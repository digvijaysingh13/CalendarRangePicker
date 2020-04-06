package com.digi.datepickerdemo.bpcalender;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digi.datepickerdemo.R;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BpSimpleCalender extends FrameLayout {

    public static final int DATE_START_INDEX = 7;
    private static final int OPTIMIZED_VIEW_SIZE = 44;

    private List<DayModel> mDateList = Utils.getList(OPTIMIZED_VIEW_SIZE, () -> new DayModel(""));
    public final BpCalenderAdapter mAdapter = new BpCalenderAdapter(mDateList);

    public BpSimpleCalender(@NonNull Context context) {
        super(context);
        initView(null);
    }

    public BpSimpleCalender(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public BpSimpleCalender(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BpSimpleCalender(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        View vw = LayoutInflater.from(getContext()).inflate(R.layout.calender_recycler_view, this, false);
        addView(vw);
        RecyclerView mCalRv = vw.findViewById(R.id.calender_rv);
        mCalRv.setLayoutManager(new GridLayoutManager(getContext(), 7));
        mCalRv.setAdapter(mAdapter);
        setWeekName(new String[]{"Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat"});
    }

    void setWeekName(String[] arr) {
        for (int index = 0; index < 7; index++) {
            mDateList.get(index).setDate(arr[index]);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setMonth(final int firstDayOfWeek, final int maxDaysInMonth, int month, int year) {
        mAdapter.month = month;
        mAdapter.year = year;
        int firstDay = firstDayOfWeek - 1;// -1 subtracted because of indexing 0..6
        int index = DATE_START_INDEX + firstDay;
        int date = 1;
        while (date <= maxDaysInMonth) {
            mDateList.get(index).setDate(Integer.toString(date));
            index++;
            date++;
        }
    }

    public void setMonth(Bundle bundle) {
        if (bundle != null) {
            int year = bundle.getInt("year", 0);
            int maxDate = bundle.getInt("last_day", 0);
            int month = bundle.getInt("month", 12); // months 0..11
            int firstDayOfWeek = bundle.getInt("first_day_of_week", 7);
            Serializable ser = bundle.getSerializable("cb");
            if(ser instanceof IDateSelected){
                mAdapter.iDateSelectWeakRef = new WeakReference<>((IDateSelected)ser);
            }
            if (year == 0 || maxDate == 0 || month >= 12 || firstDayOfWeek > 7) {
                setMonth();
            } else {
                setMonth(firstDayOfWeek, maxDate, month, year); // -1 subtracted because of indexing 0..6
            }
        } else {
            setMonth();
        }
    }

    private void setMonth() {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // index from 0..6
        setMonth(firstDayOfWeek, maxDate, month, year);
    }
}
