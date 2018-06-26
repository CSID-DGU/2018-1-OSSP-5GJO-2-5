package com.example.alex.dg_chatbot.UI.Main.chat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.alex.dg_chatbot.R;
import com.example.alex.dg_chatbot.UI.Login.RootActivity;
import com.example.alex.dg_chatbot.UI.Main.chat.LectureModel.LectureActivity;
import com.example.alex.dg_chatbot.UI.Main.chat.NoticeModel.NoticeFromChatActivity;
import com.example.alex.dg_chatbot.UI.Main.chat.ScheduleModel.ScheduleActivity;
import com.example.alex.dg_chatbot.Util.U;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonElement;

import java.util.Map;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

/**
 * 채팅방 기본 UI 2018.5.8
 * Firebase User 마다 각 채팅방 생성 2018.5.9
 */

public class ChatActivity extends RootActivity implements AIListener {

    private static final String ACCESS_TOKEN = "2aa32e7cdfb44da8b90ad5ca56141b01";
    private static final int NOTICE = 1;
    private static final int SCHEDULE = 2;
    private static final int LECTURE = 3;
    private static final int ETC = 4;

    private RecyclerView recyclerView;
    private EditText editText;
    private RelativeLayout addBtn;
    private DatabaseReference ref;
    private FirebaseRecyclerAdapter<ChatMessage, chat_rec> adapter;
    private Boolean flagFab = true;
    private String chatType;
    private ImageView ivChatTop;

    private FirebaseUser firebaseUser;
    private String userUid;

    private AIService aiService;
    private int stateFlag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //오디오 체크 접근 권한을 요청
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userUid = firebaseUser.getUid();


        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        addBtn = findViewById(R.id.addBtn);
        ivChatTop = findViewById(R.id.ivChatTop);


        chatType = getIntent().getStringExtra("chatType");
        int img = 0;
        switch (chatType) {
            case "NoticeModel":
                img = R.drawable.chat_frag_ll_notice;
                stateFlag = NOTICE;
                break;
            case "schedule":
                img = R.drawable.chat_frag_ll_schedule;
                stateFlag = SCHEDULE;
                break;
            case "lecture":
                img = R.drawable.chat_frag_ll_lecture;
                stateFlag = LECTURE;
                break;
            case "etc":
                img = R.drawable.chat_frag_ll_etc;
                stateFlag = ETC;
                break;
        }

        ivChatTop.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ivChatTop.setImageResource(img);


        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        ref = FirebaseDatabase.getInstance().getReference();
        ref.keepSynced(true);

        final ai.api.android.AIConfiguration config = new ai.api.android.AIConfiguration(ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.Korean,
                ai.api.android.AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        final AIDataService aiDataService = new AIDataService(config);

        final AIRequest aiRequest = new AIRequest();


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editText.getText().toString().trim();

                if (!message.equals("")) {

                    ChatMessage chatMessage = new ChatMessage(message, "user");
                    ref.child(userUid).child(chatType).push().setValue(chatMessage);

                    aiRequest.setQuery(message);
                    new AsyncTask<AIRequest, Void, AIResponse>() {

                        @Override
                        protected AIResponse doInBackground(AIRequest... aiRequests) {
                            final AIRequest request = aiRequests[0];
                            try {
                                final AIResponse response = aiDataService.request(aiRequest);
                                return response;
                            } catch (AIServiceException e) {
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(AIResponse response) {
                            if (response != null) {

                                String key = "";
                                String value = "";
                                String action = "";

                                Result result = response.getResult();
                                String reply = result.getFulfillment().getSpeech();
                                ChatMessage chatMessage = new ChatMessage(reply, "bot");
                                ref.child(userUid).child(chatType).push().setValue(chatMessage);

                                action = result.getAction().toString();

                                if (result.getParameters() != null && !result.getParameters().isEmpty()) {
                                    for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                                        key = entry.getKey();
                                        value = entry.getValue().toString();
                                        U.getInstance().resultLog("action : " + action + " key : " + key + " value : " + value
                                        +" speech : " + reply);
                                    }
                                }

                                if(action.equals("schedule") && key.equals("month")){
                                    Intent intent = new Intent(ChatActivity.this, ScheduleActivity.class);
                                    intent.putExtra("key",key);
                                    intent.putExtra("value",value);
                                    startActivity(intent);
                                }else if(action.equals("notice_normal") && key.equals("normalNotice")){
                                    Intent intent = new Intent(ChatActivity.this, NoticeFromChatActivity.class);
                                    intent.putExtra("key",key);
                                    intent.putExtra("value",value);
                                    startActivity(intent);
                                }else if(action.equals("lecture_name") || action.equals("lecture_prof")){
                                    Intent intent = new Intent(ChatActivity.this, LectureActivity.class);
                                    intent.putExtra("action", action);
                                    intent.putExtra("key", key);
                                    intent.putExtra("value", value);
                                    startActivity(intent);
                                }
                            }
                        }
                    }.execute(aiRequest);
                } else {
                    aiService.startListening();
                }

                editText.setText("");

            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ImageView fab_img = findViewById(R.id.fab_img);
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_send_white_24dp);
                Bitmap img1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_mic_white_24dp);


                if (s.toString().trim().length() != 0 && flagFab) {
                    ImageViewAnimatedChange(ChatActivity.this, fab_img, img);
                    flagFab = false;

                } else if (s.toString().trim().length() == 0) {
                    ImageViewAnimatedChange(ChatActivity.this, fab_img, img1);
                    flagFab = true;

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter = new FirebaseRecyclerAdapter<ChatMessage, chat_rec>(ChatMessage.class, R.layout.msglist, chat_rec.class, ref.child(userUid).child(chatType)) {
            @Override
            protected void populateViewHolder(chat_rec viewHolder, ChatMessage model, int position) {
                if (model.getMsgUser().equals("user")) {
                    viewHolder.rightText.setText(model.getMsgText());
                    viewHolder.rightText.setVisibility(View.VISIBLE);
                    viewHolder.leftText.setVisibility(View.GONE);
                } else {
                    viewHolder.leftText.setText(model.getMsgText());
                    viewHolder.rightText.setVisibility(View.GONE);
                    viewHolder.leftText.setVisibility(View.VISIBLE);
                }
            }
        };

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int msgCount = adapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1 ||
                        (positionStart >= (msgCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);

                }

            }
        });

        recyclerView.setAdapter(adapter);


    }


    //***********************************************
    //              AI LISTNER OVERRIDES
    //***********************************************

    public void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, R.anim.zoom_out);
        final Animation anim_in = AnimationUtils.loadAnimation(c, R.anim.zoom_in);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    @Override
    public void onResult(ai.api.model.AIResponse response) {
        Result result = response.getResult();

        String message = result.getResolvedQuery();
        ChatMessage chatMessage0 = new ChatMessage(message, "user");
        ref.child(userUid).child(chatType).push().setValue(chatMessage0);


        String reply = result.getFulfillment().getSpeech();
        ChatMessage chatMessage = new ChatMessage(reply, "bot");
        ref.child(userUid).child(chatType).push().setValue(chatMessage);
    }

    @Override
    public void onError(ai.api.model.AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }


}
