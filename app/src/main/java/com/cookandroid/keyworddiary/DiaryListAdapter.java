package com.cookandroid.keyworddiary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder> {
    ArrayList<DiaryModel> mLstDiary;        // 다이어리 데이터들을 들고 있는 자료형 배열
    ArrayList<DiaryModel> mLstDiaryFilter;
    Context mContext;
    DatabaseHelper mDatabaseHelper;

    public DiaryListAdapter() {

    }

    public DiaryListAdapter(ArrayList<DiaryModel> lstDiary) {
        mLstDiary = lstDiary;
        mLstDiaryFilter = new ArrayList<>(lstDiary);
    }

    @NonNull
    @Override
    public DiaryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 아이템 뷰가 최초로 생성될 때 호출되는 곳
        mContext = parent.getContext();
        mDatabaseHelper = new DatabaseHelper(mContext);
        View holder = LayoutInflater.from(mContext).inflate(R.layout.list_item_summary, parent, false);     // inflate : 리스트 뷰 화면에서 하나의 아이템을 붙여 나가는 행위 (뷰 안에 뷰를 붙임), 각기 다른 화면들을 한 화면에 동적으로 띄우고 싶은 경우
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryListAdapter.ViewHolder holder, int position) {
        // 생성된 아이템 뷰가 실제 연동되는 곳
        // 기분 경우의 수 작성
        int mood = mLstDiary.get(position).getMood();
        switch (mood) {
            case 0:
                // laugh
                holder.iv_mood_tag.setImageResource(R.drawable.img_laugh_32);
                break;
            case 1:
                // happy
                holder.iv_mood_tag.setImageResource(R.drawable.img_happy_32);
                break;
            case 2:
                // smile
                holder.iv_mood_tag.setImageResource(R.drawable.img_smile_32);
                break;
            case 3:
                // meh
                holder.iv_mood_tag.setImageResource(R.drawable.img_meh_32);
                break;
            case 4:
                // sad
                holder.iv_mood_tag.setImageResource(R.drawable.img_sad_32);
                break;
            case 5:
                // angry
                holder.iv_mood_tag.setImageResource(R.drawable.img_angry_32);
                break;
        }

        // 일자, 해시태그 요약
        String month = mLstDiary.get(position).getMonth();
        String day = mLstDiary.get(position).getDay();
        String summaryHashtag = mLstDiary.get(position).getWho() + " " + mLstDiary.get(position).getWhere() + " " + mLstDiary.get(position).getFood() + " " + mLstDiary.get(position).getHobby() + " " + mLstDiary.get(position).getAddition();

        holder.tv_year_and_month.setText(month);
        holder.tv_day.setText(day);
        holder.tv_summary_hashtag.setText(summaryHashtag);
    }

    @Override
    public int getItemCount() {
        // 아이템 뷰의 총 갯수를 관리하는 곳
        return mLstDiary.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // 하나의 아이템 뷰
        TextView tv_day, tv_summary_hashtag, tv_year_and_month;
        ImageView iv_mood_tag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Activity 가 아니기 때문에 (메인처럼 xml 연동 X) 뷰에 대한 정보를 가져올 수 없음. 그래서 'itemView.'을 붙여줘야 함. 여기서 itemView 는 holder 를 가리킴. holder 는 list_item_summary 라는 xml 을 가리킴
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_summary_hashtag = itemView.findViewById(R.id.tv_summary_hashtag);
            tv_year_and_month = itemView.findViewById(R.id.tv_year_and_month);
            iv_mood_tag = itemView.findViewById(R.id.iv_mood_tag);

            // 일반 클릭 (상세보기)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentPosition = getAdapterPosition();
                    DiaryModel diaryModel = mLstDiary.get(currentPosition);

                    Intent detailIntent = new Intent(mContext, WriteActivity.class);
                    detailIntent.putExtra("diaryModel", diaryModel);     // 다이어리 데이터 넘기기
                    detailIntent.putExtra("mode", "detail");       // 상세보기 모드로 설정
                    mContext.startActivity(detailIntent);
                }
            });

            // 선택지 옵션 팝업 (수정, 삭제)
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int currentPosition = getAdapterPosition();
                    DiaryModel diaryModel = mLstDiary.get(currentPosition);

                    // 버튼 선택지 배열
                    String[] strChoiceArray = {"수정하기", "삭제하기"};
                    // 팝업 화면 표시
                    new AlertDialog.Builder(mContext)
                            .setItems(strChoiceArray, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int position) {
                                    if (position == 0) {
                                        // 수정하기
                                        Intent writeIntent = new Intent(mContext, WriteActivity.class);
                                        writeIntent.putExtra("diaryModel", diaryModel);     // 다이어리 데이터 넘기기
                                        writeIntent.putExtra("mode", "modify");       // 수정하기 모드로 설정
                                        mContext.startActivity(writeIntent);
                                    } else {
                                        // 삭제하기
                                        // delete database data
                                        mDatabaseHelper.setDeleteDiaryList(diaryModel.getWriteDate());
                                        // delete ui
                                        mLstDiary.remove(currentPosition);  // 배열에서 제거된거지 리스트뷰에서 제거된게 아님
                                        notifyItemRemoved(currentPosition); // 리스트뷰에서 제거함으로써 ui 에서도 제거됨
                                    }
                                }
                            }).show();
                    return false;
                }
            });
        }
    }

    public void setListInit(ArrayList<DiaryModel> lstDiary) {
        // 데이터 리스트 update
        mLstDiary = lstDiary;
        notifyDataSetChanged(); // 리스트 뷰 새로고침
    }
}
