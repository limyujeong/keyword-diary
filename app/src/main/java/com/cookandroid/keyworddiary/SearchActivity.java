package com.cookandroid.keyworddiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    RecyclerView mRvList;
    DiaryListAdapter mAdapter;
    ArrayList<DiaryModel> mLstDiary;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mRvList = findViewById(R.id.rv_search_list);
        mAdapter = new DiaryListAdapter();
        mLstDiary = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(this);

        mRvList.setVisibility(View.INVISIBLE);

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<DiaryModel> filterDiary = new ArrayList<>();
                for (int i = 0; i < mLstDiary.size(); i++) {
                    DiaryModel diaryModel = mLstDiary.get(i);
                    if (diaryModel.getSummaryHashtag().trim().contains(query.trim())) {
                        filterDiary.add(diaryModel);
                    }
                }
                DiaryListAdapter filterAdapter = new DiaryListAdapter(filterDiary);
                filterAdapter.setListInit(filterDiary);
                mRvList.setAdapter(filterAdapter);
                mRvList.setVisibility(View.VISIBLE);
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                ArrayList<DiaryModel> filterDiary = new ArrayList<>();
//                for (int i = 0; i < mLstDiary.size(); i++) {
//                    DiaryModel diaryModel = mLstDiary.get(i);
//                    if (diaryModel.getSummaryHashtag().trim().contains(newText.trim())) {
//                        filterDiary.add(diaryModel);
//                    }
//                }
//                DiaryListAdapter filterAdapter = new DiaryListAdapter(filterDiary);
//                filterAdapter.setListInit(filterDiary);
//                mRvList.setAdapter(filterAdapter);
                return false;
            }
        });

        mAdapter.setListInit(mLstDiary);
        mRvList.setAdapter(mAdapter);
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