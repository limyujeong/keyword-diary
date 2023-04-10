package com.cookandroid.keyworddiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.radiobutton.MaterialRadioButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 작성하기 화면 or 상세보기 화면
 */
public class WriteActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvYMD;                                // 화면 상단의 년월일
    private EditText mEtWho, mEtWhere, mEtFood, mEtHobby, mEtAddition;
    private RadioGroup mRgMood;
    private TextView mTvMonth, mTvDay;

    private String mDetailMode = "";                        // intent 로 받아낸 게시글 모드
    private String mBeforeDate = "";                        // intent 로 받아낸 기존 게시글 작성 날짜 ( update 시 사용 )
    private String mSelectedUserDate = "";                  // 선택된 날짜
    private int mSelectedMood = -1;                         // 선택된 기분 값 (1~6)
    private String mSelectedMonth = "";
    private String mSelectedDay = "";

    private DatabaseHelper mDatabaseHelper;                 // Database Util 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        // Database 객체 생성
        mDatabaseHelper = new DatabaseHelper(this);

        mTvYMD = findViewById(R.id.tv_YMD);                 // 화면 상단 년월일
        mRgMood = findViewById(R.id.rg_mood);               // 오늘의 기분
        mEtWho = findViewById(R.id.et_who);                 // 만난 사람
        mEtWhere = findViewById(R.id.et_where);             // 방문한 장소
        mEtFood = findViewById(R.id.et_food);               // 먹은 음식
        mEtHobby = findViewById(R.id.et_hobby);             // 취미 활동
        mEtAddition = findViewById(R.id.et_addition);       // 추가하고 싶은 키워드
        mTvMonth = findViewById(R.id.tv_month);
        mTvDay = findViewById(R.id.tv_day_of_month);

        ImageView iv_check = findViewById(R.id.iv_check);   // 작성완료 버튼

        // this 의 의미는 implements 한 OnClickListener 에 붙이라는 의미
        iv_check.setOnClickListener(this);      // 클릭 기능 부여

        // 기본으로 설정될 날짜의 값을 지정 (디바이스 현재 시간 기준)
        mSelectedUserDate = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN).format(new Date());
        mTvYMD.setText(mSelectedUserDate);
        mSelectedMonth = new SimpleDateFormat("yyyy년 MM월", Locale.KOREAN).format(new Date());
        mTvMonth.setText(mSelectedMonth);
        mSelectedDay = new SimpleDateFormat("dd일", Locale.KOREAN).format(new Date());
        mTvDay.setText(mSelectedDay);

        // 이전 액티비티로부터 값을 전달 받음
        Intent intent = getIntent();
        if (intent.getExtras() != null) {                   // intent putExtra 했던 데이터가 존재한다면 내부를 수행
            if (intent.hasExtra("날짜")) {
                mSelectedUserDate = intent.getStringExtra("날짜");
                mTvYMD.setText(mSelectedUserDate);
            } if (intent.hasExtra("월")) {
                mSelectedMonth = intent.getStringExtra("월");
                mTvMonth.setText(mSelectedMonth);
            } if (intent.hasExtra("일")) {
                mSelectedDay = intent.getStringExtra("일");
                mTvDay.setText(mSelectedDay);
            } if (intent.hasExtra("diaryModel") || intent.hasExtra("mode")) {   // 다이어리 모델이 존재한다면
                DiaryModel diaryModel = (DiaryModel) intent.getSerializableExtra("diaryModel");
                mDetailMode = intent.getStringExtra("mode");
                mBeforeDate = diaryModel.getWriteDate();    // 게시글 database update 쿼리문 처리를 위해서 여기서 받아둠

                // 넘겨받은 값을 활용해서 각 필드들에 설정
                mSelectedUserDate = diaryModel.getUserDate();
                mTvYMD.setText(diaryModel.getUserDate());
                mEtWho.setText(diaryModel.getWho());
                mEtWhere.setText(diaryModel.getWhere());
                mEtFood.setText(diaryModel.getFood());
                mEtHobby.setText(diaryModel.getHobby());
                mEtAddition.setText(diaryModel.getAddition());
                int mood = diaryModel.getMood();
                ((MaterialRadioButton) mRgMood.getChildAt(mood)).setChecked(true);
                mSelectedMonth = diaryModel.getMonth();
                mTvMonth.setText(diaryModel.getMonth());
                mSelectedDay = diaryModel.getDay();
                mTvDay.setText(diaryModel.getDay());

                if (mDetailMode.equals("detail")) {
                    // 읽기 전용 화면으로 표시
                    mEtWho.setBackground(null);
                    mEtWho.setEnabled(false);
                    mEtWhere.setBackground(null);
                    mEtWhere.setEnabled(false);
                    mEtFood.setBackground(null);
                    mEtFood.setEnabled(false);
                    mEtHobby.setBackground(null);
                    mEtHobby.setEnabled(false);
                    mEtAddition.setBackground(null);
                    mEtAddition.setEnabled(false);
                    for (int i = 0; i < mRgMood.getChildCount(); i++) {
                        mRgMood.getChildAt(i).setEnabled(false);
                    }
                    iv_check.setVisibility(View.INVISIBLE);
                }
            }
        }
        mTvMonth.setVisibility(View.GONE);
        mTvDay.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_check:
                // 작성 완료 버튼
                mSelectedMood = mRgMood.indexOfChild(findViewById(mRgMood.getCheckedRadioButtonId()));

                // 입력필드 작성란이 비어있는지 체크
                if (mEtWho.getText().length() == 0 && mEtWhere.getText().length() == 0 && mEtFood.getText().length() == 0
                        && mEtHobby.getText().length() == 0 && mEtAddition.getText().length() == 0) {
                    Toast toastEdit = Toast.makeText(this, "항목을 입력해주세요.", Toast.LENGTH_SHORT);
                    toastEdit.setGravity(Gravity.BOTTOM, 0, 200);
                    toastEdit.show();
                    return; // 아래의 로직을 수행하지 않고 되돌려보냄
                }

                // 기분 버튼을 선택했는지 체크
                if (mSelectedMood == -1) {
                    Toast toastMood = Toast.makeText(this, "기분을 선택해주세요.", Toast.LENGTH_SHORT);
                    toastMood.setGravity(Gravity.BOTTOM, 0, 200);
                    toastMood.show();
                    return; // 아래의 로직을 수행하지 않고 되돌려보냄
                }

                /////////////////// 이곳까지 도착했다면 에러상황이 없으므로 데이터 저장 ///////////////////

                String who = mEtWho.getText().toString();                                                                  // 만난 사람 입력 값
                String where = mEtWhere.getText().toString();                                                              // 방문한 장소 입력 값
                String food = mEtFood.getText().toString();                                                                // 먹은 음식 입력 값
                String hobby = mEtHobby.getText().toString();                                                              // 취미 활동 입력 값
                String addition = mEtAddition.getText().toString();
                String summaryHashtag = who + where + food + hobby + addition;
                String userDate = mSelectedUserDate;                                                                       // 선택된 날짜
                String writeDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREAN).format(new Date());   // 작성완료 누른 시점의 일시, 데이터베이스 저장용 변수
                String month = mSelectedMonth;
                String day = mSelectedDay;

                // 별도 날짜 설정을 하지 않은 채로 작성완료를 누를 경우
                if (userDate.equals("")) {
                    userDate = mTvYMD.getText().toString();
                } else if (month.equals("")) {
                    month = mTvMonth.getText().toString();
                } else if (day.equals("")) {
                    day = mTvDay.getText().toString();
                }

                // 액티비티의 현재 모드에 따라서 데이터베이스에 저장 또는 업데이트
                if (mDetailMode.equals("modify")) {
                    // 게시글 수정 모드
                    mDatabaseHelper.setUpdateDiaryList(month, day, summaryHashtag, mSelectedMood, who, where, food, hobby, addition, userDate, writeDate, mBeforeDate);
                } else {
                    // 게시글 작성 모드
                    mDatabaseHelper.setInsertDiaryList(month, day, summaryHashtag, mSelectedMood, who, where, food, hobby, addition, userDate, writeDate);
                }
                // 홈 화면인 캘린더 화면으로 전환
                Intent mainIntent = new Intent(WriteActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;
        }
    }
}