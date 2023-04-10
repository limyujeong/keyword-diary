package com.cookandroid.keyworddiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {
    RecyclerView mRvList;                   // 리사이클러뷰(리스트뷰)
    DiaryListAdapter mAdapter;              // 리사이클러뷰와 연동할 어댑터
    ArrayList<DiaryModel> mLstDiary;        // 리스트에 표현할 다이어리 데이터들(배열)
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Database 객체 초기화
        mDatabaseHelper = new DatabaseHelper(this);

        mRvList = findViewById(R.id.rv_list);
        mAdapter = new DiaryListAdapter();
        mLstDiary = new ArrayList<>();

//        // 다이어리 샘플 아이템 6개 생성
//        DiaryModel item = new DiaryModel();
//        item.setId(0);
//        item.setUserDate("2022년 5월 22일");
//        item.setWriteDate("2022년 5월 22일");
//        item.setMonth("2022년 5월");
//        item.setDay("22일");
//        item.setSummaryHashtag("#해시태그요약샘플");
//        item.setMood(0);
//        item.setWho("#이수미");
//        item.setWhere("#이태원");
//        item.setFood("#난포한남");
//        item.setHobby("#필라테스");
//        mLstDiary.add(item);
//
//        DiaryModel item2 = new DiaryModel();
//        item2.setId(1);
//        item2.setUserDate("2022년 5월 8일");
//        item2.setWriteDate("2022년 5월 8일");
//        item2.setMonth("2022년 5월");
//        item2.setDay("8일");
//        item2.setSummaryHashtag("#해시태그요약샘플");
//        item2.setMood(2);
//        item2.setWho("#김예진");
//        item2.setWhere("#성수");
//        item2.setFood("#제스티살룬");
//        item2.setHobby("#산책");
//        mLstDiary.add(item2);
//
//        DiaryModel item3 = new DiaryModel();
//        item3.setId(2);
//        item3.setUserDate("2022년 6월 1일");
//        item3.setWriteDate("2022년 6월 1일");
//        item3.setMonth("2022년 6월");
//        item3.setDay("1일");
//        item3.setSummaryHashtag("#해시태그요약샘플");
//        item3.setMood(5);
//        item3.setWho("");
//        item3.setWhere("#스터디카페");
//        item3.setFood("#컵라면");
//        item3.setHobby("#공부");
//        mLstDiary.add(item3);
//
//        DiaryModel item4 = new DiaryModel();
//        item4.setId(3);
//        item4.setUserDate("2022년 3월 12일");
//        item4.setWriteDate("2022년 3월 12일");
//        item4.setMonth("2022년 3월");
//        item4.setDay("12일");
//        item4.setSummaryHashtag("#해시태그요약샘플");
//        item4.setMood(1);
//        item4.setWho("#정미연 #전서영");
//        item4.setWhere("#서현");
//        item4.setFood("#콩불");
//        item4.setHobby("#보드게임");
//        mLstDiary.add(item4);
//
//        DiaryModel item5 = new DiaryModel();
//        item5.setId(4);
//        item5.setUserDate("2022년 3월 2일");
//        item5.setWriteDate("2022년 3월 2일");
//        item5.setMonth("2022년 3월");
//        item5.setDay("2일");
//        item5.setSummaryHashtag("#해시태그요약샘플");
//        item5.setMood(1);
//        item5.setWho("#이지연 #최은선");
//        item5.setWhere("#학교 #블룸즈버리");
//        item5.setFood("#학식 #딸기라떼");
//        item5.setHobby("#등산");
//        mLstDiary.add(item5);
//
//        DiaryModel item6 = new DiaryModel();
//        item6.setId(5);
//        item6.setUserDate("2022년 5월 30일");
//        item6.setWriteDate("2022년 5월 30일");
//        item6.setMonth("2022년 5월");
//        item6.setDay("30일");
//        item6.setSummaryHashtag("#해시태그요약샘플");
//        item6.setMood(4);
//        item6.setWho("#김지혜 #홍가윤");
//        item6.setWhere("#여의도한강공원");
//        item6.setFood("#피자 #치킨 #햄버거");
//        item6.setHobby("#산책");
//        mLstDiary.add(item6);

        mAdapter.setListInit(mLstDiary);
        mRvList.setAdapter(mAdapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.btn_search);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 검색 버튼을 누를 때 호출되는 곳
                Intent searchIntent = new Intent(SummaryActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 액티비티의 재개 (재시작)
        // get load list
        setLoadRecentList();
    }

    private void setLoadRecentList() {
        // 최근 데이터베이스 정보를 가져와서 리사이클러뷰 갱신
        // 이전에 배열 리스트에 저장된 데이터가 있으면 비움
        if (!mLstDiary.isEmpty()) {
            mLstDiary.clear();
        }
        mLstDiary = mDatabaseHelper.getDiaryListFromDB();   // 데이터베이스로부터 저장된 DB를 확인하여 가져옴
        mAdapter.setListInit(mLstDiary);
    }
}