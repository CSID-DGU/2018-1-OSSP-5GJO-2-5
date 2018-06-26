package com.example.alex.dg_chatbot.UI.Main.chat.LectureModel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.alex.dg_chatbot.R;

import org.w3c.dom.Text;

/**
 * Created by alex on 2018. 6. 21..
 */

public class LectureViewHolder extends RecyclerView.ViewHolder {

    TextView tvLectureName, tvLectureProf, tvLectureNum, tvLectureTime,
            tvLectureGrade, tvLectureRoom, tvLectureMajor;

    public LectureViewHolder(View itemView) {
        super(itemView);

        tvLectureName = itemView.findViewById(R.id.tvLectureName);
        tvLectureProf = itemView.findViewById(R.id.tvLectureProf);
        tvLectureNum = itemView.findViewById(R.id.tvLectureNum);
        tvLectureTime = itemView.findViewById(R.id.tvLectureTime);
        tvLectureGrade = itemView.findViewById(R.id.tvLectureGrade);
        tvLectureRoom = itemView.findViewById(R.id.tvLectureRoom);
        tvLectureMajor = itemView.findViewById(R.id.tvLectureMajor);
    }
}
