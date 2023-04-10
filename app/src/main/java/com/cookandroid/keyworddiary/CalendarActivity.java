package com.cookandroid.keyworddiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    public CalendarView mCalendarView;
    public String mUserDate;
    public String mSelectedMonth, mSelectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mCalendarView = findViewById(R.id.calView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                // 달력에 선택된 년월일을 가져와서 캘린더 함수에 넣어서 사용자가 선택한 요일을 알아낸다.
                Calendar innerCal = Calendar.getInstance();
                innerCal.set(Calendar.YEAR, year);
                innerCal.set(Calendar.MONTH, month);
                innerCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                mUserDate = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN).format(innerCal.getTime());
                mSelectedMonth = new SimpleDateFormat("yyyy년 MM월", Locale.KOREAN).format(innerCal.getTime());
                mSelectedDay = new SimpleDateFormat("dd일", Locale.KOREAN).format(innerCal.getTime());

                // 날짜 선택 시 작성 화면으로 이동
                Intent intent = new Intent(CalendarActivity.this, WriteActivity.class);
                intent.putExtra("날짜", mUserDate);
                intent.putExtra("월", mSelectedMonth);
                intent.putExtra("일", mSelectedDay);
                startActivity(intent);
            }
        });
    }
}