package com.example.alex.dg_chatbot.UI.Main.chat.LectureModel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.alex.dg_chatbot.R;

import java.util.ArrayList;

public class LectureActivity extends AppCompatActivity {

    private RecyclerView rvLectureList;
    private LinearLayout llBack;
    private LinearLayoutManager linearLayoutManager;

    private ArrayList<LectureModel> data = new ArrayList<>();
    private LectureAdapter lectureAdapter;
    private String questionType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        llBack = findViewById(R.id.llBack);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        questionType = getIntent().getStringExtra("action");
        //input data
        if(questionType.equals("lecture_name")){

        }else if(questionType.equals("lecture_prof")){

        }


        rvLectureList = findViewById(R.id.rvLectureList);
        rvLectureList.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLectureList.setLayoutManager(linearLayoutManager);

        lectureAdapter = new LectureAdapter(data);
        rvLectureList.setAdapter(lectureAdapter);


    }
}
