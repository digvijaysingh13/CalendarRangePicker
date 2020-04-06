package com.digi.datepickerdemo.bpcalender;

import java.io.Serializable;

interface IDateSelected extends Serializable {
    void onDateSelected(BpDate date);
}
