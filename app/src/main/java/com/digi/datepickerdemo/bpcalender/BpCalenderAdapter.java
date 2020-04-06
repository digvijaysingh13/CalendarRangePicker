package com.digi.datepickerdemo.bpcalender;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.digi.datepickerdemo.CalenderApp;
import com.digi.datepickerdemo.R;

import java.lang.ref.WeakReference;
import java.util.List;

class BpCalenderAdapter extends RecyclerView.Adapter<BpCalenderTitleHolder> {

    private final List<DayModel> dayModels;
    WeakReference<IDateSelected> iDateSelectWeakRef;
    int month, year;

    BpCalenderAdapter(@NonNull List<DayModel> dayModels) {
        this.dayModels = dayModels;
    }

    @NonNull
    @Override
    public BpCalenderTitleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new BpCalenderTitleHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calender_title, parent, false)
            );
        else
            return new BpCalenderHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calender_date, parent, false), this::onItemClicked
            );
    }

    private void onItemClicked(int index) {
        if (Utils.isValidRef(iDateSelectWeakRef) && !dayModels.get(index).getDate().isEmpty()) {
            iDateSelectWeakRef.get().onDateSelected(new BpDate(Integer.parseInt(dayModels.get(index).getDate()), month, year));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BpCalenderTitleHolder holder, int position) {
        Log.e("Data","-----"+position+"====="+ dayModels.get(position).getDate());
        holder.bindView(dayModels.get(position));
    }

    @Override
    public int getItemCount() {
        return dayModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 7) return 0;
        else return 1;
    }

    public void refreshCalender(BpDate[] dateSelection) {
        int index = BpSimpleCalender.DATE_START_INDEX;
        while (index < dayModels.size()) {
            DayModel dm = dayModels.get(index);
            if (!dm.getDate().isEmpty()) {
                if (dateSelection[0] == null) {
                   dm.state = EDateSelectionState.UNSELECTED;
                } else if (dateSelection[1] == null) {
                    int result = compareDate(dm, dateSelection[0]);
                    if (result == 0) dm.state = EDateSelectionState.SELECTED;
                    else dm.state = EDateSelectionState.UNSELECTED;
                } else {
                    int res1 = compareDate(dm, dateSelection[0]);
                    int res2 = compareDate(dm, dateSelection[1]);
                    if (res1 < 0 || res2 > 0) {
                        dm.state = EDateSelectionState.UNSELECTED;
                    } else if (res1 == 0) {
                        dm.state = EDateSelectionState.START_SELECTED;
                    } else if (res2 == 0) {
                        dm.state = EDateSelectionState.END_SELECTED;
                    } else {
                        dm.state = EDateSelectionState.INRANGE;
                    }
                }
            }
            index++;
        }
        notifyDataSetChanged();
    }

    //    returns 1 if day is leads, -1 if day is lags, 0 for equal
    private int compareDate(DayModel day, BpDate date) {
        if (year > date.year) return 1;
        else if (year < date.year) return -1;
        else {
            if (month > date.month) return 1;
            else if (month < date.month) return -1;
            else {
                return Integer.compare(Integer.parseInt(day.getDate()), date.date);
            }
        }
    }
}

class BpCalenderTitleHolder extends RecyclerView.ViewHolder {

    TextView mDateTv;

    BpCalenderTitleHolder(View view) {
        super(view);
        mDateTv = view.findViewById(R.id.icd_date_tv);
    }

    protected void bindView(DayModel dayModel) {
        mDateTv.setText(dayModel.getDate());
    }
}

class BpCalenderHolder extends BpCalenderTitleHolder {

    private View mLeftView, mRightView;

    private static int whiteColor = ContextCompat.getColor(CalenderApp.appContext, R.color.white);
    private static int grayColor = ContextCompat.getColor(CalenderApp.appContext, R.color.gray);

    BpCalenderHolder(View view, IListItem iListItem) {
        super(view);
        mDateTv = view.findViewById(R.id.icd_date_tv);
        mLeftView = view.findViewById(R.id.icd_left_view);
        mRightView = view.findViewById(R.id.icd_right_view);
        view.findViewById(R.id.icd_parent_ll).setOnClickListener(v -> {
            if (getAdapterPosition() > -1) {
                iListItem.atIndex(getAdapterPosition());
            }
        });
    }

    @Override
    protected void bindView(DayModel dayModel) {
        mDateTv.setText(dayModel.getDate());
        if (dayModel.getDate().isEmpty()){
            mDateTv.setBackground(null);
            mLeftView.setBackgroundColor(whiteColor);
            mRightView.setBackgroundColor(whiteColor);
            return;
        }
        switch (dayModel.state) {
            case INRANGE:
                mDateTv.setBackground(null);
                mLeftView.setBackgroundColor(grayColor);
                mRightView.setBackgroundColor(grayColor);
                break;
            case UNSELECTED:
                mDateTv.setBackground(null);
                mLeftView.setBackgroundColor(whiteColor);
                mRightView.setBackgroundColor(whiteColor);
                break;
            case START_SELECTED:
                mDateTv.setBackground(ContextCompat.getDrawable(mDateTv.getContext(), R.drawable.date_circle_bg));
                mLeftView.setBackgroundColor(whiteColor);
                mRightView.setBackgroundColor(grayColor);
                break;
            case END_SELECTED:
                mDateTv.setBackground(ContextCompat.getDrawable(mDateTv.getContext(), R.drawable.date_circle_bg));
                mLeftView.setBackgroundColor(grayColor);
                mRightView.setBackgroundColor(whiteColor);
                break;
            case SELECTED:
                mDateTv.setBackground(ContextCompat.getDrawable(mDateTv.getContext(), R.drawable.date_circle_bg));
                mLeftView.setBackgroundColor(whiteColor);
                mRightView.setBackgroundColor(whiteColor);
                break;
        }
    }
}
