package com.cookandroid.keyworddiary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * 데이터베이스 관리 유틸 클래스
 */
// SQLite = 안드로이드에서 지원하는 앱 내부 데이터베이스 시스템
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "KeywordDiary.db";

    // 생성자
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Database Create
        // (최초 1회만) 테이블 공간 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS DiaryInfo (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "month TEXT, day TEXT, summaryHashtag TEXT, " +
                "mood INTEGER NOT NULL, who TEXT, wwhere TEXT, food TEXT, hobby TEXT, addition TEXT, " +
                "userDate TEXT NOT NULL, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /**
     * 다이어리 작성 데이터를 DB에 저장 ( INSERT ) - create
     */
    public void setInsertDiaryList(String _month, String _day, String _summaryHashtag, int _mood, String _who, String _wwhere, String _food, String _hobby, String _addition, String _userDate, String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DiaryInfo (month, day, summaryHashtag, mood, who, wwhere, food, hobby, addition, userDate, writeDate) VALUES ('" + _month + "', '" + _day + "', '" + _summaryHashtag + "', '" + _mood + "', '" + _who + "', '" + _wwhere + "', '" + _food + "', '" + _hobby + "', '" + _addition + "', '" + _userDate + "', '" + _writeDate + "')");
    }

    /**
     * 다이어리 작성 데이터 조회 ( SELECT ) - read
     */
    public ArrayList<DiaryModel> getDiaryListFromDB() {
        ArrayList<DiaryModel> lstDiary = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DiaryInfo ORDER BY userDate DESC", null);   // DiaryInfo 테이블에 존재하는 모든 컬럼 * 을 가져옴
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String month = cursor.getString(cursor.getColumnIndexOrThrow("month"));
                String day = cursor.getString(cursor.getColumnIndexOrThrow("day"));
                String summaryHashtag = cursor.getString(cursor.getColumnIndexOrThrow("summaryHashtag"));
                int mood = cursor.getInt(cursor.getColumnIndexOrThrow("mood"));
                String who = cursor.getString(cursor.getColumnIndexOrThrow("who"));
                String wwhere = cursor.getString(cursor.getColumnIndexOrThrow("wwhere"));
                String food = cursor.getString(cursor.getColumnIndexOrThrow("food"));
                String hobby = cursor.getString(cursor.getColumnIndexOrThrow("hobby"));
                String addition = cursor.getString(cursor.getColumnIndexOrThrow("addition"));
                String userDate = cursor.getString(cursor.getColumnIndexOrThrow("userDate"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                // create data class
                DiaryModel diaryModel = new DiaryModel();
                diaryModel.setId(id);
                diaryModel.setMonth(month);
                diaryModel.setDay(day);
                diaryModel.setSummaryHashtag(summaryHashtag);
                diaryModel.setMood(mood);
                diaryModel.setWho(who);
                diaryModel.setWhere(wwhere);
                diaryModel.setFood(food);
                diaryModel.setHobby(hobby);
                diaryModel.setAddition(addition);
                diaryModel.setUserDate(userDate);
                diaryModel.setWriteDate(writeDate);

                lstDiary.add(diaryModel);
            }
        }
        cursor.close();

        return lstDiary;
    }

    /**
     * 기존 작성 데이터 수정 ( UPDATE ) - update
     */
    public void setUpdateDiaryList(String _month, String _day, String _summaryHashtag, int _mood, String _who, String _wwhere, String _food, String _hobby, String _addition, String _userDate, String _writeDate, String _beforeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE DiaryInfo SET month = '" + _month + "', day = '" + _day + "', summaryHashtag = '" + _summaryHashtag + "', mood = '" + _mood + "', who = '" + _who + "', wwhere = '" + _wwhere + "', food = '" + _food + "', hobby = '" + _hobby + "', addition = '" + _addition + "', userDate = '" + _userDate + "', writeDate = '" + _writeDate + "' WHERE writeDate = '" + _beforeDate + "'");
    }

    /**
     * 기존 작성 데이터 삭제 ( DELETE ) - delete
     */
    public void setDeleteDiaryList(String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM DiaryInfo WHERE writeDate = '" + _writeDate + "'");
    }
}
