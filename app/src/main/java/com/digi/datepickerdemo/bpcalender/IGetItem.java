package com.digi.datepickerdemo.bpcalender;

import androidx.annotation.NonNull;

interface IGetItem<T> {
    @NonNull
    T getItem();
}
