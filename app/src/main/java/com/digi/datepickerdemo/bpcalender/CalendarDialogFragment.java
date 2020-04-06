package com.digi.datepickerdemo.bpcalender;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.digi.datepickerdemo.R;

import java.util.Calendar;

public class CalendarDialogFragment extends DialogFragment implements ViewPager.OnPageChangeListener {

    private ViewPager mPager;
    private TextView mMonthTv;
    private Button mLeftBtn, mRightBtn;
    private CalenderPagerAdapter mAdapter;
    private IDateSelected iMonthInit = (IDateSelected) date -> {
        mMonthTv.setText("" + date.month + "-" + date.year);
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(params);
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater.inflate(R.layout.dialog_fragment, container, false));
    }

    private View initView(View v) {
        mPager = v.findViewById(R.id.view_pager);
        mLeftBtn = v.findViewById(R.id.df_left_btn);
        mRightBtn = v.findViewById(R.id.df_right_btn);
        mMonthTv = v.findViewById(R.id.df_month_tv);
        mAdapter = new CalenderPagerAdapter(getChildFragmentManager(), this::refreshCalendar);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(this);
        setMonthText(new int[]{mAdapter.mCurrentCalender.get(Calendar.MONTH),
                mAdapter.mCurrentCalender.get(Calendar.YEAR)});
        return v;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        FragmentCalender cal = mAdapter.getCalenderAt(position);
        if (cal != null) {
            int[] md = cal.getMonthDate();
            setMonthText(md);
            if(cal.getmCalendarView() != null){
                cal.getmCalendarView().mAdapter.refreshCalender(mAdapter.mSelectedDates);
            }
        }
    }

    private void refreshCalendar(){
        onPageSelected(mPager.getCurrentItem());
    }


    private void setMonthText(int[] md) {
        mMonthTv.setText("" + md[0] + "-" + md[1]);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
