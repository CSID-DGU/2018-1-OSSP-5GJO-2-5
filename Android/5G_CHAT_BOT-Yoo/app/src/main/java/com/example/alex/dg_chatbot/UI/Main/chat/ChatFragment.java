package com.example.alex.dg_chatbot.UI.Main.chat;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.alex.dg_chatbot.R;

/**
 * Selection으로 구현해서 원하는 정보를 얻을 수 있도록 함.
 * 버튼을 누르면 ChatActivity로 이동
 */
public class ChatFragment extends Fragment{

    private Button btGoChat;
    private LinearLayout llNotice;
    private LinearLayout llSchedule;
    private LinearLayout llLecture;
    private LinearLayout llEtc;

    public ChatFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_chat, container, false);

        
        llNotice = view.findViewById(R.id.llNotice);
        llSchedule = view.findViewById(R.id.llSchedule);
        llLecture = view.findViewById(R.id.llLecture);
        llEtc = view.findViewById(R.id.llEtc);


//        btGoChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getBaseContext(), ChatActivity.class);
//                startActivity(intent);
//            }
//        });

        llNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getBaseContext(), ChatActivity.class);
                intent.putExtra("chatType","NoticeModel");
                startActivity(intent);
            }
        });

        llSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getBaseContext(), ChatActivity.class);
                intent.putExtra("chatType","schedule");
                startActivity(intent);
            }
        });

        llLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getBaseContext(), ChatActivity.class);
                intent.putExtra("chatType","lecture");
                startActivity(intent);
            }
        });

        llEtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getBaseContext(), ChatActivity.class);
                intent.putExtra("chatType","etc");
                startActivity(intent);
            }
        });



//        aiRequest.setQuery("1월 학사일정 알려줘");
//        new AsyncTask<AIRequest, Void, AIResponse>(){
//            @Override
//            protected AIResponse doInBackground(AIRequest... aiRequests) {
//                final AIRequest request = aiRequests[0];
//                try {
////                    tvResult.setText("" + aiRequests[0]);
//                    final AIResponse aiResponse = aiDataService.request(request);
//                    return aiResponse;
//                } catch (AIServiceException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(AIResponse aiResponse) {
//                super.onPostExecute(aiResponse);
//
//                if(aiResponse != null){
//                    Result result = aiResponse.getResult();
//                    String paramString = "";
//                    if(result.getParameters() != null && !result.getParameters().isEmpty()){
//                        for(final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()){
//                            paramString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
//                        }
//                    }
//
//
//                    tvResult.setText("Query : " + result.getResolvedQuery() +
//                            "\nAction : " + result.getAction()+
//                            "\nParam : " + paramString+
//                            "\nspeech : " + result.getFulfillment().getSpeech());
//
//
//
////                    tvResult.setText(aiResponse.getResult() + "");
//                }else{
//                    tvResult.setText("error");
//                }
//            }
//        }.execute(aiRequest);

//select from where만 만들기
        return view;
    }






}
