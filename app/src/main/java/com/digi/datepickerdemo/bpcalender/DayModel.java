package com.digi.datepickerdemo.bpcalender;

import androidx.annotation.NonNull;

class DayModel {

   private String date;
    EDateSelectionState state;

    DayModel(String date, EDateSelectionState state){
        this.date = date;
        this.state = state;
    }

    DayModel(String date){
        this(date, EDateSelectionState.UNSELECTED);
    }

    @NonNull
    String getDate(){
        return date != null ? date : "";
    }

    void setDate(String date){
        this.date = date;
    }
}
