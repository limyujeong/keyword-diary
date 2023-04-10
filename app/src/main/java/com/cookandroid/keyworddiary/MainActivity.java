package com.cookandroid.keyworddiary;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;

import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends TabActivity implements TabHost.OnTabChangeListener {
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = getTabHost();
        tabHost.setOnTabChangedListener(this);

        Intent writeIntent = new Intent().setClass(this, WriteActivity.class);
        TabHost.TabSpec tabSpecWrite = tabHost.newTabSpec("write").setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.img_write_40, null)).setContent(writeIntent);
        tabHost.addTab(tabSpecWrite);

        Intent calendarIntent = new Intent().setClass(this, CalendarActivity.class);
        TabHost.TabSpec tabSpecCalendar = tabHost.newTabSpec("calendar").setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.img_calendar_45, null)).setContent(calendarIntent);
        tabHost.addTab(tabSpecCalendar);

        Intent summaryIntent = new Intent().setClass(this, SummaryActivity.class);
        TabHost.TabSpec tabSpecSummary = tabHost.newTabSpec("summary").setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.img_clipboard_40, null)).setContent(summaryIntent);
        tabHost.addTab(tabSpecSummary);

        tabHost.setCurrentTab(1);
    }

    @Override
    public void onTabChanged(String tabId) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#90DFDF"));
        }
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#6FC3C3"));
    }
}