package com.digi.datepickerdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.digi.datepickerdemo.bpcalender.CalendarDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = findViewById(R.id.container_vp);
        findViewById(R.id.open_dialog).setOnClickListener(v->{
            DialogFragment df = new CalendarDialogFragment();
            df.show(getSupportFragmentManager(), "dialog");
        });
    }

}
