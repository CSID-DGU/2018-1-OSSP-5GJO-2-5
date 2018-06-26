package com.example.alex.dg_chatbot.UI.Main.chat.NoticeModel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alex.dg_chatbot.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NoticeFromChatActivity extends AppCompatActivity {
    
    private LinearLayout llBack;
    private RecyclerView noticeRecyclerview;
    private LinearLayoutManager linearLayoutManager;

    private ArrayList<NoticeModel> data = new ArrayList<>();
    private NoticeAdapter noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_from_chat);

        data.add(new NoticeModel("","","","이상","2018-06-19", 789, "[전산원] 하계 토익특강안내", "전산원", "TOEIC 550+/755+ 특강안내..."));
//        data.add(new NoticeModel("","","","유근창","2018-00-00", 789, "title22", "타입22", "컨턴츠22"));
//        data.add(new NoticeModel("","","","유근창","2018-00-00", 789, "title33", "타입33", "컨턴츠33"));
//        data.add(new NoticeModel("","","","유근창","2018-00-00", 789, "title44", "타입44", "컨턴츠44"));
//        data.add(new NoticeModel("","","","유근창","2018-00-00", 789, "title55", "타입55", "컨턴츠55"));
        
        llBack = findViewById(R.id.llBack);
        
        noticeRecyclerview = findViewById(R.id.noticeRecyclerview);
        noticeRecyclerview.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noticeRecyclerview.setLayoutManager(linearLayoutManager);
        
        noticeAdapter = new NoticeAdapter(data);
        noticeRecyclerview.setAdapter(noticeAdapter);
        
        
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //***********************************************
    //              ADAPTER & VIEW HOLDER
    //***********************************************
    private class NoticeViewHolder extends RecyclerView.ViewHolder{

        TextView title, regDate, type, writer, content;
        public NoticeViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            regDate = itemView.findViewById(R.id.regDate);
            type = itemView.findViewById(R.id.type);
            writer = itemView.findViewById(R.id.writer);
            content = itemView.findViewById(R.id.content);
        }
    }

    private class NoticeAdapter extends RecyclerView.Adapter{

        ArrayList<NoticeModel> data = new ArrayList<>();

        public NoticeAdapter(ArrayList<NoticeModel> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_list, parent, false);
            return new NoticeViewHolder(item);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            NoticeModel noticeModel = data.get(position);
            NoticeViewHolder noticeViewHolder = (NoticeViewHolder) holder;

            noticeViewHolder.regDate.setText(""+noticeModel.getRegDate());
            noticeViewHolder.title.setText(""+noticeModel.getTitle());
            noticeViewHolder.type.setText(""+noticeModel.getType());
            noticeViewHolder.writer.setText(""+noticeModel.getWriter());
            noticeViewHolder.content.setText(""+noticeModel.getContent());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}
