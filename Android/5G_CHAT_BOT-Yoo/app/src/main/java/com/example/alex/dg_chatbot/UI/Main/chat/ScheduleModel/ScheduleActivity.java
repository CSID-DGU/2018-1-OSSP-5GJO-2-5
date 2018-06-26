package com.example.alex.dg_chatbot.UI.Main.chat.ScheduleModel;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alex.dg_chatbot.R;
import com.example.alex.dg_chatbot.UI.Main.chat.NoticeModel.NoticeModel;
import com.example.alex.dg_chatbot.Util.U;

import org.apache.http.cookie.SM;

import java.util.ArrayList;


public class ScheduleActivity extends AppCompatActivity{

    private RecyclerView scheRecyclerview;
    private LinearLayout llBack;
    private LinearLayoutManager linearLayoutManager;

    private ArrayList<ScheduleModel> data = new ArrayList<>();
    private ScheduleAdapter scheduleAdapter;

    private String month = "";

    @Override
    protected void onStart() {
        super.onStart();
        addDataByMonth(month);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        
        llBack = findViewById(R.id.llBack);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        month = getIntent().getStringExtra("value");
        if(month.equals("\"6월\"")){
            data.add(new ScheduleModel(0, "2018-06-04", "2018-06-08", "2018-2학기 학석사 \n연계과정 신청"));
            data.add(new ScheduleModel(0, "2018-06-04", "2018-06-21", "2018년 가을 졸업 \n대상자 졸업논문 \n제출"));
            data.add(new ScheduleModel(0, "2018-06-05", "2018-06-08", "전과(전공변경) 신청"));
            data.add(new ScheduleModel(0, "2018-06-05", "2018-06-08", "복수(연계)전공 신청"));
            data.add(new ScheduleModel(0, "2018-06-05", "2018-06-08", "교직복수(연계)전공 \n신청"));
            data.add(new ScheduleModel(0, "2018-06-05", "2018-06-11", "대학원 영어대체강좌 \n접수"));
            data.add(new ScheduleModel(0, "2018-06-05", "2018-06-08", "졸업연기 신청"));
            data.add(new ScheduleModel(0, "2018-06-11", "2018-06-25", "1학기 성적처리(입력)"));
            data.add(new ScheduleModel(0, "2018-06-15", "2018-06-21", "1학기 기말시험"));
        }else if(month.equals("\"5월\"")){
            data.add(new ScheduleModel(0, "2018-05-08", "2018-05-08", "개교기념일"));
            data.add(new ScheduleModel(0, "2018-05-10", "2018-05-11", "경주캠퍼스 정규학기 \n학점교류 신청"));
            data.add(new ScheduleModel(0, "2018-05-16", "2018-05-16", "학기 2/3 기준일"));
            data.add(new ScheduleModel(0, "2018-05-22", "2018-05-22", "부처님 오신날"));
            data.add(new ScheduleModel(0, "2018-05-24", "2018-05-25", "여름 계절학기 수강신청"));
            data.add(new ScheduleModel(0, "2018-05-30", "2018-05-30", "학기 4/5 기준일"));
        }



        U.getInstance().resultLog("month : " + month);

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        scheRecyclerview = findViewById(R.id.scheRecyclerview);
        scheRecyclerview.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        scheRecyclerview.setLayoutManager(linearLayoutManager);

        scheduleAdapter = new ScheduleAdapter(data);
        scheRecyclerview.setAdapter(scheduleAdapter);



    }

    public void addDataByMonth(String month){

    }

    //***********************************************
    //              ADAPTER & VIEW HOLDER
    //***********************************************
    private class ScheduleViewHolder extends RecyclerView.ViewHolder{
        
        TextView preDate, postDate, content;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            preDate = itemView.findViewById(R.id.preDate);
            postDate = itemView.findViewById(R.id.postDate);
            content = itemView.findViewById(R.id.content);
        }
    }

    private class ScheduleAdapter extends RecyclerView.Adapter{

        ArrayList<ScheduleModel> data = new ArrayList<>();

        public ScheduleAdapter(ArrayList<ScheduleModel> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_list, parent, false);
            return new ScheduleViewHolder(item);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ScheduleModel scheduleModel = data.get(position);
            ScheduleViewHolder scheduleViewHolder = (ScheduleViewHolder) holder;
            
            scheduleViewHolder.preDate.setText(""+scheduleModel.getPreDate());
            scheduleViewHolder.postDate.setText(""+scheduleModel.getPostDate());
            scheduleViewHolder.content.setText(""+scheduleModel.getContent());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }



}
