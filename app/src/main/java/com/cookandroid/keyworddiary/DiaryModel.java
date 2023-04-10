package com.cookandroid.keyworddiary;

import java.io.Serializable;

public class DiaryModel implements Serializable {
    int id;                 // 게시물 고유 ID 값
    String userDate;        // 사용자가 선택한 날짜
    String writeDate;       // 게시글을 작성한 날짜
    String month;           // 리스트에 보여지는 년,월 (2022년 5월)
    String day;             // 리스트에 보여지는 일 (8일)
    String summaryHashtag;  // 리스트에 요약된 줄글 해시태그
    int mood;               // 오늘의 기분 (0:laugh 1:happy 2:smile 3:meh 4:sad 5:angry)
    String who;             // 누구랑 만났는지
    String where;           // 어디를 갔는지
    String food;            // 무엇을 먹었는지
    String hobby;           // 무슨 활동을 했는지 (취미)
    String addition;        // 추가하고 싶은 키워드

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSummaryHashtag() {
        return summaryHashtag;
    }

    public void setSummaryHashtag(String summaryHashtag) {
        this.summaryHashtag = summaryHashtag;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }
}
