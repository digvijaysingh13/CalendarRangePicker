package com.digi.datepickerdemo.bpcalender;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

class Utils {

    static <T> List<T> getList(int size, @NonNull IGetItem<T> cb) {
        List<T> list = new ArrayList<>();
        for (int index = 0; index < size; index++) list.add(cb.getItem());
        return list;
    }

    static <T> boolean isValidRef(WeakReference<T> ref){
        return ref != null && ref.get() != null;
    }
}
