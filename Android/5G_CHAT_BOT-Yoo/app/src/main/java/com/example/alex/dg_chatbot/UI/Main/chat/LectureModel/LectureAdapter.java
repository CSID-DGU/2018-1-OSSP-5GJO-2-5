package com.example.alex.dg_chatbot.UI.Main.chat.LectureModel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alex.dg_chatbot.R;

import java.util.ArrayList;

/**
 * Created by alex on 2018. 6. 21..
 */

public class LectureAdapter extends RecyclerView.Adapter{

    ArrayList<LectureModel> data = new ArrayList<>();

    public LectureAdapter(ArrayList<LectureModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecture_cardview, parent, false);
        return new LectureViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LectureModel lectureModel = data.get(position);
        LectureViewHolder lectureViewHolder = (LectureViewHolder) holder;

        lectureViewHolder.tvLectureMajor.setText(""+lectureModel.getLectureMajor());
        lectureViewHolder.tvLectureRoom.setText(""+lectureModel.getLectureRoom());
        lectureViewHolder.tvLectureGrade.setText(""+lectureModel.getLectureGrade());
        lectureViewHolder.tvLectureProf.setText(""+lectureModel.getLectureProf());
        lectureViewHolder.tvLectureName.setText(""+lectureModel.getLectureName());
        lectureViewHolder.tvLectureNum.setText(""+lectureModel.getLectureNum());
        lectureViewHolder.tvLectureTime.setText(""+lectureModel.getLectureTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
